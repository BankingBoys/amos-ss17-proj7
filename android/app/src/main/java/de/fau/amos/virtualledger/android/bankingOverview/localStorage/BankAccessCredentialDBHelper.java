package de.fau.amos.virtualledger.android.bankingOverview.localStorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class BankAccessCredentialDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_CREATE = "CREATE TABLE " + BankAccessCredentialDBConstants.TABLE_NAME + " (_id INTEGER PRIMARY KEY, " + BankAccessCredentialDBConstants.COLUMN_NAME_USER + " TEXT. " + BankAccessCredentialDBConstants.COLUMN_NAME_ACCESS_ID + " TEXT, " + BankAccessCredentialDBConstants.COLUMN_NAME_PIN + " TEXT)";
    private static final String DATABASE_DELETE = "DROP TABLE IF EXISTS " + BankAccessCredentialDBConstants.TABLE_NAME;

    BankAccessCredentialDBHelper(final Context context) {
        super(context, BankAccessCredentialDBConstants.DATABASE_NAME, null, BankAccessCredentialDBConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL(DATABASE_DELETE);
        onCreate(db);
    }
}
