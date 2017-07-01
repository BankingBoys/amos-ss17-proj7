package de.fau.amos.virtualledger.android.api.contacts;

import android.util.Log;

import java.util.List;

import de.fau.amos.virtualledger.android.api.Restapi;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.model.Contact;
import de.fau.amos.virtualledger.android.model.SavingsAccount;
import io.reactivex.Observable;
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

        final retrofit2.Call<List<Contact>> responseMessage = retrofit.create(Restapi.class).getContacts(authenticationProvider.getToken());
        final PublishSubject observable = PublishSubject.create();

        responseMessage.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Contact>> call, Response<List<Contact>> response) {
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

                Log.e(TAG, "No connection to server!");
                observable.onError(new Throwable("No connection to server!"));
            }
        });

        return observable;
    }


    @Override
    public Observable<String> addContacts(Contact savingsAccount) {
        return null;
    }
}
