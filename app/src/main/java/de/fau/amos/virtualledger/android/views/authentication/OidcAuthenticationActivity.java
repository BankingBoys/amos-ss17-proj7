package de.fau.amos.virtualledger.android.views.authentication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.views.menu.MainMenu;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OidcAuthenticationActivity extends AppCompatActivity {
    private static final String TAG = OidcAuthenticationActivity.class.getSimpleName();


    @BindView(R.id.userIDField)
    EditText userIdField;

    @BindView(R.id.SecretField)
    EditText secretField;

    @BindView(R.id.textViewFailLogin)
    TextView textviewLoginFail;

    @BindView(R.id.loginCheckBox)
    CheckBox checkBoxStayLoggedIn;

    @BindView(R.id.login_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.login_linear_layout_content)
    LinearLayout linearLayoutContent;

    @Inject
    AuthenticationProvider authenticationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ((App) getApplication()).getOidcAuthenticationComponent().inject(this);

        setContentView(R.layout.authentication_activity_login);
        ButterKnife.bind(this);

        if(authenticationProvider.isLoginDataPersisted()) {
            setUiLoading(true);
            authenticationProvider.tryLoadLoginData()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(@NonNull String s) throws Exception {
                            if (authenticationProvider.isLoggedIn()) {
                                executeNextActivityMenu();
                            } else {
                                textviewLoginFail.setText(s);
                                setUiLoading(false);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) {
                            Log.e(TAG, "Error occured in Observable from login: " + throwable.getMessage());
                            setUiLoading(false);
                        }
                    });
        } else {
            setUiLoading(false);
        }
    }

    @OnClick(R.id.loginButton)
    public void onLogin() {
        final String userID = userIdField.getText().toString();
        final String password = secretField.getText().toString();

        setUiLoading(true);
        // use observable due to asynchronizm
        authenticationProvider.login(userID, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        if (authenticationProvider.isLoggedIn()) {
                            executeNextActivityMenu();
                            if (checkBoxStayLoggedIn.isChecked()) {
                                authenticationProvider.persistLoginData();
                            }
                        } else {
                            textviewLoginFail.setText(s);
                            setUiLoading(false);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) {
                        Log.e(TAG, "Error occured in Observable from login.");
                        textviewLoginFail.setText(throwable.getMessage());
                        setUiLoading(false);
                    }
                }
            );
    }

    private void setUiLoading(final boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        linearLayoutContent.setVisibility(loading ? View.GONE : View.VISIBLE);
    }

    private void executeNextActivityMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
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