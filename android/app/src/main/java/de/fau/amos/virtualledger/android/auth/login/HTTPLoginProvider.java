package de.fau.amos.virtualledger.android.auth.login;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import java.io.IOException;

import de.fau.amos.virtualledger.android.MainActivity_Menu;
import de.fau.amos.virtualledger.android.RegisterActivity;
import de.fau.amos.virtualledger.android.api.Restapi;
import de.fau.amos.virtualledger.android.model.UserCredential;
import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by sebastian on 14.05.17.
 */

public class HTTPLoginProvider implements LoginProvider {
    private Retrofit retrofit;
    private String token =  "";

    public HTTPLoginProvider(Retrofit retrofit){
        this.retrofit = retrofit;
    }

    @Override
    public void login(String username, String password) {
        retrofit2.Call<StringApiModel> responseMessage = retrofit.create(Restapi.class).login(new LoginData(username,password));

        responseMessage.enqueue(new Callback<StringApiModel>() {
            @Override
            public void onResponse(retrofit2.Call<StringApiModel> call, Response<StringApiModel> response) {
                if(response.isSuccessful()) {
                    token = response.message();
                }
            }   @Override
            public void onFailure(retrofit2.Call<StringApiModel> call, Throwable t) {
                
            }
        }

        );
    }

    @Override
    public void logout() {

    }

    @Override
    public boolean isLoggedIn() {
        return token.length()==0;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public void save() {

    }

    @Override
    public boolean loginTokenSaved() {
        return false;
    }

    @Override
    public void loadFromStorage() {

    }
}
