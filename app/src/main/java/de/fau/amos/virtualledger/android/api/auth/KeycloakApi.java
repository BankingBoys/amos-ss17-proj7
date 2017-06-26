package de.fau.amos.virtualledger.android.api.auth;

import de.fau.amos.virtualledger.android.model.OidcLoginData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Georg on 26.06.2017.
 */

public interface KeycloakApi {

    @POST("token")
    Call<String> login(@Body OidcLoginData loginData);

    @POST("token")
    Call<String> refreshToken(@Body OidcLoginData loginData);
}
