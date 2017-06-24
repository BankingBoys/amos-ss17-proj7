package de.fau.amos.virtualledger.android.views.savings.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;

public class AddSavingsAccountAmountFragment extends Fragment implements AddSavingsAccountPage {
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
    public void fillInData(final AddSavingsAccountResult addSavingsAccountResult) {
        addSavingsAccountResult.amount = Double.valueOf(editText.getText().toString());
    }
}
