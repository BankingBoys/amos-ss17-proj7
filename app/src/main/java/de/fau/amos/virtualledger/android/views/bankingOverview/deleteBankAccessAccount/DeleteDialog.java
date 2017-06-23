package de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.functions.BiConsumer;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.functions.BiFunction;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Created by sebastian on 21.05.17.
 * Shows a deletion Dialog. Shows the item name extracted by the getName function.
 * If the User approves the deletion, the approvedAction will be executed
 */

public class DeleteDialog {

    private BankAccess bankAccess;
    private BankAccount bankAccount;
    private BiFunction<BankAccess, BankAccount, String> getName;
    private BiConsumer<BankAccess, BankAccount> approvedAction;
    private Context context;


    /**
     * @param bankAccess     = the bank access
     * @param bankAccount    = the bank account if neccessary
     * @param getName        = the name extractor to extract the name of the access and the account
     * @param approvedAction = the action that is executed when the delete option is approved
     */
    public DeleteDialog(Context context, BankAccess bankAccess, BankAccount bankAccount, BiFunction<BankAccess, BankAccount, String> getName, BiConsumer<BankAccess, BankAccount> approvedAction) {
        this.bankAccess = bankAccess;
        this.bankAccount = bankAccount;
        this.getName = getName;
        this.approvedAction = approvedAction;
        this.context = context;
    }

    /**
     * shows the popup
     */
    public void show() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("DELETE CONFIRMATION");
        alert.setMessage("Are you sure to delete " + this.getName.apply(bankAccess, bankAccount)+"?");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                approvedAction.accept(bankAccess, bankAccount);

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
