package de.fau.amos.virtualledger.android.dagger.module;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.auth.login.LoginProvider;
import de.fau.amos.virtualledger.android.auth.login.MockedLoginProvider;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Simon on 07.05.2017. (taken from https://adityaladwa.wordpress.com/2016/05/09/
 * dagger-2-with-retrofit-and-okhttp-and-gson/)
 */
@Module
public class NetModule {

    /**
     *
     */
    private String baseUrl;

    /**
     *
     * @param baseUrl
     * @methodtype constructor
     */
    public NetModule(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    /**
     *
     * @param application
     * @return cache
     */
    @Provides
    @Singleton
    Cache provideHttpCache(Application application)
    {
        int cacheSize = 10*1024*1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    /**
     *
     * @return Gson
     */
    @Provides
    @Singleton
    Gson provideGson()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    /**
     *
     * @param cache
     * @return OkHttpClient
     */
    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache)
    {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache);
        return client.build();
    }

    /**
     *
     * @param gson
     * @param okHttpClient
     * @return Retrofit
     */
    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient)
    {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

}
