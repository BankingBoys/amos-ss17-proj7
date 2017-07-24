package de.fau.amos.virtualledger.android.views.savings.Delete;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import de.fau.amos.virtualledger.dtos.SavingsAccount;

/**
 * Created by Simon on 24.07.2017.
 */

public class SavingsAccountDeleteDialog {

    private Context context;
    private SavingsAccount savingsAccount;

    public SavingsAccountDeleteDialog(Context context, SavingsAccount savingsAccount) {
        this.context = context;
        this.savingsAccount = savingsAccount;
    }

    public void show() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("DELETE CONFIRMATION");
        alert.setMessage("Are you sure to delete " + this.savingsAccount.getName()+" from your savings List?");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SavingsAccountDeleteAction deleteAction = new SavingsAccountDeleteAction(context, savingsAccount);
                deleteAction.delete();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();

    }
}
