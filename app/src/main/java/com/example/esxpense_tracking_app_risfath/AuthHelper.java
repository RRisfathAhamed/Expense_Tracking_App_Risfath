package com.example.esxpense_tracking_app_risfath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AuthHelper {

    private ExpenseDbHelper dbHelper;

    public AuthHelper(Context context) {
        dbHelper = new ExpenseDbHelper(context);
    }

    public boolean registerUser(String username, String password) {
        if (userExists(username)) {
            return false;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ExpenseContract.UserEntry.COLUMN_USERNAME, username);
        values.put(ExpenseContract.UserEntry.COLUMN_PASSWORD, password);

        long newRowId = db.insert(ExpenseContract.UserEntry.TABLE_NAME, null, values);
        return newRowId != -1;
    }

    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                ExpenseContract.UserEntry._ID
        };

        String selection = ExpenseContract.UserEntry.COLUMN_USERNAME + " = ? AND " +
                ExpenseContract.UserEntry.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                ExpenseContract.UserEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean loginSuccessful = cursor.getCount() > 0;
        cursor.close();
        return loginSuccessful;
    }

    public boolean userExists(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                ExpenseContract.UserEntry._ID
        };

        String selection = ExpenseContract.UserEntry.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                ExpenseContract.UserEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        return userExists;
    }
}
