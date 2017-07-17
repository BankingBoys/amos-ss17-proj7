package de.fau.amos.virtualledger.android.views.savings.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.model.SavingsAccount;

public class AddSavingsAccountAmountSplitFragment extends AddSavingsAccountPage {
    @SuppressWarnings("unused")
    private static final String TAG = AddSavingsAccountAmountSplitFragment.class.getSimpleName();

    @BindView(R.id.add_savings_account_edit_text_sub_goal_name)
    EditText editTextSubGoalName;

    @BindView(R.id.add_savings_account_edit_text_sub_goal_amount)
    EditText editTextSubGoalAmount;

    @BindView(R.id.add_savings_account_button_add_sub_goal)
    Button buttonAddSubGoal;

    @BindView(R.id.add_savings_account_list_view_sub_goals)
    ListView listViewSubGoals;

    @BindView(R.id.add_savings_account_text_view_sub_goal_total)
    TextView textViewSubGoalTotal;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.saving_accounts_add_fragment_amount_split, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void fillInData(final SavingsAccount savingsAccount) {

    }
}
