package de.fau.amos.virtualledger.android.views.savings.add;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.R;
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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final BankAccount account = super.getItem(position);
        final String accountName = account.getName();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bank_account_list_item, parent, false);
        }
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.bankAccountCheckbox);
        checkBox.setText(accountName);
        logger().info("BankAccount Name" + accountName);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    BankAccountAdapter.this.bankAccountListener.selectBankAccount(account);
                } else {
                    BankAccountAdapter.this.bankAccountListener.deselectBankAccount(account);
                }
            }
        });

        return convertView;
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName());
    }
}
