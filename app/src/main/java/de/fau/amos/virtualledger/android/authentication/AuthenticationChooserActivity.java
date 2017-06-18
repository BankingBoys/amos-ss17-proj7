package de.fau.amos.virtualledger.android.authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.authentication.demo.login.LoginActivity;


public class AuthenticationChooserActivity extends Activity {
    private static final String TAG = AuthenticationChooserActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_activity_chooser);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.authentication_activity_chooser_demo_workflow_button)
    public void onInitDemoWorkflow() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.authentication_activity_chooser_oidc_workflow_button)
    public void onInitOidcWorkflow() {

    }
}
