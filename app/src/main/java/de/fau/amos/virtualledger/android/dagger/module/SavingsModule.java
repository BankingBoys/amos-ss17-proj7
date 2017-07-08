package de.fau.amos.virtualledger.android.dagger.module;


import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.RestApi;
import de.fau.amos.virtualledger.android.api.savings.HTTPSavingsProvider;
import de.fau.amos.virtualledger.android.api.savings.SavingsProvider;
import de.fau.amos.virtualledger.android.api.shared.CallWithToken;
import de.fau.amos.virtualledger.android.dagger.component.NetComponentScope;
import retrofit2.Retrofit;

@Module(includes = {NetModule.class})
public class SavingsModule {

    @Provides
    @NetComponentScope
    SavingsProvider provideSavingsProvider(final Retrofit retrofit, final CallWithToken callWithToken) {
        return new HTTPSavingsProvider(retrofit.create(RestApi.class), callWithToken);
    }
}
