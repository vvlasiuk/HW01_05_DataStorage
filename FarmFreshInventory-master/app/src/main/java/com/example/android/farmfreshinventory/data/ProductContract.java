package com.example.android.farmfreshinventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class ProductContract {

    private ProductContract() {

    }

    public static class ProductEntry implements BaseColumns {

        public static final String CONTENT_AUTHORITY = "com.example.android.farmfreshinventory";

        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        public static final String PRODUCT_PATH = "products";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PRODUCT_PATH);
        public static final Uri CONTENT_URI_SUPPLIER = Uri.withAppendedPath(BASE_CONTENT_URI, "suppliers");


        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of products.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PRODUCT_PATH;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single product.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PRODUCT_PATH;


        public static final String TABLE_NAME = "products";
        public static final String TABLE_NAME_Supplier = "Suppliers";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME_PRODUCT_NAME = "product_name";
        public static final String COLUMN_NAME_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_NAME_PRODUCT_UNIT_PRICE = "product_unit_price";
        public static final String COLUMN_NAME_PRODUCT_QUANTITY = "product_quantity";
        public static final String COLUMN_NAME_PRODUCT_SUPPLIER_NAME = "product_supplier_name";
        public static final String COLUMN_NAME_PRODUCT_SUPPLIER_ID = "product_supplier_ID";
        public static final String COLUMN_NAME_PRODUCT_SUPPLIER_PHONE = "product_supplier_phone";
        public static final String COLUMN_NAME_PRODUCT_SUPPLIER_EMAIL = "product_supplier_email";
        public static final String COLUMN_NAME_PRODUCT_IMAGE_URI = "product_image_uri";


        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + ProductEntry.TABLE_NAME + " (" +
                        ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ProductEntry.COLUMN_NAME_PRODUCT_NAME + " TEXT NOT NULL," +
                        ProductEntry.COLUMN_NAME_PRODUCT_UNIT_PRICE + " INTEGER NOT NULL," +
                        ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY + " INTEGER NOT NULL ," +
                        ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_NAME + " TEXT NOT NULL ," +
//                        ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_ID + " INTEGER ," +
                        ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_PHONE + " TEXT NOT NULL," +
                        ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_EMAIL + " TEXT NOT NULL," +
                        ProductEntry.COLUMN_NAME_PRODUCT_IMAGE_URI + " TEXT NOT NULL " + ")";

        public static final String SQL_CREATE_ENTRIES_Supplier =
                "CREATE TABLE " + ProductEntry.TABLE_NAME_Supplier + " (" +
                        ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ProductEntry.COLUMN_NAME_SUPPLIER_NAME + " TEXT NOT NULL " + ")";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME;

        public static final String SQL_DELETE_ENTRIES_Supplier =
                "DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME_Supplier;

    }


}
