package de.fau.amos.virtualledger.android.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.api.banking.HTTPBankingProvider;
import de.fau.amos.virtualledger.android.dagger.component.NetComponentScope;
import retrofit2.Retrofit;

/**
 * Created by Georg on 21.05.2017.
 */

@Module(includes = {NetModule.class})
public class BankingModule {

    /**
     * @param retrofit
     * @return
     */
    @Provides
    @NetComponentScope
    BankingProvider provideBankingProvider(Retrofit retrofit, AuthenticationProvider authenticationProvider) {
        return new HTTPBankingProvider(retrofit, authenticationProvider);
    }
}
