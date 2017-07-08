package de.fau.amos.virtualledger.android.api.contacts;

import android.util.Log;

import java.util.List;

import de.fau.amos.virtualledger.android.api.RestApi;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.shared.RetrofitCallback;
import de.fau.amos.virtualledger.dtos.Contact;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Simon on 01.07.2017.
 */

public class HTTPContactsProvider implements ContactsProvider{

    private static final String TAG = "HTTPContactsProvider";

    private RestApi restApi;
    private AuthenticationProvider authenticationProvider;

    public HTTPContactsProvider(RestApi restApi, AuthenticationProvider authenticationProvider) {
        this.restApi = restApi;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Observable<List<Contact>> getContacts() {

        final PublishSubject<List<Contact>> observable = PublishSubject.create();

        authenticationProvider.getToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull final String token) throws Exception {
                        // got token
                        restApi.getContacts(token).enqueue(new RetrofitCallback<>(observable));
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
    public Observable<Void> addContacts(Contact savingsAccount) {
        return null;
    }
}
