package de.fau.amos.virtualledger.android.views.savings.add;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.model.SavingsAccount;

public class SavingAccountsAdapter extends ArrayAdapter<SavingsAccount> {

    private Activity activity;

    public SavingAccountsAdapter(Activity activity, int layout, ArrayList<SavingsAccount> data) {
        super(activity, layout, data);
        data.add(new SavingsAccount());
        data.add(new SavingsAccount());
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SavingsAccount transaction = super.getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.saving_accounts_list_item, parent, false);
        }

        return convertView;
    }


}
