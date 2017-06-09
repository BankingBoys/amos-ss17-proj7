package de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount;

import android.app.Activity;
import android.widget.Toast;

import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.functions.BiConsumer;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.functions.BiFunction;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sebastian on 21.05.17.
 * Action for deletion of a BankAccess.
 */

public class DeleteBankAccessAction implements BiConsumer<BankAccess, BankAccount> {

    private BankingProvider bankingProvider;
    private Activity activity;
    private BiFunction<BankAccess, BankAccount, String> getName;
    private BankingDataManager bankingDataManager;

    public DeleteBankAccessAction(Activity activity, BiFunction<BankAccess, BankAccount, String> getName, BankingProvider bankingProvider, final BankingDataManager bankingDataManager) {
        this.getName = getName;
        this.activity = activity;

        // TODO refactor so inject works!!!
        this.bankingProvider = bankingProvider;
        this.bankingDataManager = bankingDataManager;
    }

    @Override
    public void accept(final BankAccess bankAccess, final BankAccount bankAccount) {
        bankingProvider.deleteBankAccess(bankAccess.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        bankingDataManager.sync();
                        Toast.makeText(activity, "Bank access deleted:\""  + getName.apply(bankAccess, bankAccount) + "\"", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(activity, "Bank access could not be deleted:\""  + getName.apply(bankAccess, bankAccount) + "\"", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        /*Toast.makeText(activity, "Bank access deleted:\"" + getName.apply(bankAccess, bankAccount) + "\"", Toast.LENGTH_LONG).show();*/
    }
}
