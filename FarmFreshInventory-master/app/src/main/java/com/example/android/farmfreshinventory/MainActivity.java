package com.example.android.farmfreshinventory;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.android.farmfreshinventory.data.ProductAdapter;
import com.example.android.farmfreshinventory.data.ProductContract;
import com.example.android.farmfreshinventory.data.ProductDbHelper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CURSOR_LOADER_ID = 1;
    String[] projection;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addProductBtn = (FloatingActionButton) findViewById(R.id.add_new_product_btn_id);
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);

            }
        });

        productAdapter = new ProductAdapter(this, null);
        getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);

        ListView listView = (ListView) findViewById(R.id.list_view_id);
        View emptyView = (View) findViewById(R.id.empty_view_id);
        listView.setAdapter(productAdapter);
        if (productAdapter.getCount() == 0) {
            listView.setEmptyView(emptyView);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.setData(Uri.withAppendedPath(ProductContract.ProductEntry.CONTENT_URI, String.valueOf(id)));
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.insert_test_data_id:
                mInsertTestData();
                return true;

            case R.id.delete_all_product_id:
                mDeleteAllProductsData();
                return true;

            case R.id.add_supplier_id:
                mAdd_supplier();
                return true;

            case R.id.list_suppliers_id:
                mList_supplier();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void mDeleteAllProductsData() {

        int delete_count = getContentResolver().delete(ProductContract.ProductEntry.CONTENT_URI, null, null);
        if (delete_count > 0) {

            Toast.makeText(this, "Products Deleted" + ProductContract.ProductEntry.CONTENT_URI.toString(), Toast.LENGTH_SHORT);

        } else {

            Toast.makeText(this, "Error in Products Delete" + ProductContract.ProductEntry.CONTENT_URI.toString(), Toast.LENGTH_SHORT);

        }

    }

    private void mAdd_supplier() {
        Intent intent = new Intent(MainActivity.this, supplier_editor.class);
        startActivity(intent);
    }

    private void mList_supplier() {


        Intent intent = new Intent(MainActivity.this, list_suppliers.class);
        startActivity(intent);

        //        if (productAdapter.getCount() == 0) {
//            listView.setEmptyView(emptyView);
//        }

    }

    private void mInsertTestData() {

        ContentValues values;
        Uri imageUri;

        // suppliers
        SQLiteDatabase db;
        ProductDbHelper dbHelper;

        dbHelper = new ProductDbHelper(this);
        db = dbHelper.getWritableDatabase();

        values = new ContentValues();
        values.put("supplier_name", "Bob Ltd");
        Long newRowId  = db.insert(ProductContract.ProductEntry.TABLE_NAME_Supplier, null, values);
        int supplierBobID = newRowId.intValue();

        values = new ContentValues();
        imageUri = getUriToDrawable(this, R.drawable.carrot);
        values.put("product_name", "Carrot");
        values.put("product_unit_price", "15");
        values.put("product_quantity", "10");
        values.put("product_supplier_name", "Richard");
//        values.put("product_supplier_ID", supplierBobID);
        values.put("product_supplier_phone", "123345678");
        values.put("product_supplier_email", "richard@grofers.com");
        values.put("product_image_uri", imageUri.toString());
        getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);

        values = new ContentValues();
        imageUri = getUriToDrawable(this, R.drawable.beet);
        values.put("product_name", "Beet Root");
        values.put("product_unit_price", "11");
        values.put("product_quantity", "10");
        values.put("product_supplier_name", "Henry");
//        values.put("product_supplier_ID", supplierBobID);
        values.put("product_supplier_phone", "123345678");
        values.put("product_supplier_email", "henry@grofers.com");
        values.put("product_image_uri", imageUri.toString());
        getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);

        values = new ContentValues();
        imageUri = getUriToDrawable(this, R.drawable.eggplant);
        values.put("product_name", "Eggplant");
        values.put("product_unit_price", "12");
        values.put("product_quantity", "10");
        values.put("product_supplier_name", "Henry");
//        values.put("product_supplier_ID", supplierBobID);
        values.put("product_supplier_phone", "123345678");
        values.put("product_supplier_email", "henry@grofers.com");
        values.put("product_image_uri", imageUri.toString());
        getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);

        values = new ContentValues();
        imageUri = getUriToDrawable(this, R.drawable.lettuce);
        values.put("product_name", "Lettuce");
        values.put("product_unit_price", "10");
        values.put("product_quantity", "10");
        values.put("product_supplier_name", "Henry");
//        values.put("product_supplier_ID", supplierBobID);
        values.put("product_supplier_phone", "123345678");
        values.put("product_supplier_email", "henry@grofers.com");
        values.put("product_image_uri", imageUri.toString());
        getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);

        values = new ContentValues();
        imageUri = getUriToDrawable(this, R.drawable.onion);
        values.put("product_name", "Onion");
        values.put("product_unit_price", "16");
        values.put("product_quantity", "10");
        values.put("product_supplier_name", "Henry");
//        values.put("product_supplier_ID", supplierBobID);
        values.put("product_supplier_phone", "123345678");
        values.put("product_supplier_email", "henry@grofers.com");
        values.put("product_image_uri", imageUri.toString());
        getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);



    }

    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {

        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId));
        return imageUri;

    }


    @NonNull
    @Override
    public CursorLoader onCreateLoader(int id, @Nullable Bundle args) {

        projection = new String[]{
                BaseColumns._ID,
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY,
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_UNIT_PRICE,
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE_URI
        };

        return new CursorLoader(this, ProductContract.ProductEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Cursor data) {

        productAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

        productAdapter.swapCursor(null);

    }

}
