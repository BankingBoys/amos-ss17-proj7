package de.fau.amos.virtualledger.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import de.fau.amos.virtualledger.R;

public class AddBankAccessActivity extends AppCompatActivity {

    private static final String TAG = "AddBankAccessActivity";

    @BindView(R.id.editText_addBankAccess_blz)
    EditText blzEditText;
    @BindView(R.id.editText_addBankAccess_loginName)
    EditText loginNameEditText;
    @BindView(R.id.editText_addBankAccess_pin)
    EditText pinEditText;

    @OnClick(R.id.button_addBankAccess_submit)
    void submit() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_bank_access);
    }
}
