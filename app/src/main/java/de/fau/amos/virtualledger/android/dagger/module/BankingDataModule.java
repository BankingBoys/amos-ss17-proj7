package de.fau.amos.virtualledger.android.dagger.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.dagger.component.NetComponentScope;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.localStorage.BankAccessCredentialDB;

@Module(includes = {AppModule.class, BankingModule.class, DatabaseModule.class, SavingsModule.class})
public class BankingDataModule {


    @Provides
    @NetComponentScope
    BankingDataManager provideBankingDataManager(Application application, BankingProvider bankingProvider, BankAccessCredentialDB bankAccessCredentialDB, AuthenticationProvider authenticationProvider) {
        return new BankingDataManager(application, bankingProvider, bankAccessCredentialDB, authenticationProvider);
    }
}
