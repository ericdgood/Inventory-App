package com.example.android.bookstore;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.bookstore.data.BookContract;

public class BookCursorAdapter extends CursorAdapter{

    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.price);
        TextView summaryQtyView = (TextView) view.findViewById(R.id.product_quantity_text_view);
        Button productSaleButton = view.findViewById(R.id.sale_button);

        final int columnIdIndex = cursor.getColumnIndex(BookContract.BookEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_PRICE);
        int qtyColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QTY);
        int productQuantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QTY);

        final String productID = cursor.getString(columnIdIndex);
        String bookName = cursor.getString(nameColumnIndex);
        String bookprice = cursor.getString(priceColumnIndex);
        String bookQty = cursor.getString(qtyColumnIndex);
        final String productQuantity = cursor.getString(productQuantityColumnIndex);

        nameTextView.setText(bookName);
        summaryTextView.setText(bookprice);
        summaryQtyView.setText(bookQty);

        productSaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity Activity = (MainActivity) context;
                Activity.productSaleCount(Integer.valueOf(productID), Integer.valueOf(productQuantity));
            }
    });
}}
