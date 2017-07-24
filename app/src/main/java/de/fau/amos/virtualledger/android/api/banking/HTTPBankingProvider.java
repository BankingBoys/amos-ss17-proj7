package de.fau.amos.virtualledger.android.api.banking;

import java.util.List;

import de.fau.amos.virtualledger.android.api.RestApi;
import de.fau.amos.virtualledger.android.api.shared.CallWithToken;
import de.fau.amos.virtualledger.android.api.shared.RetrofitCallback;
import de.fau.amos.virtualledger.android.api.shared.TokenCallback;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class HTTPBankingProvider implements BankingProvider {

    @SuppressWarnings("unused")
    private static final String TAG = HTTPBankingProvider.class.getSimpleName();

    private final RestApi restApi;
    private final CallWithToken callWithToken;

    public HTTPBankingProvider(final RestApi restApi, final CallWithToken callWithToken) {
        this.callWithToken = callWithToken;
        this.restApi = restApi;
    }

    @Override
    public Observable<List<BankAccess>> getBankingOverview() {
        final PublishSubject<List<BankAccess>> observable = PublishSubject.create();

        callWithToken.callWithToken(observable, new TokenCallback() {
            @Override
            public void onReceiveToken(final String token) {
                // got token
                restApi.getBankAccesses(token).enqueue(new RetrofitCallback<>(observable));
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
                restApi.getBookings(token, bankAccountSyncList).enqueue(new RetrofitCallback<>(observable));
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
                restApi.addBankAccess(token, bankAccessCredential).enqueue(new RetrofitCallback<>(observable));
            }
        });


        return observable;
    }

    @Override
    public Observable<StringApiModel> deleteBankAccess(final String accessId) {
        final PublishSubject<StringApiModel> observable = PublishSubject.create();

        callWithToken.callWithToken(observable, new TokenCallback() {
            @Override
            public void onReceiveToken(final String token) {
                // got token
                restApi.deleteBankAccess(token, accessId).enqueue(new RetrofitCallback<>(observable));
            }
        });


        return observable;
    }

    @Override
    public Observable<StringApiModel> deleteBankAccount(final String accessId, final String accountId) {
        final PublishSubject<StringApiModel> observable = PublishSubject.create();

        callWithToken.callWithToken(observable, new TokenCallback() {
            @Override
            public void onReceiveToken(final String token) {
                // got token
                restApi.deleteBankAccount(token, accessId, accountId).enqueue(new RetrofitCallback<>(observable));
            }
        });

        return observable;
    }
}
