package de.fau.amos.virtualledger.android.data;

import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by sebastian on 04.07.17.
 */

public class BankingTransactionsObservable extends Observable<BankAccountSyncResult> {

    private Observer<? super BankAccountSyncResult> observer;

    @Override
    protected void subscribeActual(Observer<? super BankAccountSyncResult> observer) {
        this.observer = observer;
    }

    public void notifyOnComplete() {
        this.observer.onComplete();
    }

    public void notifyNext() {
        this.observer.onNext(new BankAccountSyncResult());
    }
}
