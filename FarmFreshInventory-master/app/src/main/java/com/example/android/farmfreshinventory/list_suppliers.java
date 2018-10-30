package com.example.android.farmfreshinventory;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.farmfreshinventory.data.ProductDbHelper;
import com.example.android.farmfreshinventory.data.ProductContract;

import java.util.ArrayList;
import java.util.List;

public class list_suppliers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_suppliers);

        SQLiteDatabase db;
        ProductDbHelper dbHelper;

        dbHelper = new ProductDbHelper(this);
        db = dbHelper.getReadableDatabase();

        String[] projection = {ProductContract.ProductEntry._ID, ProductContract.ProductEntry.COLUMN_NAME_SUPPLIER_NAME};
//        String[] projection = {ProductContract.ProductEntry._ID};

        // Делаем запрос
        Cursor cursor = db.query(
                ProductContract.ProductEntry.TABLE_NAME_Supplier,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки


        ListView listView = (ListView) findViewById(R.id.list_suppliers_view_id);
//        View emptyView = (View) findViewById(R.id.empty_view_id);
        //productAdapter = new ProductAdapter(this, null);
//        getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);

//        String[] names = {"Иван", "Марья", "Петр", "Антон", "Даша", "Борис"};
        List<String> suppliersList = new ArrayList<String>();
        int nameColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_SUPPLIER_NAME);

        while (cursor.moveToNext()) {
            String currentName = cursor.getString(nameColumnIndex);
            suppliersList.add(currentName);
        }


        ArrayAdapter<String> supplierAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, suppliersList);
        listView.setAdapter(supplierAdapter);

    }
}
