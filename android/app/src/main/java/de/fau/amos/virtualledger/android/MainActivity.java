package de.fau.amos.virtualledger.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.model.StringApiModel;
import de.fau.amos.virtualledger.android.api.Restapi;
import de.fau.amos.virtualledger.android.model.UserCredential;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 *
 */
public class MainActivity extends AppCompatActivity {


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.testfile);
    }
}
