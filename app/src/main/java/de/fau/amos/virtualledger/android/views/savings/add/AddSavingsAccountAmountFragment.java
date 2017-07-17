package de.fau.amos.virtualledger.android.views.savings.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.model.SavingsAccount;
import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;

public class AddSavingsAccountAmountFragment extends AddSavingsAccountPage {
    @SuppressWarnings("unused")
    private static final String TAG = AddSavingsAccountAmountFragment.class.getSimpleName();

    @BindView(R.id.add_savings_account_edit_text_amount)
    EditText editText;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.saving_accounts_add_fragment_amount, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void fillInData(final SavingsAccount savingsAccount) {
        savingsAccount.setGoalbalance(NumberUtils.toDouble(editText.getText().toString()));
        savingsAccount.setSubGoals(new ArrayList<SavingsAccountSubGoal>());
    }

    @Override
    public boolean navigatePossible() {
        if (editText.getText().toString().isEmpty()) {
            logger().info("No text entered. Presenting toast.");
            Toast.makeText(AddSavingsAccountAmountFragment.super.getContext(), R.string.add_savings_account_enter_goal_info_needed_message, Toast.LENGTH_LONG).show();
            return false;
        }

        if (Float.parseFloat(editText.getText().toString()) == 0.0) {
            Toast.makeText(AddSavingsAccountAmountFragment.super.getContext(), R.string.add_savings_account_enter_goal_info_needed_message_not_zero, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName());
    }
}
