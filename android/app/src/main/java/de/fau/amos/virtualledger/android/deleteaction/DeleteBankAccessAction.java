package de.fau.amos.virtualledger.android.deleteaction;

import android.app.Activity;
import android.widget.Toast;

import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.functions.BiConsumer;
import de.fau.amos.virtualledger.android.functions.BiFunction;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Created by sebastian on 21.05.17.
 * Action for deletion of a BankAccess.
 */

public class DeleteBankAccessAction implements BiConsumer<BankAccess,BankAccount>{

    private BankingProvider bankingProvider;
    private Activity activity;
    private BiFunction<BankAccess,BankAccount,String> getName;

    public DeleteBankAccessAction(Activity activity, BiFunction<BankAccess,BankAccount,String> getName, BankingProvider bankingProvider){
        this.getName = getName;
        this.activity = activity;

        // TODO refactor so inject works!!!
        this.bankingProvider = bankingProvider;
    }

    @Override
    public void accept(BankAccess bankAccess, BankAccount bankAccount) {
        bankingProvider.deleteBankAccess(bankAccess.getId());
        Toast.makeText(activity, "Bank access deleted:\""+getName.apply(bankAccess,bankAccount)+"\"", Toast.LENGTH_LONG).show();
    }
}
