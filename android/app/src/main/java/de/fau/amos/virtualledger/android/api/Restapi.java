package de.fau.amos.virtualledger.android.api;

import de.fau.amos.virtualledger.android.api.model.StringApiModel;
import de.fau.amos.virtualledger.android.model.UserCredential;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Simon on 07.05.2017. taken from https://adityaladwa.wordpress.com/2016/05/09/
 * dagger-2-with-retrofit-and-okhttp-and-gson/)
 */

public interface Restapi {

    @POST("/api/auth/register")
    Call<StringApiModel> register(@Body UserCredential credential);

}
