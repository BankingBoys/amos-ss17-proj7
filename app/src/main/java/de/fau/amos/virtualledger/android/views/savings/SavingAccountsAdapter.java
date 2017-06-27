package de.fau.amos.virtualledger.android.views.savings;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.model.SavingsAccount;

public class SavingAccountsAdapter extends ArrayAdapter<SavingsAccount> {

    private Activity activity;

    public SavingAccountsAdapter(Activity activity, int layout, List<SavingsAccount> data) {
        super(activity, layout, data);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SavingsAccount transaction = super.getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.saving_accounts_list_item, parent, false);
        }
        final SavingsAccount savingsAccount = this.getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.id_name);
        name.setText(savingsAccount.getName());

        TextView currentBalance = (TextView) convertView.findViewById(R.id.id_current_balance);
        currentBalance.setText(Math.round(savingsAccount.getCurrentbalance()) + "");

        TextView goalBalance = (TextView) convertView.findViewById(R.id.id_goal_balance);
        goalBalance.setText(Math.round(savingsAccount.getGoalbalance()) + "");

        TextView daysLeftLabel = (TextView) convertView.findViewById(R.id.id_time_left);

        int daysLeft = savingsAccount.daysLeft();
        if (daysLeft > 1) {
            daysLeftLabel.setText(daysLeft + " days left");
        } else if (daysLeft == 1) {
            daysLeftLabel.setText("one day left");
        } else {
            daysLeftLabel.setText("done");
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavingsAccountsDialogFragment dialog = SavingsAccountsDialogFragment.newInstance(savingsAccount.getName());
                dialog.show(activity.getFragmentManager(), "DialogFragment");
            }
        });
        return convertView;
    }
}
