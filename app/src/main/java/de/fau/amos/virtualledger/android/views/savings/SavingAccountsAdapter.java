package de.fau.amos.virtualledger.android.views.savings;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.dtos.SavingsAccount;

public class SavingAccountsAdapter extends ArrayAdapter<SavingsAccount> {

    private Activity activity;

    public SavingAccountsAdapter(Activity activity, int layout, List<SavingsAccount> data) {
        super(activity, layout, data);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.saving_accounts_list_item, parent, false);
        }
        final SavingsAccount savingsAccount = this.getItem(position);
        updateText(convertView, R.id.id_name, savingsAccount.getName());
        updateText(convertView, R.id.id_current_balance,  String.valueOf(Math.round(savingsAccount.getCurrentbalance())));
        updateText(convertView, R.id.id_goal_balance,  String.valueOf(Math.round(savingsAccount.getGoalbalance())));
        updateText(convertView, R.id.id_time_left, new DaysLeftFunction().apply(savingsAccount));
        
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavingsDetailsFragment savingsDetailsFragment = new SavingsDetailsFragment(savingsAccount);
                FragmentManager manager = activity.getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_menu_content, savingsDetailsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return convertView;
    }

    private void updateText(View convertView, int id, String text) {
        TextView goalBalance = (TextView) convertView.findViewById(id);
        goalBalance.setText(text);
    }
}
