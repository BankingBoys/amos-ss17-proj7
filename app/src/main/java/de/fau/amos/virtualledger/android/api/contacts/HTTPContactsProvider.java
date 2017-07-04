package de.fau.amos.virtualledger.android.api.contacts;

import android.util.Log;

import java.util.List;

import de.fau.amos.virtualledger.android.api.Restapi;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.android.model.SavingsAccount;
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
 * Created by Simon on 01.07.2017.
 */

public class HTTPContactsProvider implements ContactsProvider{

    private static final String TAG = "HTTPContactsProvider";

    private Retrofit retrofit;
    private AuthenticationProvider authenticationProvider;

    public HTTPContactsProvider(Retrofit retrofit, AuthenticationProvider authenticationProvider) {
        this.retrofit = retrofit;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Observable<List<Contact>> getContacts() {

        final PublishSubject observable = PublishSubject.create();

        authenticationProvider.getToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull final String token) throws Exception {
                        // got token
                        final retrofit2.Call<List<Contact>> responseMessage = retrofit.create(Restapi.class).getContacts(token);

                        responseMessage.enqueue(new Callback<List<Contact>>() {
                            @Override
                            public void onResponse(retrofit2.Call<List<Contact>> call, Response<List<Contact>> response) {
                                // got response from server
                                if (response.isSuccessful()) {
                                    List<Contact> contacts = response.body();
                                    Log.v(TAG, "Fetching of contacts was successful " + response.code());
                                    observable.onNext(contacts);
                                } else {
                                    Log.e(TAG, "Fetchin of contacts was not successful! ERROR " + response.code());
                                    observable.onError(new Throwable("Fetching of contacts was not successful!"));
                                }
                            }


                            @Override
                            public void onFailure(retrofit2.Call<List<Contact>> call, Throwable t) {
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
    public Observable<String> addContacts(Contact savingsAccount) {
        return null;
    }
}
