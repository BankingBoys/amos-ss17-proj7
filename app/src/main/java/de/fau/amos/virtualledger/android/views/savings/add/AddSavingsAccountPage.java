package de.fau.amos.virtualledger.android.views.savings.add;

import android.support.v4.app.Fragment;

import java.util.logging.Logger;

import de.fau.amos.virtualledger.android.model.SavingsAccount;

public abstract class AddSavingsAccountPage extends Fragment {
    abstract void fillInData(SavingsAccount addSavingsAccountResult);

    boolean navigatePossible() {
        return true;
    }

    void consumeDataModel(SavingsAccount account) {
        Logger.getLogger(this.getClass().getCanonicalName()).info("No consuming datamodel: " + account + toString());
    }
}
