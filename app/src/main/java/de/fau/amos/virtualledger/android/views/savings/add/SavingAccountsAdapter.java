package de.fau.amos.virtualledger.android.views.savings.add;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        SavingsAccount savingsAccount = this.getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.id_name);
        name.setText(savingsAccount.getName());

        TextView currentBalance = (TextView) convertView.findViewById(R.id.id_current_balance);
        currentBalance.setText(savingsAccount.getCurrentbalance()+"");

        TextView goalBalance = (TextView) convertView.findViewById(R.id.id_goal_balance);
        goalBalance.setText(savingsAccount.getGoalbalance()+"");

        TextView daysLeft = (TextView) convertView.findViewById(R.id.id_time_left);

        goalBalance.setText(savingsAccount.getGoalbalance()+"");

        return convertView;
    }


}
