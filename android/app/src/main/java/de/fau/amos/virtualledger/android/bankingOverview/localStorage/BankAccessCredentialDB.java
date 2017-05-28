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

    public void persist(final String user, final String accessId, final String pin) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_USER, user);
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_ACCESS_ID, accessId);
        contentValues.put(BankAccessCredentialDBConstants.COLUMN_NAME_PIN, pin);
        database.insert(BankAccessCredentialDBConstants.TABLE_NAME, null, contentValues);
    }

    public String getPin(final String user, final String accessId) {
        final String[] columns = new String[] {BankAccessCredentialDBConstants.COLUMN_NAME_PIN};
        final Cursor cursor = database.query(true, BankAccessCredentialDBConstants.TABLE_NAME, columns, BankAccessCredentialDBConstants.COLUMN_NAME_USER + " = " + user + " AND " + BankAccessCredentialDBConstants.COLUMN_NAME_ACCESS_ID + " = " + accessId, null, null, null, null, null);
        cursor.moveToFirst();
        final String result = cursor.getString(0);
        cursor.close();
        return result;
    }

}
