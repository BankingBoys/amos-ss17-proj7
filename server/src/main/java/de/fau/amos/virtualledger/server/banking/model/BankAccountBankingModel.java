package de.fau.amos.virtualledger.server.banking.model;

/**
 * Created by Georg on 20.05.2017.
 */
public class BankAccountBankingModel {

    private String bankAccessId = "";
    private BankAccountBalanceBankingModel bankAccountBalance = null;
    private String bicHbciAccount = "";
    private String blzHbciAccount = "";
    private String countryHbciAccount = "";
    private String currencyHbciAccount = "";
    private String ibanHbciAccount = "";
    private String id = "";
    private String nameHbciAccount = "";
    private String numberHbciAccount = "";
    private String typeHbciAccount = "";

    public BankAccountBankingModel() {
    }

    public String getBankAccessId() {
        return bankAccessId;
    }

    public void setBankAccessId(String bankAccessId) {
        this.bankAccessId = bankAccessId;
    }

    public BankAccountBalanceBankingModel getBankAccountBalance() {
        return bankAccountBalance;
    }

    public void setBankAccountBalance(BankAccountBalanceBankingModel bankAccountBalance) {
        this.bankAccountBalance = bankAccountBalance;
    }

    public String getBicHbciAccount() {
        return bicHbciAccount;
    }

    public void setBicHbciAccount(String bicHbciAccount) {
        this.bicHbciAccount = bicHbciAccount;
    }

    public String getBlzHbciAccount() {
        return blzHbciAccount;
    }

    public void setBlzHbciAccount(String blzHbciAccount) {
        this.blzHbciAccount = blzHbciAccount;
    }

    public String getCountryHbciAccount() {
        return countryHbciAccount;
    }

    public void setCountryHbciAccount(String countryHbciAccount) {
        this.countryHbciAccount = countryHbciAccount;
    }

    public String getCurrencyHbciAccount() {
        return currencyHbciAccount;
    }

    public void setCurrencyHbciAccount(String currencyHbciAccount) {
        this.currencyHbciAccount = currencyHbciAccount;
    }

    public String getIbanHbciAccount() {
        return ibanHbciAccount;
    }

    public void setIbanHbciAccount(String ibanHbciAccount) {
        this.ibanHbciAccount = ibanHbciAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameHbciAccount() {
        return nameHbciAccount;
    }

    public void setNameHbciAccount(String nameHbciAccount) {
        this.nameHbciAccount = nameHbciAccount;
    }

    public String getNumberHbciAccount() {
        return numberHbciAccount;
    }

    public void setNumberHbciAccount(String numberHbciAccount) {
        this.numberHbciAccount = numberHbciAccount;
    }

    public String getTypeHbciAccount() {
        return typeHbciAccount;
    }

    public void setTypeHbciAccount(String typeHbciAccount) {
        this.typeHbciAccount = typeHbciAccount;
    }
}
