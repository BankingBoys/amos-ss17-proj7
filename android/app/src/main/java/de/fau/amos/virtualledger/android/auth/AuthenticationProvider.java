package de.fau.amos.virtualledger.android.auth;

import io.reactivex.Observable;

/**
 * Created by sebastian on 14.05.17.
 */

public interface AuthenticationProvider {

    Observable<String> register(String email,String password,String firstname,String lastname);

    Observable<String> login(String username, String password);

    Observable<String> logout();

    boolean isLoggedIn();

    String getToken();

    void save();

    boolean isTokenSaved();

    void loadFromStorage();
}
