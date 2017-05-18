package de.fau.amos.virtualledger.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.fau.amos.virtualledger.R;

public class AddBankAccessActivity extends AppCompatActivity {

    private static final String TAG = "AddBankAccessActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_bank_access);
    }
}
