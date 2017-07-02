package de.fau.amos.virtualledger.android.dagger.module;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.contacts.ContactsProvider;
import de.fau.amos.virtualledger.android.api.contacts.HTTPContactsProvider;
import de.fau.amos.virtualledger.android.dagger.component.NetComponentScope;
import retrofit2.Retrofit;

@Module(includes = {NetModule.class})
public class ContactsModule {
    @Provides
    @NetComponentScope
    ContactsProvider provideContactsProvider(Retrofit retrofit, AuthenticationProvider authenticationProvider) {
        return new HTTPContactsProvider(retrofit, authenticationProvider);
    }
}
