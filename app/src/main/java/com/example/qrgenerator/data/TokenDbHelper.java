package com.example.qrgenerator.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TokenDbHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "token.db";
    public final static int DATABASE_VERSION = 1;

    public TokenDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + TokenContract.TokenEntry.TABLE_NAME + "("
                + TokenContract.TokenEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TokenContract.TokenEntry.COLUMN_BOOKING_DATE + " TEXT NOT NULL, "
                + TokenContract.TokenEntry.COLUMN_BOOKING_HR + " INTEGER NOT NULL, "
                + TokenContract.TokenEntry.COLUMN_BOOKING_MIN + " INTEGER NOT NULL, "
                + TokenContract.TokenEntry.COLUMN_BOOKING_AMPM + " INTEGER NOT NULL, "
                + TokenContract.TokenEntry.COLUMN_ENDING_DATE + " TEXT NOT NULL, "
                + TokenContract.TokenEntry.COLUMN_ENDING_HR + " INTEGER NOT NULL, "
                + TokenContract.TokenEntry.COLUMN_ENDING_MIN + " INTEGER NOT NULL, "
               + TokenContract.TokenEntry.COLUMN_ENDING_AMPM + " INTEGER NOT NULL, "
        + TokenContract.TokenEntry.COLUMN_TOKEN + " TEXT NOT NULL);";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
