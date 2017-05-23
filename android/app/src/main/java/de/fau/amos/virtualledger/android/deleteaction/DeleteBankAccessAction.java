package de.fau.amos.virtualledger.android.deleteaction;

import android.app.Activity;
import android.widget.Toast;


import javax.inject.Inject;

import de.fau.amos.virtualledger.android.App;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.functions.BiConsumer;
import de.fau.amos.virtualledger.android.functions.Consumer;
import de.fau.amos.virtualledger.android.functions.Function;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;
import retrofit2.Retrofit;

/**
 * Created by sebastian on 21.05.17.
 * Action for deletion of a BankAccess.
 */

public class DeleteBankAccessAction implements Consumer<BankAccess>, BiConsumer<BankAccess,BankAccess>{

    private BankingProvider bankingProvider;
    private Activity activity;
    private Function<BankAccess,String> getName;

    public DeleteBankAccessAction(Activity activity, Function<BankAccess,String> getName, BankingProvider bankingProvider){
        this.getName = getName;
        this.activity = activity;

        // TODO refactor so inject works!!!
        this.bankingProvider = bankingProvider;
    }

    @Override
    public void accept(BankAccess bankAccess) {
        bankingProvider.deleteBankAccess(bankAccess.getId());
        Toast.makeText(activity, "Bank access deleted:\""+getName.apply(bankAccess)+"\"", Toast.LENGTH_LONG).show();
    }

    @Override
    public void accept(BankAccess item1, BankAccess item2) {
        this.accept(item1);
        if (item1 != item2){
            this.accept(item2);
        }
    }
}
