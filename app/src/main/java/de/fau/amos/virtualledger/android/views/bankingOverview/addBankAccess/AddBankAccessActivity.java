package de.fau.amos.virtualledger.android.views.bankingOverview.addBankAccess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.views.menu.MainMenu;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;

public class AddBankAccessActivity extends AppCompatActivity {
    @SuppressWarnings("unused")
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
    void submit() {
        final BankAccessCredential bankAccessCredential = new BankAccessCredential();
        final String bankCode = blzEditText.getText().toString();
        final String bankLogin = loginNameEditText.getText().toString();
        final String pin = pinEditText.getText().toString();
        bankAccessCredential.setBankcode(bankCode);
        bankAccessCredential.setBanklogin(bankLogin);
        bankAccessCredential.setPin(pin);

        bankingDataManager.addBankAccess(bankAccessCredential);
        Intent intent = new Intent(this, MainMenu.class);
        Bundle bundle = new Bundle();
        bundle.putInt("startingFragment", 2);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ((App) getApplication()).getNetComponent().inject(this);

        setContentView(R.layout.banking_overview_activity_add_bank_access);
        ButterKnife.bind(this);
    }
}
