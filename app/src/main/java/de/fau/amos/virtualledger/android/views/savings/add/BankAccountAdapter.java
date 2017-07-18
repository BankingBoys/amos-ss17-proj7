package de.fau.amos.virtualledger.android.views.savings.add;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Created by Simon on 17.07.2017.
 */

public class BankAccountAdapter extends ArrayAdapter<BankAccount> {

    private BankAccountSelectedListener bankAccountListener;

    public BankAccountAdapter (Activity activity, int layout, ArrayList<BankAccount> bankAccounts, BankAccountSelectedListener bankAccountListener) {
        super(activity, layout, bankAccounts);
        this.bankAccountListener = bankAccountListener;
    }
}
