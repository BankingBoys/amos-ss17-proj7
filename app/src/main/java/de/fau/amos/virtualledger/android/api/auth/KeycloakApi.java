package de.fau.amos.virtualledger.android.api.auth;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Georg on 26.06.2017.
 */

public interface KeycloakApi {

    @FormUrlEncoded
    @POST("token")
    Call<OidcData> login(@Field("username") String username, @Field("password") String password, @Field("client_id") String clientId, @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("token")
    Call<OidcData> refreshToken(@Field("refresh_token") String refreshToken, @Field("client_id") String clientId, @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("logout")
    Call<Object> logout(@Field("refresh_token") String refreshToken, @Field("client_id") String clientId);
}
