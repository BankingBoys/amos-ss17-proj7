package de.fau.amos.virtualledger.android.deleteaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import de.fau.amos.virtualledger.android.functions.BiConsumer;
import de.fau.amos.virtualledger.android.functions.Consumer;
import de.fau.amos.virtualledger.android.functions.Function;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Created by sebastian on 21.05.17.
 * Shows a deletion Dialog. Shows the item name extracted by the getName function.
 * If the User approves the deletion, the approvedAction will be executed
 */

public class DeleteDialog<R,T> {

    private Activity listenedObject;
    private R listenedModel;
    private T listenedModel2;
    private Function<T, String> getName;
    private BiConsumer<R,T> approvedAction;


    /**
     * Create a dialogue for deletion confirmatrion
     * @param listenedObject = the referenced activity
     * @param listenedModel = the model object which whould be deleted or not.
     *                      The getName function extracts the shown name out of this object
     * @param getName = a function that creates the name that is presented on the dialog out of the modelobject
     * @param approvedAction = the action that is fired if the user approves
     */
    public DeleteDialog(Activity listenedObject, R listenedModel,T listenedModel2, Function<T, String> getName, BiConsumer<R,T> approvedAction) {
        this.listenedObject = listenedObject;
        this.listenedModel = listenedModel;
        this.listenedModel2 = listenedModel2;
        this.getName = getName;
        this.approvedAction = approvedAction;
    }

    /**
     * shows the popup
     */
    public void show() {
        AlertDialog.Builder alert = new AlertDialog.Builder(listenedObject);
        alert.setTitle("DELETE CONFIRMATION");
        alert.setMessage("Are you sure to delete " + this.getName.apply(listenedModel2));
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                approvedAction.accept(listenedModel,listenedModel2);

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
