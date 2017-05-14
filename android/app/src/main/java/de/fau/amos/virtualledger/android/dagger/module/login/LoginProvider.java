package de.fau.amos.virtualledger.android.dagger.module.login;

/**
 * Created by sebastian on 14.05.17.
 */

public interface LoginProvider {

    void login(String username, String password);

    void logout();

    boolean isLoggedIn();

    String getToken();

    void save();

    boolean loginTokenSaved();

    void loadFromStorage();
}
