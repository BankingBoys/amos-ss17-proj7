package de.fau.amos.virtualledger.android.auth.login;

import android.util.Log;

import de.fau.amos.virtualledger.android.api.Restapi;
import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.dtos.SessionData;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by sebastian on 14.05.17.
 */

public class HTTPLoginProvider implements LoginProvider {

    private static final String TAG = "HTTPLoginProvider";

    private Retrofit retrofit;
    private String token =  "";
    private String email = "";

    public HTTPLoginProvider(Retrofit retrofit){
        this.retrofit = retrofit;
    }

    @Override
    public void login(final String username, String password) {
        retrofit2.Call<SessionData> responseMessage = retrofit.create(Restapi.class).login(new LoginData(username,password));

        responseMessage.enqueue(new Callback<SessionData>() {
            @Override
            public void onResponse(retrofit2.Call<SessionData> call, Response<SessionData> response) {
                if(response.isSuccessful()) {
                    email = username;
                    token = response.body().getSessionid();
                } else
                {
                    Log.e(TAG, "Login was not successful!");
                }
            }
            @Override
            public void onFailure(retrofit2.Call<SessionData> call, Throwable t) {
                Log.e(TAG, "Login failed!");
            }
        }

        );
    }

    @Override
    public void logout() {

        retrofit2.Call<StringApiModel> responseMessage = retrofit.create(Restapi.class).logout(token);

        responseMessage.enqueue(new Callback<StringApiModel>() {
            @Override
            public void onResponse(retrofit2.Call<StringApiModel> call, Response<StringApiModel> response) {
                if(response.isSuccessful()) {
                    token = "";
                } else
                {
                    Log.e(TAG,"Logout was not successful! ERROR " + response.code());
                }
            }


            @Override
            public void onFailure(retrofit2.Call<StringApiModel> call, Throwable t) {

                Log.e(TAG, "Logout failed!");
            }
        });
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
    public void save() {
        // TODO implement
    }

    @Override
    public boolean isTokenSaved() {
        // TODO implement
        return false;
    }

    @Override
    public void loadFromStorage() {
        // TODO implement
    }
}
