package com.example.esxpense_tracking_app_risfath;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpenseDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expenses.db";
    private static final int DATABASE_VERSION = 1;

    public ExpenseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_EXPENSES_TABLE = "CREATE TABLE " +
                ExpenseContract.ExpenseEntry.TABLE_NAME + " (" +
                ExpenseContract.ExpenseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ExpenseContract.ExpenseEntry.COLUMN_AMOUNT + " REAL NOT NULL, " +
                ExpenseContract.ExpenseEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                ExpenseContract.ExpenseEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                ExpenseContract.ExpenseEntry.COLUMN_NOTES + " TEXT" +
                ");";

        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " +
                ExpenseContract.UserEntry.TABLE_NAME + " (" +
                ExpenseContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ExpenseContract.UserEntry.COLUMN_USERNAME + " TEXT NOT NULL, " +
                ExpenseContract.UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_EXPENSES_TABLE);
        db.execSQL(SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ExpenseContract.ExpenseEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ExpenseContract.UserEntry.TABLE_NAME);
        onCreate(db);
    }

    public void deleteExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ExpenseContract.ExpenseEntry.TABLE_NAME, ExpenseContract.ExpenseEntry._ID + "=?", new String[]{String.valueOf(id)});
    }
}
