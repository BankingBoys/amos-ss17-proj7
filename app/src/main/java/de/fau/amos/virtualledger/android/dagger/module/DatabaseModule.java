package de.fau.amos.virtualledger.android.dagger.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.dagger.component.NetComponentScope;
import de.fau.amos.virtualledger.android.localStorage.BankAccessCredentialDB;

@Module(includes = {AppModule.class})
public class DatabaseModule {

    @Provides
    @NetComponentScope
    BankAccessCredentialDB provideBankAccessCredentialDB(Application application) {
        return new BankAccessCredentialDB(application);
    }
}
