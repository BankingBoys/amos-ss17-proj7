package de.fau.amos.virtualledger.server.model;


import java.util.List;

public class SavingsAccountAddWrapper {

    private SavingsAccount savingsAccount;
    private List<BankAccountIdentifier> bankAccountIdentifierList;

    public SavingsAccountAddWrapper(SavingsAccount savingsAccount, List<BankAccountIdentifier> bankAccountIdentifierList) {
        this.savingsAccount = savingsAccount;
        this.bankAccountIdentifierList = bankAccountIdentifierList;
    }

    public SavingsAccountAddWrapper() {
    }

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    public List<BankAccountIdentifier> getBankAccountIdentifierList() {
        return bankAccountIdentifierList;
    }

    public void setBankAccountIdentifierList(List<BankAccountIdentifier> bankAccountIdentifierList) {
        this.bankAccountIdentifierList = bankAccountIdentifierList;
    }
}
