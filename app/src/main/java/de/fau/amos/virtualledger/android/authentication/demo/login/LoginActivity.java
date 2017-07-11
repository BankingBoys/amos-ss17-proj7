package de.fau.amos.virtualledger.android.authentication.demo.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.views.menu.MainMenu;
import de.fau.amos.virtualledger.android.authentication.demo.registration.RegisterActivity;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
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


        authenticationProvider.tryLoadLoginData();
        if (authenticationProvider.isLoggedIn()) {
            executeNextActivityMenu();
        }

        setContentView(R.layout.authentication_activity_login);


        Button button_login = (Button) findViewById(R.id.loginButton);

        final TextView textviewLoginFail = (TextView) findViewById(R.id.textViewFailLogin);
        textviewLoginFail.setTextColor(Color.RED);
        final TextView textViewRegister = (TextView) findViewById(R.id.textViewLogin_RegisterFirst);
        final CheckBox checkBoxStayLoggedIn = (CheckBox) findViewById(R.id.loginCheckBox);
        final AppCompatActivity context = this;

        textViewRegister.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        executeNextActivityRegister();
                    }

                }
        );

        button_login.setOnClickListener(
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
                                        if (authenticationProvider.isLoggedIn()) {
                                            executeNextActivityMenu();
                                            if (checkBoxStayLoggedIn.isChecked()) {
                                                authenticationProvider.persistLoginData();
                                            }
                                        } else {
                                            textviewLoginFail.setText(s);
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.e(TAG, "Error occured in Observable from login.");
                                        textviewLoginFail.setText(e.getMessage());
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

    private void executeNextActivityRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void executeNextActivityMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    public Context getContext() {
        return this;
    }
}
