package de.fau.amos.virtualledger.android.api.savings;


import android.util.Log;

import java.util.List;

import de.fau.amos.virtualledger.android.api.RestApi;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.shared.RetrofitCallback;
import de.fau.amos.virtualledger.android.model.SavingsAccount;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class HTTPSavingsProvider implements SavingsProvider {

    private static final String TAG = "HTTPSavingsProvider";

    private RestApi restApi;
    private AuthenticationProvider authenticationProvider;


    public HTTPSavingsProvider(final RestApi restApi, final AuthenticationProvider authenticationProvider) {
        this.restApi = restApi;
        this.authenticationProvider = authenticationProvider;
    }


    @Override
    public Observable<List<SavingsAccount>> getSavingAccounts() {
        final PublishSubject<List<SavingsAccount>> observable = PublishSubject.create();

        authenticationProvider.getToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull final String token) throws Exception {
                        // got token
                        restApi.getSavingAccounts(token).enqueue(new RetrofitCallback<>(observable));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull final Throwable throwable) throws Exception {
                        // did not get any token
                        Log.e(TAG, throwable.getMessage());
                        observable.onError(new Throwable("No authentication token available!"));
                    }
                });

        return observable;
    }

    @Override
    public Observable<Void> addSavingAccount(final SavingsAccount savingsAccount) {

        final PublishSubject<Void> observable = PublishSubject.create();

        authenticationProvider.getToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull final String token) throws Exception {
                        // got token
                        restApi.addSavingAccounts(token, savingsAccount).enqueue(new RetrofitCallback<>(observable));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull final Throwable throwable) throws Exception {
                        // did not get any token
                        Log.e(TAG, throwable.getMessage());
                        observable.onError(new Throwable("No authentication token available!"));
                    }
                });

        return observable;
    }

}
