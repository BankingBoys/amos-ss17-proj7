package de.fau.amos.virtualledger.android.dagger.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.savings.HTTPSavingsProvider;
import de.fau.amos.virtualledger.android.api.savings.SavingsProvider;
import de.fau.amos.virtualledger.android.dagger.component.NetComponentScope;
import retrofit2.Retrofit;

@Module(includes = {NetModule.class})
public class SavingsModule {

    /**
     * @param retrofit
     * @return
     */
    @Provides
    @NetComponentScope
    SavingsProvider provideSavingsProvider(Retrofit retrofit, AuthenticationProvider authenticationProvider) {
        return new HTTPSavingsProvider(retrofit, authenticationProvider);
    }
}
