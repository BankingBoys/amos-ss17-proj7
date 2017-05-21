package de.fau.amos.virtualledger.android.auth;

import android.content.Context;

import java.util.List;

import de.fau.amos.virtualledger.dtos.BankAccess;
import io.reactivex.Observable;

/**
 * Created by sebastian on 14.05.17.
 */

public interface AuthenticationProvider {

    Observable<String> register(String email,String password,String firstname,String lastname);

    Observable<String> login(String username, String password);

    Observable<String> logout();

    List<BankAccess> getBankAccess();

    boolean isLoggedIn();

    String getToken();

    void persistLoginData(Context context);

    void deleteSavedLoginData(Context context);

    /**
     * tries to load the login data from storage
     * check afterwards with isLoggedIn() if it worked
     */
    void tryLoadLoginData(Context context);
}
