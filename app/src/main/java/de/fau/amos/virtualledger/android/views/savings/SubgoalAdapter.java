package de.fau.amos.virtualledger.android.views.savings;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;

public class SubgoalAdapter extends ArrayAdapter<SavingsAccountSubGoal> {

    private Activity activity;

    public SubgoalAdapter(Activity activity, int layout, List<SavingsAccountSubGoal> data) {
        super(activity, layout, data);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.subgoals_list_item, parent, false);
        }
        final SavingsAccountSubGoal savingsAccount = this.getItem(position);
        updateText(convertView, R.id.subgoal_name, savingsAccount.getName());
        updateText(convertView, R.id.subgoal_amount, String.valueOf(Math.round(savingsAccount.getAmount())));
        return convertView;
    }

    private void updateText(View convertView, int id, String text) {
        TextView goalBalance = (TextView) convertView.findViewById(id);
        goalBalance.setText(text);
    }
}
