package de.fau.amos.virtualledger.android.views.savings.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.dtos.AddSavingsAccountData;

public class AddSavingsAccountFinalDateFragment extends AddSavingsAccountPage {
    @SuppressWarnings("unused")
    private static final String TAG = AddSavingsAccountFinalDateFragment.class.getSimpleName();

    @BindView(R.id.add_savings_account_date_picker_enter_final_date)
    DatePicker datePicker;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.saving_accounts_add_fragment_final_date, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void fillInData(final AddSavingsAccountData addSavingsAccountResult) {

    }
}
