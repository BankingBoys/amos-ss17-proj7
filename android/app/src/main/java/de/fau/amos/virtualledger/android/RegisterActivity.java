package de.fau.amos.virtualledger.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.Restapi;
import de.fau.amos.virtualledger.android.api.model.StringApiModel;
import de.fau.amos.virtualledger.android.model.UserCredential;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Georg on 13.05.2017.
 */

public class RegisterActivity extends AppCompatActivity {
    /**
     *
     */
    @Inject
    Retrofit retrofit;

    /**
     *
     */
    TextView textView;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.registration);
        ((App) getApplication()).getNetComponent().inject(this);

        Button button_register = (Button) findViewById(R.id.button_register);

        button_register.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        register();
                    }
                }
        );
    }

    /**
     * reads data out of form and performs a REST call to server for registering a new server
     */
    private void register() {

        String email = ((EditText) findViewById(R.id.Email)).getText().toString();
        String password = ((EditText) findViewById(R.id.Password)).getText().toString();
        String firstname = ((EditText) findViewById(R.id.FirstName)).getText().toString();
        String lastname = ((EditText) findViewById(R.id.LastName)).getText().toString();
        retrofit2.Call<StringApiModel> responseMessage = retrofit.create(Restapi.class).register(new UserCredential(email, password, firstname, lastname));

        textView = (TextView) findViewById(R.id.registration_feedback);

        responseMessage.enqueue(new Callback<StringApiModel>() {
            @Override
            public void onResponse(retrofit2.Call<StringApiModel> call, Response<StringApiModel> response) {
                if(response.isSuccessful()) {
                    textView.setTextColor(Color.GREEN);
                    textView.setText(response.body().getData());
                    Intent intent = new Intent(RegisterActivity.this, MainActivity_Menu.class);
                    startActivity(intent);

                } else if(response.code() == 400)
                { // code for sent data were wrong
                    try {
                        textView.setTextColor(Color.RED);
                        textView.setText(response.errorBody().string());
                    } catch(IOException ex)
                    {
                        Log.v("Exception thrown: ", ex.getMessage());
                    }
                } else
                {
                    Log.v("Error Connection", "The communication to the server failed!");
                }
            }


            @Override
            public void onFailure(retrofit2.Call<StringApiModel> call, Throwable t) {
                //Set the error to the textview
                textView.setText(t.getMessage());
            }
        });
    }
}
