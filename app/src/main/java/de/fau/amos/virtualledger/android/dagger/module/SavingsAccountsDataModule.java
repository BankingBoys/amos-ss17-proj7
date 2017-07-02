package de.fau.amos.virtualledger.android.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.fau.amos.virtualledger.android.api.savings.SavingsProvider;
import de.fau.amos.virtualledger.android.dagger.component.NetComponentScope;
import de.fau.amos.virtualledger.android.data.SavingsAccountsDataManager;

@Module(includes = {SavingsModule.class})
public class SavingsAccountsDataModule {

    @Provides
    @NetComponentScope
    SavingsAccountsDataManager provideBankingDataManager(final SavingsProvider savingsProvider) {
        return new SavingsAccountsDataManager(savingsProvider);
    }
}
