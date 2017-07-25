package de.fau.amos.virtualledger.android.views.savings.Delete;

import android.content.Context;

import javax.inject.Inject;

import de.fau.amos.virtualledger.android.api.sync.Toaster;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.SavingsAccountsDataManager;
import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.dtos.SavingsAccount;

/**
 * Created by Simon on 24.07.2017.
 */

public class SavingsAccountDeleteAction {
    @Inject
    SavingsAccountsDataManager savingsAccountsDataManager;
    private SavingsAccount savingsAccount;
    Toaster toaster;

    public SavingsAccountDeleteAction(Context context, SavingsAccount savingsAccount) {
        ((App) context.getApplicationContext()).getNetComponent().inject(this);
        this.savingsAccount = savingsAccount;
        toaster = new Toaster(context.getApplicationContext())
                .pushConceptualErrorMessage("User not found").pushSuccessMessage("Saving: " + savingsAccount.getName() + " was successfully deleted");
    }

    public void delete() {
        savingsAccountsDataManager.delete(savingsAccount, toaster);
    }
}
