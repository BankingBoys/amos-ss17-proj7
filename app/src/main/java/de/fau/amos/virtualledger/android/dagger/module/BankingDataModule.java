package de.fau.amos.virtualledger.android.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.api.savings.SavingsProvider;
import de.fau.amos.virtualledger.android.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.android.data.BankingDataManager;

@Module(includes = {BankingModule.class, DatabaseModule.class, SavingsModule.class})
public class BankingDataModule {


    @Provides
    BankingDataManager provideBankingDataManager(BankingProvider bankingProvider, SavingsProvider savingsProvider, BankAccessCredentialDB bankAccessCredentialDB, AuthenticationProvider authenticationProvider) {
        return new BankingDataManager(bankingProvider, savingsProvider, bankAccessCredentialDB, authenticationProvider);
    }
}
