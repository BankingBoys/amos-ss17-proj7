package de.fau.amos.virtualledger.android.api.auth;

import android.content.Context;
import android.util.Log;

import org.apache.commons.lang3.NotImplementedException;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Georg on 26.06.2017.
 */

public class OidcAuthenticationProvider implements AuthenticationProvider {

    private static final String TAG = "OidcAuthenticationProvi";

    private Retrofit retrofit;

    private final String CLIENT_ID = "multibanking-client";
    private final String GRANT_TYPE_LOGIN = "password";
    private final String GRANT_TYPE_REFRESH = "refresh_token";



    public OidcAuthenticationProvider(Retrofit retrofit) {
        this.retrofit = retrofit;
    }


    @Override
    public Observable<String> register(String email, String password, String firstname, String lastname) {
        throw new NotImplementedException("Registering with OIDC is not implemented! Please redirect the user to the register page of the authority server in a browser!");
    }

    @Override
    public Observable<String> login(String username, String password) {
        retrofit2.Call<OidcData> responseMessage = retrofit.create(KeycloakApi.class).login(username, password, CLIENT_ID, GRANT_TYPE_LOGIN);
        final PublishSubject observable = PublishSubject.create();

        responseMessage.enqueue(new Callback<OidcData>() {
            @Override
            public void onResponse(retrofit2.Call<OidcData> call, Response<OidcData> response) {
                if (response.isSuccessful()) {


                    observable.onNext("Login was successful!");
                } else {
                    Log.e(TAG, "Login was not successful!");
                    observable.onError(new Throwable("Login was not successful!"));
                }
            }

            @Override
            public void onFailure(retrofit2.Call<OidcData> call, Throwable t) {
                Log.e(TAG, "Login failed!");
                observable.onError(new Throwable("Login failed!"));
            }
        });
        return observable;
    }

    @Override
    public Observable<String> logout() {
        return null;
    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public void persistLoginData(Context context) {

    }

    @Override
    public void deleteSavedLoginData(Context context) {

    }

    @Override
    public void tryLoadLoginData(Context context) {

    }
}
