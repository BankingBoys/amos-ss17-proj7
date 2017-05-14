package de.fau.amos.virtualledger.android.dagger.module;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.dagger.module.login.LoginProvider;
import de.fau.amos.virtualledger.android.dagger.module.login.MockedLoginProvider;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class LoginModule {

    @Provides
    @Singleton
    LoginProvider provideLoginProvider(Retrofit retrofitClient)
    {
        return new MockedLoginProvider();
    }


}
