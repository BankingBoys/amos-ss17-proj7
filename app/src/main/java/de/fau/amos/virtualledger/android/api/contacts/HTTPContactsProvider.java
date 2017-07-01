package de.fau.amos.virtualledger.android.api.contacts;

import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import retrofit2.Retrofit;

/**
 * Created by Simon on 01.07.2017.
 */

public class HTTPContactsProvider {

    private static final String TAG = "HTTPContactsProvider";

    private Retrofit retrofit;
    private AuthenticationProvider authenticationProvider;

    public HTTPContactsProvider(Retrofit retrofit, AuthenticationProvider authenticationProvider) {
        this.retrofit = retrofit;
        this.authenticationProvider = authenticationProvider;
    }

}
