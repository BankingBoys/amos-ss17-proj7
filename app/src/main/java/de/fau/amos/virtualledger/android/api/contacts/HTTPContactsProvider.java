package de.fau.amos.virtualledger.android.api.contacts;

import java.util.List;

import de.fau.amos.virtualledger.android.api.RestApi;
import de.fau.amos.virtualledger.android.api.banking.HTTPBankingProvider;
import de.fau.amos.virtualledger.android.api.shared.CallWithToken;
import de.fau.amos.virtualledger.android.api.shared.RetrofitCallback;
import de.fau.amos.virtualledger.android.api.shared.TokenCallback;
import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class HTTPContactsProvider implements ContactsProvider{

    @SuppressWarnings("unused")
    private static final String TAG = HTTPBankingProvider.class.getSimpleName();

    private final RestApi restApi;
    private final CallWithToken callWithToken;

    public HTTPContactsProvider(final RestApi restApi, final CallWithToken callWithToken) {
        this.restApi = restApi;
        this.callWithToken = callWithToken;
    }

    @Override
    public Observable<List<Contact>> get() {

        final PublishSubject<List<Contact>> observable = PublishSubject.create();

        callWithToken.callWithToken(observable, new TokenCallback() {
            @Override
            public void onReceiveToken(final String token) {
                // got token
                restApi.getContacts(token).enqueue(new RetrofitCallback<>(observable));
            }
        });

        return observable;
    }

    @Override
    public Observable<StringApiModel> add(final Contact contact) {
        final PublishSubject<StringApiModel> observable = PublishSubject.create();

        callWithToken.callWithToken(observable, new TokenCallback() {
            @Override
            public void onReceiveToken(final String token) {
                // got token
                restApi.addContact(token, contact).enqueue(new RetrofitCallback<>(observable));
            }
        });

        return observable;
    }

    @Override
    public Observable<StringApiModel> delete(final Contact contact) {
        final PublishSubject<StringApiModel> observable = PublishSubject.create();

        callWithToken.callWithToken(observable, new TokenCallback() {
            @Override
            public void onReceiveToken(final String token) {
                // got token
                restApi.deleteContact(token, contact.getEmail()).enqueue(new RetrofitCallback<StringApiModel>(observable));
            }
        });

        return observable;
    }
}
