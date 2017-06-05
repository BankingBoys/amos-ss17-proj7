package de.fau.amos.virtualledger.android.bankingOverview.localStorage;

class BankAccessCredentialDBConstants {
    static final String DATABASE_NAME = "BankAccessData";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "credentials";
    static final String COLUMN_NAME_USER = "user";
    static final String COLUMN_NAME_BANK_LOGIN = "bankLogin";
    static final String COLUMN_NAME_BANK_CODE = "bankCode";
    static final String COLUMN_NAME_PIN = "pin";
    static final String COLUMN_NAME_ACCESSID = "accessId";
    static final String COLUMN_NAME_ACCOUNTID = "accountID";
}
