package de.fau.amos.virtualledger.android.deleteaction;

import android.app.Activity;
import android.widget.Toast;


import de.fau.amos.virtualledger.android.functions.Consumer;
import de.fau.amos.virtualledger.dtos.BankAccess;
import retrofit2.Retrofit;

/**
 * Created by sebastian on 21.05.17.
 * Action for deletion of a BankAccess.
 */

public class DeleteAccessAction implements Consumer<BankAccess>{

    private Activity activity;
    private Retrofit retrofit;

    public DeleteAccessAction(Activity activity){
        this.activity = activity;
    }

    @Override
    public void accept(BankAccess bankAccess) {
        //TODO Delete Action
        Toast.makeText(activity, "Bank access deleted:\""+bankAccess.getName()+"\"", Toast.LENGTH_LONG).show();
    }
}
