package de.fau.amos.virtualledger.android.localStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BankAccessCredentialDB {

    private SQLiteDatabase database;

    /**
     *
     */
    public BankAccessCredentialDB(final Context context) {
        final BankAccessCredentialDBHelper dbHelper = new BankAccessCredentialDBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    /**
     *
     */
    public void persist(final String user, final String pin, final String accessId, final String accountId, final String accessName, final String accountName) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_USER, user);
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_PIN, pin);
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_ACCESSID, accessId);
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_ACCOUNTID, accountId);
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_ACCESS_NAME, accessName);
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_ACCOUNT_NAME, accountName);
        database.insert(BankAccessCredentialDBConstants.TABLE_NAME, null, contentValues);
    }

    public void delete(final String user, final String accessId, final String accountId) {
        final String whereClause = BankAccessCredentialDBConstants.COLUMN_NAME_USER + " = ?" + " AND " + BankAccessCredentialDBConstants.COLUMN_NAME_ACCESSID + " = ?" + " AND " + BankAccessCredentialDBConstants.COLUMN_NAME_ACCOUNTID + " = ?";
        final String[] whereArgs = {user, accessId, accountId};
        database.delete(BankAccessCredentialDBConstants.TABLE_NAME, whereClause, whereArgs);
    }

    /**
     *
     */
    public String getPin(final String user, final String accessId, final String accountId) {
        final String[] columns = new String[]{BankAccessCredentialDBConstants.COLUMN_NAME_PIN};
        final Cursor cursor = database.query(true, BankAccessCredentialDBConstants.TABLE_NAME, columns, BankAccessCredentialDBConstants.COLUMN_NAME_USER + " = ?" + " AND " + BankAccessCredentialDBConstants.COLUMN_NAME_ACCESSID + " = ?" + " AND " + BankAccessCredentialDBConstants.COLUMN_NAME_ACCOUNTID + " = ?", new String[]{user, accessId, accountId}, null, null, null, null);
        final boolean success = cursor.moveToFirst();
        if (!success) {
            cursor.close();
            return null;
        }
        final String result = cursor.getString(0);
        cursor.close();
        return result;
    }


    /**
     *
     */
    public String getAccountName(final String user, final String accessId, final String accountId) {
        final String[] columns = new String[]{
                BankAccessCredentialDBConstants.COLUMN_NAME_ACCOUNT_NAME,
        };
        final Cursor cursor = database.query(true, BankAccessCredentialDBConstants.TABLE_NAME, columns, BankAccessCredentialDBConstants.COLUMN_NAME_USER + " = ?" + " AND " + BankAccessCredentialDBConstants.COLUMN_NAME_ACCESSID + " = ?" + " AND " + BankAccessCredentialDBConstants.COLUMN_NAME_ACCOUNTID + " = ?", new String[]{user, accessId, accountId}, null, null, null, null);
        final boolean success = cursor.moveToFirst();
        if (!success) {
            cursor.close();
            return "Bank account not found!";
        }
        final String result = cursor.getString(0);
        cursor.close();
        return result;
    }
}
