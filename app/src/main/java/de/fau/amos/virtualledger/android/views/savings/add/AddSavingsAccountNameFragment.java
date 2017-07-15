package de.fau.amos.virtualledger.android.views.savings.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.model.SavingsAccount;

public class AddSavingsAccountNameFragment extends AddSavingsAccountPage {
    @SuppressWarnings("unused")
    private static final String TAG = AddSavingsAccountNameFragment.class.getSimpleName();

    @BindView(R.id.add_savings_account_edit_text_name)
    EditText editText;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.saving_accounts_add_fragment_name, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void fillInData(final SavingsAccount savingsAccount) {
        savingsAccount.setName(editText.getText().toString());
    }

    @Override
    public boolean navigatePossible() {
        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(AddSavingsAccountNameFragment.super.getContext(), R.string.add_savings_account_enter_name_info_needed_message, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
