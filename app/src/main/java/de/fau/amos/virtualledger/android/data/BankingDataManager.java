package de.fau.amos.virtualledger.android.data;

import java.util.List;

import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.bankingOverview.localStorage.BankAccessCredentialDB;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class BankingDataManager {

    private final BankingProvider bankingProvider;
    private final BankAccessCredentialDB bankAccessCredentialDB;

    private List<BankAccess> bankAccesses;
    private List<BankAccountBookings> bankAccountBookings;

    public BankingDataManager(final BankingProvider bankingProvider, final BankAccessCredentialDB bankAccessCredentialDB) {
        this.bankingProvider = bankingProvider;
        this.bankAccessCredentialDB = bankAccessCredentialDB;
    }

    public void sync(final String user) {
        bankingProvider.getBankingOverview().subscribe(new Consumer<List<BankAccess>>() {
            @Override
            public void accept(@NonNull final List<BankAccess> bankAccesses) throws Exception {
                BankingDataManager.this.bankAccesses = bankAccesses;
                syncBookings(user);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull final Throwable throwable) throws Exception {

            }
        });
    }

    private void syncBookings(final String user) {
        final List<BankAccountSync> bankAccountSyncList = bankAccessCredentialDB.getBankAccountSyncList(user);
        bankingProvider.getBankingTransactions(bankAccountSyncList).subscribe(new Consumer<BankAccountSyncResult>() {
            @Override
            public void accept(@NonNull final BankAccountSyncResult bankAccountSyncResult) throws Exception {
                bankAccountBookings = bankAccountSyncResult.getBankaccountbookings();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull final Throwable throwable) throws Exception {

            }
        });
    }

    public List<BankAccess> getBankAccesses() {
        return bankAccesses;
    }

    public List<BankAccountBookings> getBankAccountBookings() {
        return bankAccountBookings;
    }
}
