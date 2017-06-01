package de.fau.amos.virtualledger.dtos;

import java.util.List;

public class BankAccountSyncResult {

    private List<BankAccountBookings> bankaccountbookings;

    public BankAccountSyncResult(List<BankAccountBookings> bankaccountbookings) {
        this.bankaccountbookings = bankaccountbookings;
    }

    public BankAccountSyncResult() {
    }

    public List<BankAccountBookings> getBankaccountbookings() {
        return bankaccountbookings;
    }

    public void setBankaccountbookings(List<BankAccountBookings> bankaccountbookings) {
        this.bankaccountbookings = bankaccountbookings;
    }
}
