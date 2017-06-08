package de.fau.amos.virtualledger.android.data;

import java.util.List;

import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccountBookings;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;

public class BankingDataManager {

    private final BankingProvider bankingProvider;

    private List<BankAccess> bankAccesses;
    private List<BankAccountBookings> bankAccountBookings;

    public BankingDataManager(final BankingProvider bankingProvider) {
        this.bankingProvider = bankingProvider;
    }

    public List<BankAccess> getBankAccesses() {
        return bankAccesses;
    }

    public List<BankAccountBookings> getBankAccountBookings() {
        return bankAccountBookings;
    }
}
