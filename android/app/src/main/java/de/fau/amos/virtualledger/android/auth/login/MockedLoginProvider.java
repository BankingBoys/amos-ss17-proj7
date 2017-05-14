package de.fau.amos.virtualledger.android.auth.login;

/**
 * Created by sebastian on 14.05.17.
 */

public class MockedLoginProvider implements LoginProvider {

    private String token = "";

    @Override
    public void login(String username, String password) {
        this.token = username+"_"+password;
    }

    @Override
    public void logout() {
        this.token = "";
    }

    @Override
    public boolean isLoggedIn() {
        return this.token.length()==0;
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
