package de.fau.amos.virtualledger.android.auth.login;

/**
 * Created by sebastian on 14.05.17.
 */

public class HTTPLoginProvider implements LoginProvider {
    @Override
    public void login(String username, String password) {

    }

    @Override
    public void logout() {

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
