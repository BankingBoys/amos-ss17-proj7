package de.fau.amos.virtualledger.android.api.banking;

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
import retrofit2.Retrofit;

public class HTTPBankingProvider implements BankingProvider {

    @SuppressWarnings("unused")
    private static final String TAG = "HTTPBankingProvider";

    private final Retrofit retrofit;
    private final CallWithToken callWithToken;

    public HTTPBankingProvider(final Retrofit retrofit, final CallWithToken callWithToken) {
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
                retrofit.create(Restapi.class).deleteBankAccess(token, accessId).enqueue(new RetrofitCallback<>(observable));
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
                retrofit.create(Restapi.class).deleteBankAccount(token, accessId, accountId).enqueue(new RetrofitCallback<>(observable));
            }
        });

        return observable;
    }
}
