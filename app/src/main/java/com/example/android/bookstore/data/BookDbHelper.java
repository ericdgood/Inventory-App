package com.example.android.bookstore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = BookDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "library.db";

    private static final int DATABASE_VERSION = 1;

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_BOOKS_TABLE =  "CREATE TABLE " + BookContract.BookEntry.TABLE_NAME + " ("
                + BookContract.BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BookContract.BookEntry.COLUMN_BOOK_NAME + " TEXT NOT NULL, "
                + BookContract.BookEntry.COLUMN_BOOK_PRICE + " TEXT, "
                + BookContract.BookEntry.COLUMN_BOOK_QTY + " INTEGER NOT NULL, "
                + BookContract.BookEntry.COLUMN_SUPPLIER_NAME+ " TEXT, "
                + BookContract.BookEntry.COLUMN_SUPPLIER_PHONE + " INTEGER NOT NULL DEFAULT 0);";
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
