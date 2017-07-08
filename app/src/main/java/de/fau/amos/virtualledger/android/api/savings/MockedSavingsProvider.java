package de.fau.amos.virtualledger.android.api.savings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.fau.amos.virtualledger.android.model.SavingsAccount;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static de.fau.amos.virtualledger.android.api.banking.MockedBankingProvider.DELAY_TIME_MILLISECONDS;


public class MockedSavingsProvider implements SavingsProvider {


    @Override
    public Observable<List<SavingsAccount>> getSavingAccounts() {
        final List<SavingsAccount> savingsAccountList = new ArrayList<>();

        SavingsAccount savingsAccount = new SavingsAccount("1", "dummy1", 100.00, 12.23, new Date(), new Date());
        savingsAccountList.add(savingsAccount);

        final PublishSubject observable = PublishSubject.create();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Wait until subject is subscribed
                    Thread.sleep(DELAY_TIME_MILLISECONDS);
                } catch (Exception e) {

                }
                // publish accounts to subject
                observable.onNext(savingsAccountList);
                observable.onComplete();
            }
        });
        th.start();

        return observable;
    }

    @Override
    public Observable<Void> addSavingAccount(SavingsAccount savingsAccount) {
        final PublishSubject observable = PublishSubject.create();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Wait until subject is subscribed
                    Thread.sleep(DELAY_TIME_MILLISECONDS);
                } catch (Exception e) {

                }
                // publish accounts to subject
                observable.onNext("SavingsAccount was added! DummyImplementation...");
                observable.onComplete();
            }
        });
        th.start();

        return observable;
    }
}
