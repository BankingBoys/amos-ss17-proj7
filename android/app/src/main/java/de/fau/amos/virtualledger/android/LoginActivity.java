package de.fau.amos.virtualledger.android;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.module.AppModule;
import de.fau.amos.virtualledger.android.dagger.module.login.LoginProvider;

/**
 * Created by sebastian on 14.05.17.
 */

public class LoginActivity extends AppCompatActivity {

    @Inject
    LoginProvider loginProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        Button button_register = (Button) findViewById(R.id.loginButton);
        button_register.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        System.out.println("Token:"+loginProvider.getToken());
                    }
                }
        );
    }
    private void init() {
        ((App) getApplication()).getNetComponent().inject(this);
    }
}
