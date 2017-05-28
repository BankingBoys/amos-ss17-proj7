package de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import javax.inject.Inject;

import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.functions.BiConsumer;
import de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.functions.BiFunction;
import de.fau.amos.virtualledger.android.menu.MainMenu;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sebastian on 22.05.17.
 * Action of deletion of an BankAccount
 */

public class DeleteBankAccountAction implements BiConsumer<BankAccess, BankAccount> {

    // injected by setter
    private BankingProvider bankingProvider;

    private Activity activity;
    private BiFunction<BankAccess, BankAccount, String> getName;

    public DeleteBankAccountAction(Activity activity, BiFunction<BankAccess, BankAccount, String> getName, BankingProvider bankingProvider) {
        this.getName = getName;
        this.activity = activity;


        // TODO refactor so inject works!!!
        this.bankingProvider = bankingProvider;
    }

    @Override
    public void accept(final BankAccess bankAccess,final BankAccount bankAccount) {
        bankingProvider.deleteBankAccount(bankAccess.getId(), bankAccount.getBankid())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Toast.makeText(activity, "Bank account deleted:\"" + getName.apply(bankAccess, bankAccount) + "\"", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(activity, MainMenu.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(activity, "Bank account could not be deleted:\"" + getName.apply(bankAccess, bankAccount) + "\"", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Inject
    public void setBankingProvider(BankingProvider bankingProvider) {
        this.bankingProvider = bankingProvider;
    }
}
