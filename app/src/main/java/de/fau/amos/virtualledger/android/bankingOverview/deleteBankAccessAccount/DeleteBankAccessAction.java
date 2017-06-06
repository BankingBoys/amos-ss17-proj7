package de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
 * Created by sebastian on 21.05.17.
 * Action for deletion of a BankAccess.
 */

public class DeleteBankAccessAction implements BiConsumer<BankAccess, BankAccount> {

    private BankingProvider bankingProvider;
    private Activity activity;
    private BiFunction<BankAccess, BankAccount, String> getName;

    public DeleteBankAccessAction(Activity activity, BiFunction<BankAccess, BankAccount, String> getName, BankingProvider bankingProvider) {
        this.getName = getName;
        this.activity = activity;

        // TODO refactor so inject works!!!
        this.bankingProvider = bankingProvider;
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
                        Toast.makeText(activity, "Bank access deleted:\""  + getName.apply(bankAccess, bankAccount) + "\"", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(activity, MainMenu.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("startingFragment", 1);
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                        activity.finish();
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
