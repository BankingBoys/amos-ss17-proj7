package de.fau.amos.virtualledger.android.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.auth.login.LoginProvider;
import de.fau.amos.virtualledger.android.auth.login.MockedLoginProvider;
import retrofit2.Retrofit;

/**
 * Created by Georg on 14.05.2017.
 */

@Module(includes = {NetModule.class})
public class AuthenticationModule {


    /**
     *
     * @param retrofit
     * @return
     */
    @Provides
    @Singleton
    LoginProvider provideLoginProvider(Retrofit retrofit)
    {
        return new MockedLoginProvider();
    }
}
