package de.fau.amos.virtualledger.android.views.savings.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.model.SavingsAccount;

public class AddSavingsAccountAmountTypeFragment extends AddSavingsAccountPage {
    @SuppressWarnings("unused")
    private static final String TAG = AddSavingsAccountAmountTypeFragment.class.getSimpleName();

    @OnClick(R.id.add_savings_account_radio_button_amount_type_normal)
    void onClickRadioButtonNormal() {
        onChangeType(false);
    }

    @OnClick(R.id.add_savings_account_radio_button_amount_type_split)
    void onClickRadioButtonSplit() {
        onChangeType(true);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.saving_accounts_add_fragment_amount_type, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void fillInData(final SavingsAccount addSavingsAccountResult) {
        //Nothing to do
    }

    private void onChangeType(final boolean split) {
        ((AddSavingsAccountActivity) getActivity()).setSplitGoals(split);
    }
}
