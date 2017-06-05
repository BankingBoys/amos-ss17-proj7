package de.fau.amos.virtualledger.android.api.banking;

import android.util.Log;

import java.util.List;

import de.fau.amos.virtualledger.android.api.Restapi;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
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
    private AuthenticationProvider authenticationProvider;

    public HTTPBankingProvider(Retrofit retrofit, AuthenticationProvider authenticationProvider) {
        this.retrofit = retrofit;
        this.authenticationProvider = authenticationProvider;
    }


    @Override
    public Observable<List<BankAccess>> getBankingOverview() {

        final retrofit2.Call<List<BankAccess>> responseMessage = retrofit.create(Restapi.class).getBankAccess(authenticationProvider.getToken());
        final PublishSubject observable = PublishSubject.create();

        responseMessage.enqueue(new Callback<List<BankAccess>>() {
            @Override
            public void onResponse(retrofit2.Call<List<BankAccess>> call, Response<List<BankAccess>> response) {
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

                Log.e(TAG, "No connection to server!");
                observable.onError(new Throwable("No connection to server!"));
            }
        });

        return observable;
    }

    @Override
    public Observable<BankAccountSyncResult> getBankingTransactions(List<BankAccountSync> bankAccountSyncList) {

        final retrofit2.Call<BankAccountSyncResult> responseMessage = retrofit.create(Restapi.class).getBookings(authenticationProvider.getToken(), bankAccountSyncList );
        final PublishSubject observable = PublishSubject.create();

        responseMessage.enqueue(new Callback<BankAccountSyncResult>() {
            @Override
            public void onResponse(retrofit2.Call<BankAccountSyncResult> call, Response<BankAccountSyncResult> response) {
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

                Log.e(TAG, "No connection to server!");
                observable.onError(new Throwable("No connection to server!"));
            }
        });

        return observable;
    }

    @Override
    public Observable<BankAccess> addBankAccess(BankAccessCredential bankAccessCredential) {
        final retrofit2.Call<BankAccess> responseMessage = retrofit.create(Restapi.class).addBankAccess(authenticationProvider.getToken(), bankAccessCredential);
        final PublishSubject observable = PublishSubject.create();

        responseMessage.enqueue(new Callback<BankAccess>() {
            @Override
            public void onResponse(retrofit2.Call<BankAccess> call, Response<BankAccess> response) {
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

                Log.e(TAG, "No connection to server!");
                observable.onError(new Throwable("No connection to server!"));
            }
        });

        return observable;
    }

    @Override
    public Observable<String> deleteBankAccess(String accessId) {
        final retrofit2.Call<Void> responseMessage = retrofit.create(Restapi.class).deleteBankAccess(authenticationProvider.getToken(), accessId);
        final PublishSubject observable = PublishSubject.create();

        responseMessage.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
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

                Log.e(TAG, "No connection to server!");
                observable.onError(new Throwable("No connection to server!"));
            }
        });

        return observable;
    }

    @Override
    public Observable<String> deleteBankAccount(String accessId, String accountId) {
        final retrofit2.Call<Void> responseMessage = retrofit.create(Restapi.class).deleteBankAccount(authenticationProvider.getToken(), accessId, accountId);
        final PublishSubject observable = PublishSubject.create();

        responseMessage.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
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

                Log.e(TAG, "No connection to server!");
                observable.onError(new Throwable("No connection to server!"));
            }
        });

        return observable;
    }

    @Override
    public Observable<String> syncBankAccounts(List<BankAccountSync> bankAccountSyncList) {
        final retrofit2.Call<Void> responseMessage = retrofit.create(Restapi.class).syncBankAccounts(authenticationProvider.getToken(), bankAccountSyncList);
        final PublishSubject observable = PublishSubject.create();

        responseMessage.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.v(TAG, "Synchronizing bank accounts was successful " + response.code());
                    observable.onNext("Synchronizing bank accounts was successful");
                } else {
                    Log.e(TAG, "Synchronizing bank accounts was not successful! ERROR " + response.code());
                    observable.onError(new Throwable("Synchronizing bank accounts was not successful!"));
                }
            }


            @Override
            public void onFailure(retrofit2.Call<Void> call, Throwable t) {

                Log.e(TAG, "No connection to server!");
                observable.onError(new Throwable("No connection to server!"));
            }
        });

        return observable;
    }

}
