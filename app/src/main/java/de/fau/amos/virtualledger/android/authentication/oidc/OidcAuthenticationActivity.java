package de.fau.amos.virtualledger.android.authentication.oidc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.auth.OidcAuthenticationProvider;
import de.fau.amos.virtualledger.android.config.PropertyReader;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.dagger.component.OidcAuthenticationScope;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OidcAuthenticationActivity extends AppCompatActivity {
    private static final String TAG = OidcAuthenticationActivity.class.getSimpleName();


    @BindView(R.id.userIDField)
    EditText userIdField;

    @BindView(R.id.SecretField)
    EditText secretField;

    @Inject
    AuthenticationProvider authenticationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ((App) getApplication()).getOidcAuthenticationComponent().inject(this);

        setContentView(R.layout.authentication_activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.loginButton)
    public void onLogin() {
        final String userID = userIdField.getText().toString();
        final String password = secretField.getText().toString();

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

                        } else {

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick(R.id.textViewLogin_RegisterFirst)
    public void onRegister() {

        String url = ((App) getApplication()).getOidcRegisterUrl();
        Uri uri = Uri.parse(url);
        Intent intent = new Intent();
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }
}