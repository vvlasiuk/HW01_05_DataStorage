package com.example.android.farmfreshinventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDbHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 14;
    public static final String DATABASE_NAME = "Inventory.db";

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProductContract.ProductEntry.SQL_CREATE_ENTRIES);

        db.execSQL(ProductContract.ProductEntry.SQL_CREATE_ENTRIES_Supplier);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(ProductContract.ProductEntry.SQL_DELETE_ENTRIES);
        db.execSQL(ProductContract.ProductEntry.SQL_DELETE_ENTRIES_Supplier);
        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
