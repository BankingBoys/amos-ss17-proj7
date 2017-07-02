package de.fau.amos.virtualledger.android.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.contacts.ContactsProvider;
import de.fau.amos.virtualledger.android.api.contacts.HTTPContactsProvider;
import retrofit2.Retrofit;

/**
 * Created by Simon on 01.07.2017.
 */

@Module(includes = {NetModule.class})
public class ContactsModule {
    @Provides
    ContactsProvider provideContactsProvider(Retrofit retrofit, AuthenticationProvider authenticationProvider) {
        return new HTTPContactsProvider(retrofit, authenticationProvider);
    }
}
