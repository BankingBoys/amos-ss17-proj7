package de.fau.amos.virtualledger.android.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.bankingOverview.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.android.data.BankingDataManager;

@Module(includes = {BankingModule.class, DatabaseModule.class})
public class BankingDataModule {


    @Provides
    @Singleton
    BankingDataManager provideBankingDataManager(BankingProvider bankingProvider, BankAccessCredentialDB bankAccessCredentialDB) {
        return new BankingDataManager(bankingProvider, bankAccessCredentialDB);
    }
}
