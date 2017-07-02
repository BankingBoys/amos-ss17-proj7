package de.fau.amos.virtualledger.android.dagger.module;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.contacts.ContactsProvider;
import de.fau.amos.virtualledger.android.dagger.component.NetComponentScope;
import de.fau.amos.virtualledger.android.data.ContactsDataManager;


@Module(includes = {ContactsModule.class})
public class ContactsDataModule {

    @Provides
    @NetComponentScope
    ContactsDataManager provideContactsDataManager (final ContactsProvider contactsProvider) {
        return new ContactsDataManager(contactsProvider);
    }
}
