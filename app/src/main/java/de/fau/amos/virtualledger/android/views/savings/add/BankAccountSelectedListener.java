package de.fau.amos.virtualledger.android.views.savings.add;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.android.model.SavingsAccount;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Created by Simon on 17.07.2017.
 */

public class BankAccountSelectedListener {
    Set<BankAccount> bankAccounts = new HashSet<>();
    SavingsAccount account;

    public BankAccountSelectedListener(Set<BankAccount> bankAccounts, SavingsAccount account) {
        this.bankAccounts = bankAccounts;
        this.account = account;
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName());
    }

    public void selectBankAccount(BankAccount bankAccount) {
        this.logger().info("Selecting" + bankAccount);
        this.bankAccounts.add(bankAccount);
        this.syncToSavingsAccount();
    }

    public void deselectBankAccount(BankAccount bankAccount) {
        this.logger().info("Deselecting" + bankAccount);
        this.bankAccounts.remove(bankAccount);
        this.syncToSavingsAccount();
    }

    private void syncToSavingsAccount() {
        List<BankAccount> bankAccounts = new ArrayList<>();
        for (BankAccount account: this.bankAccounts) {
            bankAccounts.add(account);
        }
        this.account.setAssignedBankAccounts(bankAccounts);
    }
}
