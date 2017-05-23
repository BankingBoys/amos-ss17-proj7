package de.fau.amos.virtualledger.android.deleteaction;

import android.app.Activity;
import android.widget.Toast;

import javax.inject.Inject;

import de.fau.amos.virtualledger.android.App;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.functions.BiConsumer;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.android.functions.Function;
import de.fau.amos.virtualledger.android.functions.Consumer;

/**
 * Created by sebastian on 22.05.17.
 * Action of deletion of an BankAccount
 */

public class DeleteBankAccountAction implements BiConsumer<BankAccess,BankAccount> {

    // injected by setter
    private BankingProvider bankingProvider;

    private Activity activity;
    private Function<BankAccount, String> getName;

    public DeleteBankAccountAction(Activity activity, Function<BankAccount, String> getName, BankingProvider bankingProvider){
        this.getName = getName;
        this.activity = activity;


        // TODO refactor so inject works!!!
        this.bankingProvider = bankingProvider;
    }

    @Override
    public void accept(BankAccess bankAccess, BankAccount bankAccount) {
        bankingProvider.deleteBankAccount(bankAccess.getId(), bankAccount.getBankid());
        Toast.makeText(activity, "Bank account deleted:\""+getName.apply(bankAccount)+"\"", Toast.LENGTH_LONG).show();
    }

    @Inject
    public void setBankingProvider(BankingProvider bankingProvider)
    {
        this.bankingProvider = bankingProvider;
    }
}
