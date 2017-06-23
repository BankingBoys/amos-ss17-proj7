package de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount;

import android.app.Activity;
import android.view.View;

import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.functions.BiConsumer;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.functions.BiFunction;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Long click lisntens to an single element
 * Created by sebastian on 21.05.17.
 */

public class LongClickDeleteListenerSingleItem implements View.OnLongClickListener {

    private final Activity listenedObject;
    private BankAccess bankAccess;
    private BankAccount bankAccount;
    private BiFunction<BankAccess, BankAccount, String> getName;
    private BiConsumer<BankAccess, BankAccount> approvedAction;

    /**
     * @param listenedObject the activity
     * @param bankAccess     the bank access for the bank account
     * @param bankAccount    the bank account that may be deleted
     * @param getName        generates the name out of the bank access and the bank account
     * @param approvedAction the delete action that may be fired
     */
    public LongClickDeleteListenerSingleItem(Activity listenedObject, BankAccess bankAccess, BankAccount bankAccount, BiFunction<BankAccess, BankAccount, String> getName, BiConsumer<BankAccess, BankAccount> approvedAction) {
        this.listenedObject = listenedObject;
        this.bankAccess = bankAccess;
        this.bankAccount = bankAccount;
        this.getName = getName;
        this.approvedAction = approvedAction;
    }


    @Override
    public boolean onLongClick(View v) {
        DeleteDialog tDeleteDialog = new DeleteDialog(v.getContext(), bankAccess, bankAccount, getName, approvedAction);
        tDeleteDialog.show();
        return true;
    }
}
