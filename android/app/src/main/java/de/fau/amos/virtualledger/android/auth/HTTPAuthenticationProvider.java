package de.fau.amos.virtualledger.android.auth;

import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInstaller;
import android.graphics.Color;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.fau.amos.virtualledger.android.LoginActivity;
import de.fau.amos.virtualledger.android.MainActivity_Menu;
import de.fau.amos.virtualledger.android.RegisterActivity;
import de.fau.amos.virtualledger.android.api.Restapi;
import de.fau.amos.virtualledger.android.model.UserCredential;
import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.dtos.SessionData;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by sebastian on 14.05.17.
 */

public class HTTPAuthenticationProvider implements AuthenticationProvider {

    private static final String TAG = "HTTPAuthenticatProvider";
    private static final String FILENAME = "login.save";

    private Retrofit retrofit;
    private String token =  "";
    private String email = "";

    public HTTPAuthenticationProvider(Retrofit retrofit){
        this.retrofit = retrofit;
    }

    @Override
    public Observable<String> register(String email, String password, String firstname, String lastname) {
        retrofit2.Call<StringApiModel> responseMessage = retrofit.create(Restapi.class).register(new UserCredential(email, password, firstname, lastname));
        final PublishSubject observable = PublishSubject.create();

        responseMessage.enqueue(new Callback<StringApiModel>() {
            @Override
            public void onResponse(retrofit2.Call<StringApiModel> call, Response<StringApiModel> response) {
                if (response.isSuccessful()) {
                    String responseMsg = response.body().getData();
                    observable.onNext(responseMsg);
                } else if (response.code() == 400) { // code for sent data were wrong
                    try {
                        String responseMsg = response.errorBody().string();
                        observable.onError(new Throwable(responseMsg));
                    } catch (IOException ex) {
                        Log.e(TAG, "Could not parse error body in register");
                        observable.onError(new Throwable("Could not parse error body in register"));
                    }
                } else {
                    Log.v(TAG, "The communication to the server failed at register!");
                    observable.onError(new Throwable("The communication to the server failed at register!"));
                }
            }


            @Override
            public void onFailure(retrofit2.Call<StringApiModel> call, Throwable t) {
                Log.v(TAG, "Register failed!");
                observable.onError(new Throwable("Register failed!"));
            }
        });

        return observable;
    }

    @Override
    public Observable<String> login(final String username, String password) {
        retrofit2.Call<SessionData> responseMessage = retrofit.create(Restapi.class).login(new LoginData(username,password));
        final PublishSubject observable = PublishSubject.create();

        responseMessage.enqueue(new Callback<SessionData>() {
            @Override
            public void onResponse(retrofit2.Call<SessionData> call, Response<SessionData> response) {
                if(response.isSuccessful()) {
                    email = username;
                    token = response.body().getSessionid();
                    observable.onNext("Login was successful!");
                } else
                {
                    Log.e(TAG, "Login was not successful!");
                    observable.onError(new Throwable("Login was not successful!"));
                }
            }
            @Override
            public void onFailure(retrofit2.Call<SessionData> call, Throwable t) {
                Log.e(TAG, "Login failed!");
                observable.onError(new Throwable("Logout failed!"));
            }
        });
        return observable;
    }

    @Override
    public Observable<String> logout() {

        retrofit2.Call<StringApiModel> responseMessage = retrofit.create(Restapi.class).logout(token);
        final PublishSubject observable = PublishSubject.create();

        responseMessage.enqueue(new Callback<StringApiModel>() {
            @Override
            public void onResponse(retrofit2.Call<StringApiModel> call, Response<StringApiModel> response) {
                if(response.isSuccessful()) {
                    token = "";
                    /*deleteSavedLoginData();*/
                    observable.onNext("Logout was successful!");
                } else
                {
                    Log.e(TAG,"Logout was not successful! ERROR " + response.code());
                    observable.onError(new Throwable("Logout was not successful!"));
                }
            }


            @Override
            public void onFailure(retrofit2.Call<StringApiModel> call, Throwable t) {

                Log.e(TAG, "Logout failed!");
                observable.onError(new Throwable("Logout failed!"));
            }
        });

        return observable;
    }

    @Override
    public boolean isLoggedIn() {
        return token.length() != 0;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public void persistLoginData(Context context) {
        /*deleteSavedLoginData(context);*/
        File loginData = new File(FILENAME);
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(new SessionData(email, token));
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "Error in persisting login data: " + e.getMessage());
        } finally {
              {
                try{
                    if(fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if(objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                } catch(IOException e) {
                    Log.e(TAG, "Error while closing streams" + e.getMessage());
                }


            }
        }
    }

    @Override
    public void deleteSavedLoginData(Context context) {
        File loginData = new File(FILENAME);
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(new SessionData("", ""));
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "Error in persisting login data: " + e.getMessage());
        } finally {

                try{
                    if(fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if(objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                } catch(IOException e) {
                    Log.e(TAG, "Error while closing streams" + e.getMessage());
                }
        }
        this.email = "";
        this.token = "";

    }

    @Override
    public void tryLoadLoginData(Context context) {
        File file = new File(FILENAME);
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = context.openFileInput(FILENAME);
            objectInputStream = new ObjectInputStream(fileInputStream);
            SessionData savedSession = (SessionData) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

            if(savedSession.getEmail() == null || savedSession.getEmail().isEmpty() || savedSession.getSessionid() == null || savedSession.getSessionid().isEmpty())
            {
                throw new ClassNotFoundException("One of the loaded parameters was null or empty!");
            }

            this.email = savedSession.getEmail();
            this.token = savedSession.getSessionid();
        } catch (IOException e) {
            Log.e(TAG, "Error in reading persisted login data: " + e.getMessage());
        }
        catch (ClassNotFoundException e) {
            Log.e(TAG, "Error in reading persisted login data: " + e.getMessage());
        } finally {
            try{
                if(fileInputStream  != null) {
                    fileInputStream.close();
                }
                if(objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch(IOException e) {
                Log.e(TAG, "Error while closing streams" + e.getMessage());
            }
        }
    }

    @Override
    public Observable<String> getBankAccess() {
        /*retrofit2.Call<StringApiModel> responseMessage = retrofit.create(Restapi.class).register(new UserCredential(email, password, firstname, lastname));*/
        /*final PublishSubject observable = PublishSubject.create();

        responseMessage.enqueue(new Callback<StringApiModel>() {
            @Override
            public void onResponse(retrofit2.Call<StringApiModel> call, Response<StringApiModel> response) {
                if (response.isSuccessful()) {
                    String responseMsg = response.body().getData();
                    observable.onNext(responseMsg);
                } else if (response.code() == 400) { // code for sent data were wrong
                    try {
                        String responseMsg = response.errorBody().string();
                        observable.onError(new Throwable(responseMsg));
                    } catch (IOException ex) {
                        Log.e(TAG, "Could not parse error body in register");
                        observable.onError(new Throwable("Could not parse error body in register"));
                    }
                } else {
                    Log.v(TAG, "The communication to the server failed at register!");
                    observable.onError(new Throwable("The communication to the server failed at register!"));
                }
            }


            @Override
            public void onFailure(retrofit2.Call<StringApiModel> call, Throwable t) {
                Log.v(TAG, "Register failed!");
                observable.onError(new Throwable("Register failed!"));
            }
        });

        return observable;
        */
        return null;
    }

}
