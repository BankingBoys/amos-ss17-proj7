package de.fau.amos.virtualledger.android.authentication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.fau.amos.virtualledger.R;

/**
 * Created by Georg on 18.06.2017.
 */

public class AuthenticationChooserActivity extends Activity {
    private static final String TAG = AuthenticationChooserActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setContentView(R.layout.authentication_activity_chooser);
    }


    @OnClick(R.id.authentication_activity_chooser_demo_workflow_button)
    public void onInitDemoWorkflow() {

    }

    @OnClick(R.id.authentication_activity_chooser_oidc_workflow_button)
    public void onInitOidcWorkflow() {

    }
}
