package de.fau.amos.virtualledger.android.api.savings;


import android.util.Log;

import java.util.List;

import de.fau.amos.virtualledger.android.api.Restapi;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.model.SavingsAccount;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HTTPSavingsProvider implements SavingsProvider {

    private static final String TAG = "HTTPSavingsProvider";

    private Retrofit retrofit;
    private AuthenticationProvider authenticationProvider;

    public HTTPSavingsProvider(Retrofit retrofit, AuthenticationProvider authenticationProvider) {
        this.retrofit = retrofit;
        this.authenticationProvider = authenticationProvider;
    }


    @Override
    public Observable<List<SavingsAccount>> getSavingAccounts() {

        final retrofit2.Call<List<SavingsAccount>> responseMessage = retrofit.create(Restapi.class).getSavingAccounts(authenticationProvider.getToken());
        final PublishSubject observable = PublishSubject.create();

        responseMessage.enqueue(new Callback<List<SavingsAccount>>() {
            @Override
            public void onResponse(retrofit2.Call<List<SavingsAccount>> call, Response<List<SavingsAccount>> response) {
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

                Log.e(TAG, "No connection to server!");
                observable.onError(new Throwable("No connection to server!"));
            }
        });

        return observable;
    }

    @Override
    public Observable<String> addSavingAccount(SavingsAccount savingsAccount) {
        final retrofit2.Call<Void> responseMessage = retrofit.create(Restapi.class).addSavingAccounts(authenticationProvider.getToken(), savingsAccount);
        final PublishSubject observable = PublishSubject.create();

        responseMessage.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    /*String callResponse = response.body();*/
                    Log.v(TAG, "Adding Saving Accounts was successful " + response.code());
                    observable.onNext("Adding Saving Accounts was successful");
                } else {
                    Log.e(TAG, "Adding Saving Accounts was not successful! ERROR " + response.code());
                    observable.onError(new Throwable("Adding Saving Accounts was not successful!"));
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
