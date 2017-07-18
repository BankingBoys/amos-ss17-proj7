package de.fau.amos.virtualledger.android.views.savings.add;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.dtos.BankAccountIdentifier;
import de.fau.amos.virtualledger.dtos.SavingsAccount;

/**
 * Created by Simon on 17.07.2017.
 */

public class BankAccountSelectedListener {
    Set<BankAccount> bankAccounts = new HashSet<>();
    SavingsAccount account;
    Context context;

    public BankAccountSelectedListener(Context context, SavingsAccount account) {
        this.context = context;
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
        List<BankAccountIdentifier> bankAccounts = new ArrayList<>();
        for (BankAccount account: this.bankAccounts) {
            BankAccountIdentifier identifier = new BankAccountIdentifier("", account.getBankid());
            bankAccounts.add(identifier);
        }
        this.account.setAssignedBankAccounts(bankAccounts);
    }
}
