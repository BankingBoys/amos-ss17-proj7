package de.fau.amos.virtualledger.android.api.banking;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by sebastian on 14.05.17.
 */

public class MockedBankingProvider implements BankingProvider {

    private String token = "";


    @Override
    public Observable<List<BankAccess>> getBankingOverview() {

        BankAccount account = new BankAccount("dummy account id", "dummy account name", 100);
        List<BankAccount> accountList = new ArrayList<BankAccount>();
        accountList.add(account);
        BankAccess access = new BankAccess("dummy access id", "dummy access name", accountList);
        access.setBankaccounts(accountList);

        final List<BankAccess> accessList = new ArrayList<BankAccess>();
        accessList.add(access);

        final PublishSubject observable = PublishSubject.create();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                Thread.sleep(500);}
                catch (Exception e){

                }
                observable.onNext(accessList);
                observable.onComplete();
            }
        });
        th.start();

        return observable;
    }


}
