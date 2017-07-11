package de.fau.amos.virtualledger.android.api.savings;


import java.util.List;

import de.fau.amos.virtualledger.android.api.RestApi;
import de.fau.amos.virtualledger.android.api.shared.CallWithToken;
import de.fau.amos.virtualledger.android.api.shared.RetrofitCallback;
import de.fau.amos.virtualledger.android.api.shared.TokenCallback;
import de.fau.amos.virtualledger.android.model.SavingsAccount;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class HTTPSavingsProvider implements SavingsProvider {

    @SuppressWarnings("unused")
    private static final String TAG = HTTPSavingsProvider.class.getSimpleName();

    private final RestApi restApi;
    private final CallWithToken callWithToken;


    public HTTPSavingsProvider(final RestApi restApi, final CallWithToken callWithToken) {
        this.restApi = restApi;
        this.callWithToken = callWithToken;
    }


    @Override
    public Observable<List<SavingsAccount>> get() {
        final PublishSubject<List<SavingsAccount>> observable = PublishSubject.create();

        callWithToken.callWithToken(observable, new TokenCallback() {
            @Override
            public void onReceiveToken(final String token) {
                // got token
                restApi.getSavingAccounts(token).enqueue(new RetrofitCallback<>(observable));
            }
        });

        return observable;
    }

    @Override
    public Observable<String> add(final SavingsAccount savingsAccount) {

        final PublishSubject<String> observable = PublishSubject.create();

        callWithToken.callWithToken(observable, new TokenCallback() {
            @Override
            public void onReceiveToken(final String token) {
                // got token
                restApi.addSavingAccounts(token, savingsAccount).enqueue(new RetrofitCallback<>(observable));
            }
        });

        return observable;
    }

}
