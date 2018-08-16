package com.example.android.bookstore;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.bookstore.data.BookContract;
import com.example.android.bookstore.data.BookDbHelper;

public class AddActivity extends AppCompatActivity{

    private EditText mNameEditText;

    private EditText mPriceEditText;

    private EditText mQtyEditText;

    private EditText mSupplierName;

    private EditText mSupplierPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mNameEditText = findViewById(R.id.book_title_add);
        mPriceEditText = findViewById(R.id.price_add);
        mQtyEditText = findViewById(R.id.qty_add);
        mSupplierName = findViewById(R.id.supplier_name_add);
        mSupplierPhone = findViewById(R.id.suppler_phone_add);

    }

    private void insertBook() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String priceInt = mPriceEditText.getText().toString().trim();
        String qtyInt = mQtyEditText.getText().toString().trim();
        String supplierNameString = mSupplierName.getText().toString().trim();
        String supplierPhoneString = mSupplierPhone.getText().toString().trim();
        int price = Integer.parseInt(priceInt);
        int qty = Integer.parseInt(qtyInt);
        // Create database helper
        BookDbHelper mDbHelper = new BookDbHelper(this);
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookContract.BookEntry.COLUMN_BOOK_NAME, nameString);
        values.put(BookContract.BookEntry.COLUMN_BOOK_PRICE, price);
        values.put(BookContract.BookEntry.COLUMN_BOOK_QTY, qty);
        values.put(BookContract.BookEntry.COLUMN_SUPPLIER_NAME, supplierNameString);
        values.put(BookContract.BookEntry.COLUMN_SUPPLIER_PHONE, supplierPhoneString);

        long newRowId = db.insert(BookContract.BookEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {

            Toast.makeText(this, "Error with saving book", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "book saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:

                insertBook();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
