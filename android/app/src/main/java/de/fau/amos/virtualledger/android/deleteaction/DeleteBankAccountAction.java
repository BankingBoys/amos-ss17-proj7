package de.fau.amos.virtualledger.android.deleteaction;

import android.app.Activity;
import android.widget.Toast;

import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.android.functions.Function;
import de.fau.amos.virtualledger.android.functions.Consumer;
import retrofit2.Retrofit;

/**
 * Created by sebastian on 22.05.17.
 * Action of deletion of an BankAccount
 */

public class DeleteBankAccountAction implements Consumer<BankAccount> {

    private Activity activity;
    private Function<BankAccount, String> getName;
    private Retrofit retrofit;

    public DeleteBankAccountAction(Activity activity, Function<BankAccount, String> getName){
        this.getName = getName;
        this.activity = activity;
    }

    @Override
    public void accept(BankAccount item) {
        //TODO Delete Action
        Toast.makeText(activity, "Bank account deleted:\""+getName.apply(item)+"\"", Toast.LENGTH_LONG).show();
    }
}
