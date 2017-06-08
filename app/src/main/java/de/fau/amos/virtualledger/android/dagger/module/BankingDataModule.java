package de.fau.amos.virtualledger.android.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.data.BankingDataManager;

@Module(includes = {BankingModule.class})
public class BankingDataModule {


    @Provides
    @Singleton
    BankingDataManager provideBankingDataManager(BankingProvider bankingProvider) {
        return new BankingDataManager(bankingProvider);
    }
}
