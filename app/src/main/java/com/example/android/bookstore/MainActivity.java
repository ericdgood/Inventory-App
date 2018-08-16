package com.example.android.bookstore;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.bookstore.data.BookContract;
import com.example.android.bookstore.data.BookDbHelper;

public class MainActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private BookDbHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new BookDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection ={
                BookContract.BookEntry._ID,
                BookContract.BookEntry.COLUMN_BOOK_NAME,
                BookContract.BookEntry.COLUMN_BOOK_PRICE,
                BookContract.BookEntry.COLUMN_BOOK_QTY,
                BookContract.BookEntry.COLUMN_SUPPLIER_NAME,
                BookContract.BookEntry.COLUMN_SUPPLIER_PHONE,
        };

        Cursor cursor = db.query(
                BookContract.BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.text_view_books);


        try {

            displayView.setText("The library table contains " + cursor.getCount() + " books.\n\n");
            displayView.append(BookContract.BookEntry._ID + " - " +
                    BookContract.BookEntry.COLUMN_BOOK_NAME + " - " +
                    BookContract.BookEntry.COLUMN_BOOK_PRICE + " - " +
                    BookContract.BookEntry.COLUMN_BOOK_QTY + " - " +
                    BookContract.BookEntry.COLUMN_SUPPLIER_NAME + " - " +
                    BookContract.BookEntry.COLUMN_SUPPLIER_PHONE + "\n");
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(BookContract.BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_PRICE);
            int qtyColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QTY);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_SUPPLIER_PHONE);
            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQty = cursor.getInt(qtyColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQty + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhone));
            }
        } finally {

            cursor.close();
        }
    }

    private void insertBook() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookContract.BookEntry.COLUMN_BOOK_NAME, "7 Habits");
        values.put(BookContract.BookEntry.COLUMN_BOOK_PRICE, 9);
        values.put(BookContract.BookEntry.COLUMN_BOOK_QTY, 3);
        values.put(BookContract.BookEntry.COLUMN_SUPPLIER_NAME, "Steve");
        values.put(BookContract.BookEntry.COLUMN_SUPPLIER_PHONE, 407-999-9999);

        long newRowId = db.insert(BookContract.BookEntry.TABLE_NAME, null, values);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_books:
                insertBook();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_books:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
