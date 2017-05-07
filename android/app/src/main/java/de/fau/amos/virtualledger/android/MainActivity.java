package de.fau.amos.virtualledger.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.fau.amos.virtualledger.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
    }
}
