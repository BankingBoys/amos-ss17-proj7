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
        return null;
    }

    @Override
    public Observable<String> addContacts(Contact savingsAccount) {
        return null;
    }
}
