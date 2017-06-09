package de.fau.amos.virtualledger.android.bankingOverview.addBankAccess;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingAddFailedException;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;

public class AddBankAccessActivity extends AppCompatActivity {

    private static final String TAG = "AddBankAccessActivity";

    @Inject
    BankingDataManager bankingDataManager;

    @BindView(R.id.editText_addBankAccess_blz)
    EditText blzEditText;
    @BindView(R.id.editText_addBankAccess_loginName)
    EditText loginNameEditText;
    @BindView(R.id.editText_addBankAccess_pin)
    EditText pinEditText;

    @OnClick(R.id.button_addBankAccess_submit)
    void submit(View view) {
        final BankAccessCredential bankAccessCredential = new BankAccessCredential();
        final String bankCode = blzEditText.getText().toString();
        final String bankLogin = loginNameEditText.getText().toString();
        final String pin = pinEditText.getText().toString();
        bankAccessCredential.setBankcode(bankCode);
        bankAccessCredential.setBanklogin(bankLogin);
        bankAccessCredential.setPin(pin);

        try {
            bankingDataManager.addBankAccess(bankAccessCredential);
            Toast.makeText(this, "Access added successfully", Toast.LENGTH_SHORT).show();
        } catch (BankingAddFailedException e) {
            Toast.makeText(AddBankAccessActivity.this, "Access could not be added", Toast.LENGTH_SHORT).show();
        } finally {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ((App) getApplication()).getNetComponent().inject(this);

        setContentView(R.layout.banking_overview_activity_add_bank_access);
        ButterKnife.bind(this);
    }
}
