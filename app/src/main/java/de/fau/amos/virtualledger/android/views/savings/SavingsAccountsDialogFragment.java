package de.fau.amos.virtualledger.android.views.savings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;

/**
 * Created by Simon on 27.06.2017.
 */

public class SavingsAccountsDialogFragment extends DialogFragment {

    private static final String nameTag = "ACCOUNS_SAVINGS_NAME";

    private String name = "testTitle";

    public static SavingsAccountsDialogFragment newInstance(String name) {

        SavingsAccountsDialogFragment savingsAccountsDialogFragment = new SavingsAccountsDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString(nameTag, name);
        savingsAccountsDialogFragment.setArguments(bundle);
        return savingsAccountsDialogFragment;
    }
    
}
