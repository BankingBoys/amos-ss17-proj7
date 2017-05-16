package de.fau.amos.virtualledger.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.fau.amos.virtualledger.android.auth.AuthenticationProvider;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sebastian on 14.05.17.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @Inject
    AuthenticationProvider authenticationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        init();


        authenticationProvider.tryLoadLoginData(this);
        if(authenticationProvider.isLoggedIn())
        {
            // TODO logic here
        }

        setContentView(R.layout.login);


        Button button_register = (Button) findViewById(R.id.loginButton);

        final TextView textview = (TextView) findViewById(R.id.loginTextView);
        final AppCompatActivity context = this;

        button_register.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        final String userID = ((EditText) findViewById(R.id.userIDField)).getText().toString();
                        final String password = ((EditText) findViewById(R.id.SecretField)).getText().toString();

                        // use observable due to asynchronizm
                        authenticationProvider.login(userID, password)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<String>() {

                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull String s) {
                                        if(authenticationProvider.isLoggedIn()){
                                            textview.setText(s);
                                            // TODO if user made cross on UI for perma login
                                            if(true) {
                                                authenticationProvider.persistLoginData(context);
                                            }
                                        } else{
                                            textview.setText(s);
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.e(TAG, "Error occured in Observable from login.");
                                        textview.setText(e.getMessage());
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                }
        );
    }
    private void init() {
        ((App) getApplication()).getNetComponent().inject(this);
    }
}
