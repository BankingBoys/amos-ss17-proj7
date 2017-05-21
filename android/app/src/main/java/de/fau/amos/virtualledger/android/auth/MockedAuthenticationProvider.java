package de.fau.amos.virtualledger.android.auth;

import android.content.Context;

import java.util.List;

import de.fau.amos.virtualledger.dtos.BankAccess;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by sebastian on 14.05.17.
 */

public class MockedAuthenticationProvider implements AuthenticationProvider {

    private String token = "";

    @Override
    public Observable<String> register(String email, String password, String firstname, String lastname) {
        final PublishSubject observable = PublishSubject.create();
        observable.onNext("Registered (Dummy)");
        return observable;
    }

    @Override
    public Observable<String> login(String username, String password) {
        this.token = username+"_"+password;
        final PublishSubject observable = PublishSubject.create();
        observable.onNext("Logged in (Dummy)");
        return observable;
    }

    @Override
    public Observable<String> logout() {
        this.token = "";

        final PublishSubject observable = PublishSubject.create();
        observable.onNext("Logged out (Dummy)");
        return observable;
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
    public void persistLoginData(Context context) {

    }

    @Override
    public void deleteSavedLoginData(Context context) {

    }

    @Override
    public List<BankAccess> getBankAccess() {
        return null;
    }

    @Override
    public void tryLoadLoginData(Context context) {

    }

}
