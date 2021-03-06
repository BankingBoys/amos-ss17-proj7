package de.fau.amos.virtualledger.android.dagger.module;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.RestApi;
import de.fau.amos.virtualledger.android.api.contacts.ContactsProvider;
import de.fau.amos.virtualledger.android.api.contacts.HTTPContactsProvider;
import de.fau.amos.virtualledger.android.api.shared.CallWithToken;
import de.fau.amos.virtualledger.android.dagger.component.NetComponentScope;
import retrofit2.Retrofit;

@Module(includes = {NetModule.class})
public class ContactsModule {
    @Provides
    @NetComponentScope
    ContactsProvider provideContactsProvider(final Retrofit retrofit, final CallWithToken callWithToken) {
        return new HTTPContactsProvider(retrofit.create(RestApi.class), callWithToken);
    }
}
