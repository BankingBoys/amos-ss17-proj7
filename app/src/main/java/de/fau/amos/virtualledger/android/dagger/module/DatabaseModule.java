package de.fau.amos.virtualledger.android.dagger.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.localStorage.BankAccessCredentialDB;

/**
 * Created by Georg on 06.06.2017.
 */
@Module(includes = {AppModule.class})
public class DatabaseModule {

    @Provides
    @Singleton
    BankAccessCredentialDB provideBankAccessCredentialDB(Application application) {
        return new BankAccessCredentialDB(application);
    }
}
