package de.fau.amos.virtualledger.android.views.savings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.dtos.SavingsAccount;

/**
 * Created by sebastian on 20.07.17.
 */

public class SavingsDetailsFragment extends Fragment {

    private View view;
    private SavingsAccount account;

    public SavingsDetailsFragment(SavingsAccount account) {
        this.account = account;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.savings_account_details, container, false);
        ButterKnife.bind(this, view);

        updateText(R.id.id_name, account.getName());
        updateText(R.id.id_time_left, new DaysLeftFunction().apply(this.account));
        updateText(R.id.id_current_balance, String.valueOf(Math.round(this.account.getCurrentbalance())));
        updateText(R.id.id_goal_balance, String.valueOf(Math.round(this.account.getGoalbalance())));


        return view;
    }

    private void updateText(int id, String text) {
        TextView textView = (TextView) view.findViewById(id);
        textView.setText(text);
    }

}
