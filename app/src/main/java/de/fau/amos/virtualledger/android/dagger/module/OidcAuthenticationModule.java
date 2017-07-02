package de.fau.amos.virtualledger.android.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.auth.OidcAuthenticationProvider;
import de.fau.amos.virtualledger.android.dagger.component.OidcAuthenticationScope;
import retrofit2.Retrofit;

/**
 * Created by Georg on 26.06.2017.
 */

@Module(includes = {NetModule.class})
public class OidcAuthenticationModule {

    /**
     * @param retrofit
     * @return
     */
    @Provides
    @OidcAuthenticationScope
    AuthenticationProvider provideLoginProvider(Retrofit retrofit) {
        return new OidcAuthenticationProvider(retrofit);
    }
}
