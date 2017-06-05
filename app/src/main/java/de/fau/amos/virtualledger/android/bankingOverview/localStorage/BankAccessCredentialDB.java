package de.fau.amos.virtualledger.android.bankingOverview.localStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BankAccessCredentialDB {

    private SQLiteDatabase database;

    public BankAccessCredentialDB(final Context context) {
        final BankAccessCredentialDBHelper dbHelper = new BankAccessCredentialDBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void persist(final String user, final String bankCode, final String bankLogin, final String pin, final String accessId, final String accountId) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_USER, user);
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_BANK_CODE, bankCode);
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_BANK_LOGIN, bankLogin);
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_PIN, pin);
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_ACCESSID, accessId);
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_ACCOUNTID, accountId);
        database.insert(BankAccessCredentialDBConstants.TABLE_NAME, null, contentValues);
    }

    public String getPin(final String user, final String bankCode, final String bankLogin, final String accessId, final String accountId) {
        final String[] columns = new String[] {BankAccessCredentialDBConstants.COLUMN_NAME_PIN};
        final Cursor cursor = database.query(true, BankAccessCredentialDBConstants.TABLE_NAME, columns, BankAccessCredentialDBConstants.COLUMN_NAME_USER + " = ?" + " AND " + BankAccessCredentialDBConstants.COLUMN_NAME_BANK_CODE + " = ?" + " AND " + BankAccessCredentialDBConstants.COLUMN_NAME_BANK_LOGIN + " = ?"  + " AND " + BankAccessCredentialDBConstants.COLUMN_NAME_ACCESSID + " = ?"  + " AND " + BankAccessCredentialDBConstants.COLUMN_NAME_ACCOUNTID + " = ?", new String[] {user, bankCode, bankLogin, accessId, accountId}, null, null, null, null);
        final boolean success = cursor.moveToFirst();
        if(!success) {
            return null;
        }
        final String result = cursor.getString(0);
        cursor.close();
        return result;
    }

}
