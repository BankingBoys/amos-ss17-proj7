package de.fau.amos.virtualledger.android.dagger.module.login;

import de.fau.amos.virtualledger.android.api.Restapi;
import de.fau.amos.virtualledger.android.model.UserCredential;
import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.dtos.StringApiModel;
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
