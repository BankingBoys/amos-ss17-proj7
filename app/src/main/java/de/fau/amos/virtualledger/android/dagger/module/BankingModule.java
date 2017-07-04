package de.fau.amos.virtualledger.android.dagger.module;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.api.banking.HTTPBankingProvider;
import de.fau.amos.virtualledger.android.dagger.component.NetComponentScope;
import retrofit2.Retrofit;

@Module(includes = {NetModule.class})
public class BankingModule {

    @Provides
    @NetComponentScope
    BankingProvider provideBankingProvider(Retrofit retrofit, AuthenticationProvider authenticationProvider) {
        return new HTTPBankingProvider(retrofit, authenticationProvider);
    }
}
