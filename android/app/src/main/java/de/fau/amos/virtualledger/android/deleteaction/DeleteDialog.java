package de.fau.amos.virtualledger.android.deleteaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import de.fau.amos.virtualledger.android.functions.BiConsumer;
import de.fau.amos.virtualledger.android.functions.BiFunction;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Created by sebastian on 21.05.17.
 * Shows a deletion Dialog. Shows the item name extracted by the getName function.
 * If the User approves the deletion, the approvedAction will be executed
 */

public class DeleteDialog {

    private Activity listenedObject;
    private BankAccess bankAccess;
    private BankAccount bankAccount;
    private BiFunction<BankAccess,BankAccount, String> getName;
    private BiConsumer<BankAccess,BankAccount> approvedAction;


    /**
     * Create a dialogue for deletion confirmatrion
     * @param listenedObject = the referenced activity
     * @param bankAccess = the model object which whould be deleted or not.
     *                      The getName function extracts the shown name out of this object
     * @param getName = a function that creates the name that is presented on the dialog out of the modelobject
     * @param approvedAction = the action that is fired if the user approves
     */
    public DeleteDialog(Activity listenedObject, BankAccess bankAccess, BankAccount bankAccount, BiFunction<BankAccess,BankAccount, String> getName, BiConsumer<BankAccess,BankAccount> approvedAction) {
        this.listenedObject = listenedObject;
        this.bankAccess = bankAccess;
        this.bankAccount = bankAccount;
        this.getName = getName;
        this.approvedAction = approvedAction;
    }

    /**
     * shows the popup
     */
    public void show() {
        AlertDialog.Builder alert = new AlertDialog.Builder(listenedObject);
        alert.setTitle("DELETE CONFIRMATION");
        alert.setMessage("Are you sure to delete " + this.getName.apply(bankAccess,bankAccount));
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
