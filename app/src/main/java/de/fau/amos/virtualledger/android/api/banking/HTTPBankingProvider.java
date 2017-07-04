package de.fau.amos.virtualledger.android.api.banking;

import android.util.Log;

import java.util.List;

import de.fau.amos.virtualledger.android.api.Restapi;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.model.SavingsAccount;
import de.fau.amos.virtualledger.android.views.savings.SavingAccountsAdapter;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by sebastian on 14.05.17.
 */

public class HTTPBankingProvider implements BankingProvider {

    private static final String TAG = "HTTPBankingProvider";

    private Retrofit retrofit;
    private AuthenticationProvider authenticationProvider;

    public HTTPBankingProvider(Retrofit retrofit, AuthenticationProvider authenticationProvider) {
        this.retrofit = retrofit;
        this.authenticationProvider = authenticationProvider;
    }


    @Override
    public Observable<List<BankAccess>> getBankingOverview() {
        final PublishSubject observable = PublishSubject.create();

        authenticationProvider.getToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull final String token) throws Exception {
                        // got token
                        final retrofit2.Call<List<BankAccess>> responseMessage = retrofit.create(Restapi.class).getBankAccesses(token);

                        responseMessage.enqueue(new Callback<List<BankAccess>>() {
                            @Override
                            public void onResponse(retrofit2.Call<List<BankAccess>> call, Response<List<BankAccess>> response) {
                                // got response from server
                                if (response.isSuccessful()) {
                                    List<BankAccess> bankAccesses = response.body();
                                    Log.v(TAG, "Fetching of bank accesses was successful " + response.code());
                                    observable.onNext(bankAccesses);
                                } else {
                                    Log.e(TAG, "Fetchin of bank accesses was not successful! ERROR " + response.code());
                                    observable.onError(new Throwable("Fetching of bank accesses was not successful!"));
                                }
                            }


                            @Override
                            public void onFailure(retrofit2.Call<List<BankAccess>> call, Throwable t) {
                                // response of server failed
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
    public Observable<BankAccountSyncResult> getBankingTransactions(final List<BankAccountSync> bankAccountSyncList) {

        final PublishSubject observable = PublishSubject.create();
        authenticationProvider.getToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull final String token) throws Exception {
                        // got token
                        final retrofit2.Call<BankAccountSyncResult> responseMessage = retrofit.create(Restapi.class).getBookings(token, bankAccountSyncList );

                        responseMessage.enqueue(new Callback<BankAccountSyncResult>() {
                            @Override
                            public void onResponse(retrofit2.Call<BankAccountSyncResult> call, Response<BankAccountSyncResult> response) {
                                // got response from server
                                if (response.isSuccessful()) {
                                    BankAccountSyncResult syncResult = response.body();
                                    Log.v(TAG, "Getting Bookings was successful " + response.code());
                                    observable.onNext(syncResult);
                                } else {
                                    Log.e(TAG, "Getting Bookings was not successful!  ERROR " + response.code());
                                    observable.onError(new Throwable("Getting Bookings was not successful!"));
                                }
                            }


                            @Override
                            public void onFailure(retrofit2.Call<BankAccountSyncResult> call, Throwable t) {
                                // response of server failed
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
    public Observable<BankAccess> addBankAccess(final BankAccessCredential bankAccessCredential) {
        final PublishSubject observable = PublishSubject.create();

        authenticationProvider.getToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull final String token) throws Exception {
                        // got token
                        final retrofit2.Call<BankAccess> responseMessage = retrofit.create(Restapi.class).addBankAccess(token, bankAccessCredential);

                        responseMessage.enqueue(new Callback<BankAccess>() {
                            @Override
                            public void onResponse(retrofit2.Call<BankAccess> call, Response<BankAccess> response) {
                                // got response from server
                                if (response.isSuccessful()) {
                                    BankAccess bankAccess = response.body();
                                    Log.v(TAG, "Adding bank accesses was successful " + response.code());
                                    observable.onNext(bankAccess);
                                } else {
                                    Log.e(TAG, "Adding bank accesses was not successful! ERROR " + response.code());
                                    observable.onError(new Throwable("Adding bank accesses was not successful!"));
                                }
                            }


                            @Override
                            public void onFailure(retrofit2.Call<BankAccess> call, Throwable t) {
                                // response of server failed
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
    public Observable<String> deleteBankAccess(final String accessId) {
        final PublishSubject observable = PublishSubject.create();

        authenticationProvider.getToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull final String token) throws Exception {
                        // got token
                        final retrofit2.Call<Void> responseMessage = retrofit.create(Restapi.class).deleteBankAccess(token, accessId);

                        responseMessage.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                                // got response from server
                                if (response.isSuccessful()) {
                                    Log.v(TAG, "Deleting bank accesses was successful " + response.code());
                                    observable.onNext("Deleting bank access was successful");
                                } else {
                                    Log.e(TAG, "Deleting bank accesses was not successful! ERROR " + response.code());
                                    observable.onError(new Throwable("Deleting bank accesses was not successful!"));
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

    @Override
    public Observable<String> deleteBankAccount(final String accessId, final String accountId) {
        final PublishSubject observable = PublishSubject.create();

        authenticationProvider.getToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull final String token) throws Exception {
                        // got token
                        final retrofit2.Call<Void> responseMessage = retrofit.create(Restapi.class).deleteBankAccount(token, accessId, accountId);

                        responseMessage.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                                // got response from server
                                if (response.isSuccessful()) {
                                    Log.v(TAG, "Deleting bank account was successful " + response.code());
                                    observable.onNext("Deleting bank account was successful");
                                } else {
                                    Log.e(TAG, "Deleting bank account was not successful! ERROR " + response.code());
                                    observable.onError(new Throwable("Deleting bank account was not successful!"));
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
