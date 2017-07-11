package de.fau.amos.virtualledger.android.api.auth;

import android.content.Context;

import io.reactivex.Observable;

/**
 * Created by sebastian on 14.05.17.
 */

public interface AuthenticationProvider {

    Observable<String> register(String email, String password, String firstname, String lastname);

    Observable<String> login(String username, String password);

    Observable<String> logout();

    boolean isLoggedIn();

    Observable<String> getToken();

    String getUserId();

    void persistLoginData();

    void deleteSavedLoginData();

    /**
     * tries to load the login data from storage
     * and tries to login if data was found.
     */
    Observable<String> tryLoadLoginData();
}
