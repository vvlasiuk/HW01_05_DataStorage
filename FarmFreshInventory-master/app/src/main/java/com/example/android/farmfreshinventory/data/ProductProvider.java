package com.example.android.farmfreshinventory.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ProductProvider extends ContentProvider {


    public static final int PRODUCT = 100;
    public static final int PRODUCT_ID = 101;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        uriMatcher.addURI(ProductContract.ProductEntry.CONTENT_AUTHORITY, "products", 100);
        uriMatcher.addURI(ProductContract.ProductEntry.CONTENT_AUTHORITY, "products/#", 101);

    }

    private SQLiteOpenHelper mProductDBHelper;

    @Override
    public boolean onCreate() {

        mProductDBHelper = new ProductDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mProductDBHelper.getReadableDatabase();
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                cursor = database.query(ProductContract.ProductEntry.TABLE_NAME, projection, null, null, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;

            case PRODUCT_ID:

                cursor = database.query(ProductContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;


            default:
                throw new IllegalArgumentException("Invalid Query URI" + uri);

        }


    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                return ProductContract.ProductEntry.CONTENT_LIST_TYPE;
            case PRODUCT_ID:
                return ProductContract.ProductEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unable to process the Uri" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                SQLiteDatabase database = mProductDBHelper.getWritableDatabase();
                long id = database.insert(ProductContract.ProductEntry.TABLE_NAME, null, values);
                if (id != -1) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return Uri.withAppendedPath(ProductContract.ProductEntry.CONTENT_URI, String.valueOf(id));
                } else {
                    return null;
                }
            default:
                throw new IllegalArgumentException("Unable to process URI" + uri);

        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mProductDBHelper.getWritableDatabase();
        int count;

        switch (uriMatcher.match(uri)) {
            case PRODUCT:

                count = database.delete(ProductContract.ProductEntry.TABLE_NAME, null, null);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return count;


            case PRODUCT_ID:


                count = database.delete(ProductContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return count;

            default:
                throw new IllegalArgumentException("Unable to process the Delete URI" + uri.toString());
        }


    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mProductDBHelper.getWritableDatabase();
        int count;

        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                count = database.update(ProductContract.ProductEntry.TABLE_NAME, values, null, null);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return count;

            case PRODUCT_ID:

                count = database.update(ProductContract.ProductEntry.TABLE_NAME, values, selection, selectionArgs);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return count;


            default:
                throw new IllegalArgumentException("Unable to process Update URI" + uri.toString());

        }

    }
}
