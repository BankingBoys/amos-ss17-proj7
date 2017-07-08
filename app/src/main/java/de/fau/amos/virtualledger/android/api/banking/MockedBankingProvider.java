package de.fau.amos.virtualledger.android.api.banking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import de.fau.amos.virtualledger.dtos.Booking;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by sebastian on 14.05.17.
 */

public class MockedBankingProvider implements BankingProvider {

    public static final int DELAY_TIME_MILLISECONDS = 300;
    private String token = "";


    @Override
    public Observable<List<BankAccess>> getBankingOverview() {

        List<BankAccount> accountList = new ArrayList<BankAccount>();
        BankAccount account = new BankAccount("1.1dummy account id", "1.1 dummy account name1 ", 100);
        accountList.add(account);
        account = new BankAccount("1.2dummy account id", "1.2 dummy account name1 ", 100);
        accountList.add(account);

        BankAccess access = new BankAccess("1dummy access id", " 1 dummy access name ","nochn string","string!!", accountList);
        access.setBankaccounts(accountList);

        final List<BankAccess> accessList = new ArrayList<BankAccess>();
        accessList.add(access);


        accountList = new ArrayList<BankAccount>();
        account = new BankAccount("2.1dummy account id", "2.1 dummy account name1 ", 100);
        accountList.add(account);
        account = new BankAccount("2.2dummy account id", "2.2 dummy account name1 ", 100);
        accountList.add(account);

        access = new BankAccess("2dummy access id", " 2 dummy access name ", "nochn string","string!!", accountList);
        access.setBankaccounts(accountList);
        accessList.add(access);


        final PublishSubject observable = PublishSubject.create();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //Wait until subject is subscribed
                    Thread.sleep(DELAY_TIME_MILLISECONDS);}
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

    @Override
    public Observable<BankAccountSyncResult> getBankingTransactions(List<BankAccountSync> bankAccountSyncList) {
        final PublishSubject observable = PublishSubject.create();

        final List<BankAccountBookings> bankAccountBookingsList = new ArrayList<BankAccountBookings>();

        for(int i = 1; i <= 2; ++i) {

            BankAccountBookings bankAccountBookings = new BankAccountBookings();
            bankAccountBookings.setBankaccessid("DummyAccessId" + i);
            bankAccountBookings.setBankaccountid("DummyAccountId" + i);

            List<Booking> bookingList = new ArrayList<Booking>();
            Booking booking = new Booking();
            booking.setDate(new Date());
            booking.setAmount(200.00);
            bookingList.add(booking);
            booking = new Booking();
            booking.setDate(new Date());
            booking.setAmount(-150.00);
            bookingList.add(booking);

            bankAccountBookings.setBookings(bookingList);
            bankAccountBookingsList.add(bankAccountBookings);
        }
        BankAccountSyncResult bankAccountSyncResult = new BankAccountSyncResult();
        bankAccountSyncResult.setBankaccountbookings(bankAccountBookingsList);

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //Wait until subject is subscribed
                    Thread.sleep(DELAY_TIME_MILLISECONDS);}
                catch (Exception e){

                }
                // publish accounts to subject
                observable.onNext(bankAccountBookingsList);
                observable.onComplete();
            }
        });
        th.start();

        return observable;
    }

    @Override
    public Observable<BankAccess> addBankAccess(BankAccessCredential bankAccessCredential) {
        final PublishSubject observable = PublishSubject.create();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //Wait until subject is subscribed
                    Thread.sleep(DELAY_TIME_MILLISECONDS);}
                catch (Exception e){

                }
                // publish accounts to subject
                observable.onNext("BankAccess was added! DummyImplementation...");
                observable.onComplete();
            }
        });
        th.start();

        return observable;
    }

    @Override
    public Observable<Void> deleteBankAccess(String accessId) {
        final PublishSubject observable = PublishSubject.create();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //Wait until subject is subscribed
                    Thread.sleep(DELAY_TIME_MILLISECONDS);}
                catch (Exception e){

                }
                // publish accounts to subject
                observable.onNext("BankAccess was deleted! DummyImplementation...");
                observable.onComplete();
            }
        });
        th.start();

        return observable;
    }

    @Override
    public Observable<Void> deleteBankAccount(String accessId, String accountId) {
        final PublishSubject observable = PublishSubject.create();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //Wait until subject is subscribed
                    Thread.sleep(DELAY_TIME_MILLISECONDS);}
                catch (Exception e){

                }
                // publish accounts to subject
                observable.onNext("BankAccount was deleted! DummyImplementation...");
                observable.onComplete();
            }
        });
        th.start();

        return observable;
    }
}
