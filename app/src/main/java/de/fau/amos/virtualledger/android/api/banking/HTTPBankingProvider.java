package de.fau.amos.virtualledger.android.api.banking;

import android.util.Log;

import java.util.List;

import de.fau.amos.virtualledger.android.api.Restapi;
import de.fau.amos.virtualledger.android.api.shared.CallWithToken;
import de.fau.amos.virtualledger.android.api.shared.RetrofitCallback;
import de.fau.amos.virtualledger.android.api.shared.TokenCallback;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import io.reactivex.Observable;
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
    private CallWithToken callWithToken;

    public HTTPBankingProvider(Retrofit retrofit, final CallWithToken callWithToken) {
        this.retrofit = retrofit;
        this.callWithToken = callWithToken;
    }

    @Override
    public Observable<List<BankAccess>> getBankingOverview() {
        final PublishSubject<List<BankAccess>> observable = PublishSubject.create();

        callWithToken.callWithToken(observable, new TokenCallback() {
            @Override
            public void onReceiveToken(final String token) {
                // got token
                retrofit.create(Restapi.class).getBankAccesses(token).enqueue(new RetrofitCallback<>(observable));
            }
        });

        return observable;
    }

    @Override
    public Observable<BankAccountSyncResult> getBankingTransactions(final List<BankAccountSync> bankAccountSyncList) {

        final PublishSubject<BankAccountSyncResult> observable = PublishSubject.create();

        callWithToken.callWithToken(observable, new TokenCallback() {
            @Override
            public void onReceiveToken(final String token) {
                // got token
                retrofit.create(Restapi.class).getBookings(token, bankAccountSyncList).enqueue(new RetrofitCallback<>(observable));
            }
        });

        return observable;
    }

    @Override
    public Observable<BankAccess> addBankAccess(final BankAccessCredential bankAccessCredential) {
        final PublishSubject<BankAccess> observable = PublishSubject.create();

        callWithToken.callWithToken(observable, new TokenCallback() {
            @Override
            public void onReceiveToken(final String token) {
                // got token
                retrofit.create(Restapi.class).addBankAccess(token, bankAccessCredential).enqueue(new RetrofitCallback<>(observable));
            }
        });


        return observable;
    }

    @Override
    public Observable<Void> deleteBankAccess(final String accessId) {
        final PublishSubject<Void> observable = PublishSubject.create();

        callWithToken.callWithToken(observable, new TokenCallback() {
            @Override
            public void onReceiveToken(final String token) {
                // got token
                final retrofit2.Call<Void> responseMessage = retrofit.create(Restapi.class).deleteBankAccess(token, accessId);

                responseMessage.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                        // got response from server
                        if (response.isSuccessful()) {
                            Log.v(TAG, "Deleting bank accesses was successful " + response.code());
                            observable.onNext(response.body());
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
        });


        return observable;
    }

    @Override
    public Observable<Void> deleteBankAccount(final String accessId, final String accountId) {
        final PublishSubject<Void> observable = PublishSubject.create();

        callWithToken.callWithToken(observable, new TokenCallback() {
            @Override
            public void onReceiveToken(final String token) {
                // got token
                final retrofit2.Call<Void> responseMessage = retrofit.create(Restapi.class).deleteBankAccount(token, accessId, accountId);

                responseMessage.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                        // got response from server
                        if (response.isSuccessful()) {
                            Log.v(TAG, "Deleting bank account was successful " + response.code());
                            observable.onNext(response.body());
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
        });

        return observable;
    }
}
