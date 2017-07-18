package de.fau.amos.virtualledger.server.model;


import java.util.List;

public class SavingsAccountAddWrapper {

    private SavingsAccountEntity savingsAccountEntity;
    private List<BankAccountIdentifier> bankAccountIdentifierList;
    private List<String> usersEmails;

    public SavingsAccountAddWrapper(SavingsAccountEntity savingsAccountEntity, List<BankAccountIdentifier> bankAccountIdentifierList) {
        this.savingsAccountEntity = savingsAccountEntity;
        this.bankAccountIdentifierList = bankAccountIdentifierList;
    }

    public SavingsAccountAddWrapper() {
    }

    public SavingsAccountEntity getSavingsAccountEntity() {
        return savingsAccountEntity;
    }

    public void setSavingsAccountEntity(SavingsAccountEntity savingsAccountEntity) {
        this.savingsAccountEntity = savingsAccountEntity;
    }

    public List<BankAccountIdentifier> getBankAccountIdentifierList() {
        return bankAccountIdentifierList;
    }

    public void setBankAccountIdentifierList(List<BankAccountIdentifier> bankAccountIdentifierList) {
        this.bankAccountIdentifierList = bankAccountIdentifierList;
    }

    public List<String> getUsersEmails() {
        return usersEmails;
    }

    public void setUsersEmails(List<String> usersEmails) {
        this.usersEmails = usersEmails;
    }
}
