package de.fau.amos.virtualledger.server.model;


import java.util.List;

public class SavingsAccountAddWrapper {

    private SavingsAccountEntity savingsAccountEntity;
    private List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList;
    private List<String> usersEmails;

    public SavingsAccountAddWrapper(SavingsAccountEntity savingsAccountEntity, List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList) {
        this.savingsAccountEntity = savingsAccountEntity;
        this.bankAccountIdentifierEntityList = bankAccountIdentifierEntityList;
    }

    public SavingsAccountAddWrapper() {
    }

    public SavingsAccountEntity getSavingsAccountEntity() {
        return savingsAccountEntity;
    }

    public void setSavingsAccountEntity(SavingsAccountEntity savingsAccountEntity) {
        this.savingsAccountEntity = savingsAccountEntity;
    }

    public List<BankAccountIdentifierEntity> getBankAccountIdentifierEntityList() {
        return bankAccountIdentifierEntityList;
    }

    public void setBankAccountIdentifierEntityList(List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList) {
        this.bankAccountIdentifierEntityList = bankAccountIdentifierEntityList;
    }

    public List<String> getUsersEmails() {
        return usersEmails;
    }

    public void setUsersEmails(List<String> usersEmails) {
        this.usersEmails = usersEmails;
    }
}
