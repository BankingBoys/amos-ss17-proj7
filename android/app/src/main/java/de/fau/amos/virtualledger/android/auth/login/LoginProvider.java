package de.fau.amos.virtualledger.android.auth.login;

/**
 * Created by sebastian on 14.05.17.
 */

public interface LoginProvider {

    void login(String username, String password);

    void logout();

    boolean isLoggedIn();

    String getToken();

    void save();

    boolean isTokenSaved();

    void loadFromStorage();
}
