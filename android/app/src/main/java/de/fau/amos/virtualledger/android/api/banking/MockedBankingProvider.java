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

    public static final int DELAY_TIME_MILLISECUNDS = 300;
    private String token = "";


    @Override
    public Observable<List<BankAccess>> getBankingOverview() {

        List<BankAccount> accountList = new ArrayList<BankAccount>();
        BankAccount account = new BankAccount("1.1dummy account id", "1.1 dummy account name1 ", 100);
        accountList.add(account);
        account = new BankAccount("1.2dummy account id", "1.2 dummy account name1 ", 100);
        accountList.add(account);

        BankAccess access = new BankAccess("1dummy access id", " 1 dummy access name ", accountList);
        access.setBankaccounts(accountList);

        final List<BankAccess> accessList = new ArrayList<BankAccess>();
        accessList.add(access);


        accountList = new ArrayList<BankAccount>();
         account = new BankAccount("2.1dummy account id", "2.1 dummy account name1 ", 100);
        accountList.add(account);
        account = new BankAccount("2.2dummy account id", "2.2 dummy account name1 ", 100);
        accountList.add(account);

         access = new BankAccess("2dummy access id", " 2 dummy access name ", accountList);
        access.setBankaccounts(accountList);
        accessList.add(access);


        final PublishSubject observable = PublishSubject.create();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //Wait until subject is subscribed
                    Thread.sleep(DELAY_TIME_MILLISECUNDS);}
                catch (Exception e){

                }
                // publish accounts to subject
                observable.onNext(accessList);
                observable.onComplete();
            }
        });
        th.start();

        return observable;
    }


}
