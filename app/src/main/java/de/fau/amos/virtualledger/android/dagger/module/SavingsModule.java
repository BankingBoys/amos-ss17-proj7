package de.fau.amos.virtualledger.android.dagger.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.savings.HTTPSavingsProvider;
import de.fau.amos.virtualledger.android.api.savings.SavingsProvider;
import retrofit2.Retrofit;

@Module(includes = {NetModule.class, AuthenticationModule.class})
public class SavingsModule {

    /**
     * @param retrofit
     * @return
     */
    @Provides
    @Singleton
    SavingsProvider provideSavingsProvider(Retrofit retrofit, AuthenticationProvider authenticationProvider) {
        return new HTTPSavingsProvider(retrofit, authenticationProvider);
    }
}
