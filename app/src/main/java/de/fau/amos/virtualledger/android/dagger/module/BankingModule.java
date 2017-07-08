package de.fau.amos.virtualledger.android.dagger.module;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.Restapi;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.api.banking.HTTPBankingProvider;
import de.fau.amos.virtualledger.android.api.shared.CallWithToken;
import de.fau.amos.virtualledger.android.dagger.component.NetComponentScope;
import retrofit2.Retrofit;

@Module(includes = {NetModule.class})
public class BankingModule {

    @Provides
    @NetComponentScope
    BankingProvider provideBankingProvider(Retrofit retrofit, CallWithToken callWithToken) {
        return new HTTPBankingProvider(retrofit.create(Restapi.class), callWithToken);
    }
}
