package de.fau.amos.virtualledger.android.data;

import java.util.ArrayList;
import java.util.List;

import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by sebastian on 04.07.17.
 */

public class StubbedBankingProvider extends Observable<List<BankAccess>> implements BankingProvider {
    private Observer<? super List<BankAccess>> observer;
    private BankingTransactionsObservable bankingTransactionsObservable;
    private ArrayList<BankAccess> bankAccess = new ArrayList<>();

    public StubbedBankingProvider(){

    }
    public StubbedBankingProvider(BankAccess access){
        this.bankAccess.add(access);
    }


    @Override
    public Observable<List<BankAccess>> getBankingOverview() {
        return this;
    }

    @Override
    public Observable<BankAccountSyncResult> getBankingTransactions(List<BankAccountSync> bankAccountSyncList) {
        this.bankingTransactionsObservable = new BankingTransactionsObservable();
        return this.bankingTransactionsObservable;
    }

    @Override
    public Observable<BankAccess> addBankAccess(BankAccessCredential bankAccessCredential) {
        return null;
    }

    @Override
    public Observable<StringApiModel> deleteBankAccess(String accessId) {
        return Observable.just(generateStringApiModel());
    }

    @Override
    public Observable<StringApiModel> deleteBankAccount(String accessId, String accountId) {
        return Observable.just(generateStringApiModel());
    }

    @Override
    protected void subscribeActual(Observer<? super List<BankAccess>> observer) {
        this.observer = observer;
    }

    public void notifyOnComplete() {
        this.observer.onComplete();
    }

    public void notifyNext(){
        this.observer.onNext(this.bankAccess);
        this.bankingTransactionsObservable.notifyNext();
    }

    private StringApiModel generateStringApiModel() {
        StringApiModel stringApiModel = new StringApiModel();
        stringApiModel.setData("test123test");
        return stringApiModel;
    }
}
