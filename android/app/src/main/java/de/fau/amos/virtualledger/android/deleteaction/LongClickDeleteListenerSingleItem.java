package de.fau.amos.virtualledger.android.deleteaction;

import android.app.Activity;
import android.view.View;

import de.fau.amos.virtualledger.android.functions.BiConsumer;
import de.fau.amos.virtualledger.android.functions.BiFunction;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Long click lisntens to an single element
 * Created by sebastian on 21.05.17.
 */

public class LongClickDeleteListenerSingleItem implements View.OnLongClickListener {

    private final Activity listenedObject;
    private BankAccess element;
    private BankAccount element2;
    private BiFunction<BankAccess,BankAccount,String> getName;
    private BiConsumer<BankAccess,BankAccount>  approvedAction;

    /**
     * Long Click listens to an single element
     * @param listenedObject = the referenced activity
     * @param element = the model object, that is presented in the view
     * @param getName = creates the name shown in the dialog out of a single model element
     * @param approvedAction = ction that is fired after user approves the shown delete dialog
     */
    public LongClickDeleteListenerSingleItem(Activity listenedObject, BankAccess element,BankAccount element2, BiFunction<BankAccess,BankAccount,String> getName, BiConsumer<BankAccess,BankAccount> approvedAction) {
        this.listenedObject = listenedObject;
        this.element = element;
        this.element2 = element2;
        this.getName = getName;
        this.approvedAction = approvedAction;
    }


    @Override
    public boolean onLongClick(View v) {
        DeleteDialog tDeleteDialog = new DeleteDialog(listenedObject,element, element2, getName, approvedAction);
        tDeleteDialog.show();
        return true;
    }
}
