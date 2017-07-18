package de.fau.amos.virtualledger.android.views.savings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.dtos.BankAccountIdentifier;
import de.fau.amos.virtualledger.dtos.Contact;

/**
 * Created by Simon on 27.06.2017.
 */

public class SavingsAccountsDialogFragment extends DialogFragment {

    private static final String nameTag = "ACCOUNTS_SAVINGS_NAME";
    private static final String bankAccountListTag = "ACCOUNTS_SAVINGS_BANK_ACCOUNTS_LIST_NAME";
    private static final String contactsListTag= "ACCOUNS_SAVINGS_CONTACTS_LIST_NAME";

    List<BankAccountIdentifier> bankAccountList = new ArrayList<>();
    List<Contact> contactList = new ArrayList<>();

    private String name = "";

    public static SavingsAccountsDialogFragment newInstance(String savingName, ArrayList<BankAccountIdentifier> bankaccounts, ArrayList<Contact> contactList) {

        SavingsAccountsDialogFragment savingsAccountsDialogFragment = new SavingsAccountsDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString(nameTag, savingName);
        bundle.putSerializable(bankAccountListTag, bankaccounts);
        bundle.putSerializable(contactsListTag, contactList);
        savingsAccountsDialogFragment.setArguments(bundle);
        return savingsAccountsDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(name).setMessage("Person 1 \nPerson 2 \nPerson 3 \nPerson 4");
        //TODO: change to real names and bank account
        return builder.create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.name = getArguments().getString(nameTag);
        this.bankAccountList = (getArguments().getSerializable(bankAccountListTag)) instanceof ArrayList ? ((ArrayList<BankAccountIdentifier>) getArguments().getSerializable(bankAccountListTag)) : null;
        this.contactList = (getArguments().getSerializable(contactsListTag)) instanceof ArrayList ? ((ArrayList<Contact>) getArguments().getSerializable(contactsListTag)) : null;
    }
}
