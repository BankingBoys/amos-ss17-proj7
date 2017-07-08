package de.fau.amos.virtualledger.android.api.savings;


import android.util.Log;

import java.util.List;

import de.fau.amos.virtualledger.android.api.RestApi;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.model.SavingsAccount;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Callback;
import retrofit2.Response;

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
                        final retrofit2.Call<List<SavingsAccount>> responseMessage = restApi.getSavingAccounts(token);

                        responseMessage.enqueue(new Callback<List<SavingsAccount>>() {
                            @Override
                            public void onResponse(retrofit2.Call<List<SavingsAccount>> call, Response<List<SavingsAccount>> response) {
                                // got response from server
                                if (response.isSuccessful()) {
                                    List<SavingsAccount> savingsAccounts = response.body();
                                    Log.v(TAG, "Fetching of saving accounts was successful " + response.code());
                                    observable.onNext(savingsAccounts);
                                } else {
                                    Log.e(TAG, "Fetchin of saving accounts was not successful! ERROR " + response.code());
                                    observable.onError(new Throwable("Fetching of saving accounts was not successful!"));
                                }
                            }


                            @Override
                            public void onFailure(retrofit2.Call<List<SavingsAccount>> call, Throwable t) {
                                // reponse from server failed
                                Log.e(TAG, "No connection to server!");
                                observable.onError(new Throwable("No connection to server!"));
                            }
                        });
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
                        final retrofit2.Call<Void> responseMessage = restApi.addSavingAccounts(token, savingsAccount);

                        responseMessage.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                                // got resoinse from server
                                if (response.code() == 201) {
                                    Log.v(TAG, "Adding Saving Accounts was successful " + response.code());
                                    observable.onNext(response.body());
                                } else {
                                    Log.e(TAG, "Adding Saving Accounts was not successful! ERROR " + response.code());
                                    observable.onError(new Throwable("Adding Saving Accounts was not successful!"));
                                }
                            }

                            @Override
                            public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                                // response from server failed
                                Log.e(TAG, "No connection to server!");
                                observable.onError(new Throwable("No connection to server!"));
                            }
                        });
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
