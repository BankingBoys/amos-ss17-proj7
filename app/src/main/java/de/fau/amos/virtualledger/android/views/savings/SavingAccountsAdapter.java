package de.fau.amos.virtualledger.android.views.savings;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.model.SavingsAccount;

public class SavingAccountsAdapter extends ArrayAdapter<SavingsAccount> {

    private Activity activity;

    public SavingAccountsAdapter(Activity activity, int layout, List<SavingsAccount> data) {
        super(activity, layout, data);
        data.add(this.dummySavingsAccount());
        data.add(this.dummySavingsAccount());
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SavingsAccount transaction = super.getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.saving_accounts_list_item, parent, false);
        }
        SavingsAccount savingsAccount = this.getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.id_name);
        name.setText(savingsAccount.getName());

        TextView currentBalance = (TextView) convertView.findViewById(R.id.id_current_balance);
        currentBalance.setText(Math.round(savingsAccount.getCurrentbalance()) + "");

        TextView goalBalance = (TextView) convertView.findViewById(R.id.id_goal_balance);
        goalBalance.setText(Math.round(savingsAccount.getGoalbalance()) + "");

        TextView daysLeft = (TextView) convertView.findViewById(R.id.id_time_left);
        daysLeft.setText(savingsAccount.daysLeft() + " days left");

        return convertView;
    }

    private SavingsAccount dummySavingsAccount() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = sdf.parse("15/12/2017");
        } catch (Exception e) {

        }
        SavingsAccount savingsAccount = new SavingsAccount((int) Math.random() * 100, "Sparschwein", 300, 80, d);
        return savingsAccount;
    }

}
