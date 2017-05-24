package de.fau.amos.virtualledger.android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Georg on 13.05.2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    /**
     *
     */
    @Inject
    AuthenticationProvider authenticationProvider;


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

        final TextView textView = (TextView) findViewById(R.id.registration_feedback);

        authenticationProvider.register(email, password, firstname, lastname)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        /*textView.setTextColor(Color.GREEN);
                        textView.setText(s);*/
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "Error occured in Observable from register.");
                        textView.setTextColor(Color.RED);
                        textView.setText(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
