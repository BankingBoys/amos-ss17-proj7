package de.fau.amos.virtualledger.android.authentication.oidc;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.jboss.aerogear.android.authorization.AuthorizationManager;
import org.jboss.aerogear.android.authorization.AuthzModule;
import org.jboss.aerogear.android.authorization.oauth2.OAuth2AuthorizationConfiguration;
import org.jboss.aerogear.android.authorization.oauth2.OAuthWebViewDialog;
import org.jboss.aerogear.android.core.Callback;

import java.net.URL;

/**
 * Created by Georg on 18.06.2017.
 */

public class OidcAuthenticationActivity extends Activity {
    private static final String TAG = OidcAuthenticationActivity.class.getSimpleName();


    private static final String ADORSYS_SERVER_URL = "https://multibanking-keycloak.dev.adorsys.de";
    private static final String AUTHZ_URL = ADORSYS_SERVER_URL +"/auth";
    private static final String AUTHZ_ENDPOINT = "/realms/multibanking/protocol/openid-connect/auth";
    private static final String ACCESS_TOKEN_ENDPOINT = "/realms/multibanking/protocol/openid-connect/token";
    private static final String REFRESH_TOKEN_ENDPOINT = "/realms/multibanking/protocol/openid-connect/token";
    private static final String AUTHZ_ACCOOUNT_ID = "keycloak-token";
    private static final String AUTHZ_CLIENT_ID = "multibanking-client";
    private static final String AUTHZ_REDIRECT_URL = "http://oauth2callback";
    private static final String MODULE_NAME = "KeyCloakAuthz";

    private void successfullConnected(Object data) {
        try {
            String token = (String) data;
            Log.i(TAG, "got successful response from oidc server!");

            // TODO: store token
            // TODO: start new activity and finish this one

        } catch (Exception ex) {
            Log.e(TAG, "Error in obtaining token from response of oidc server!");
            Toast.makeText(this, "Login failed. Please try again later!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            AuthorizationManager.config(MODULE_NAME, OAuth2AuthorizationConfiguration.class)
                    .setBaseURL(new URL(AUTHZ_URL))
                    .setAuthzEndpoint(AUTHZ_ENDPOINT)
                    .setAccessTokenEndpoint(ACCESS_TOKEN_ENDPOINT)
                    .setRefreshEndpoint(REFRESH_TOKEN_ENDPOINT)
                    .setAccountId(AUTHZ_ACCOOUNT_ID)
                    .setClientId(AUTHZ_CLIENT_ID)
                    .setRedirectURL(AUTHZ_REDIRECT_URL)
                    .asModule();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        connect(this, new Callback() {
            @Override
            public void onSuccess(Object data) {
                successfullConnected(data);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(OidcAuthenticationActivity.this.getApplicationContext(), "Authentication failed!", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void connect(final Activity activity, final Callback callback) {
        try {
            final AuthzModule authzModule = AuthorizationManager.getModule(MODULE_NAME);

            authzModule.requestAccess(activity, new Callback<String>() {
                @Override
                public void onSuccess(String s) {
                    callback.onSuccess(s);
                }

                @Override
                public void onFailure(Exception e) {
                    if (!e.getMessage().matches(OAuthWebViewDialog.OAuthReceiver.DISMISS_ERROR)) {
                        authzModule.deleteAccount();
                    }
                    callback.onFailure(e);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
