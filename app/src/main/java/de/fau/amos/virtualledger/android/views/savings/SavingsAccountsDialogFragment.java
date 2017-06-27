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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(name).setMessage("Person 1 \nPerson 2 \nPerson 3 \nPerson 4");

        return builder.create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.name = getArguments().getString(nameTag);
    }
}
