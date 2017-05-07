package de.fau.amos.virtualledger.android.dagger;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Simon on 07.05.2017. taken from https://adityaladwa.wordpress.com/2016/05/09/
 * dagger-2-with-retrofit-and-okhttp-and-gson/)
 */

public interface Restapi {

    @GET("/api/test")
    Call<List<Post>> getPosts();
}
