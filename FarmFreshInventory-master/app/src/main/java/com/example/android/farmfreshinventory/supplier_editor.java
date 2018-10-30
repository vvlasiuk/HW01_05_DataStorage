package com.example.android.farmfreshinventory;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.ContentValues;
import com.example.android.farmfreshinventory.data.ProductDbHelper;
import com.example.android.farmfreshinventory.data.ProductContract;
import android.content.Intent;

public class supplier_editor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_editor);


    }

    public void onClickSaveItem(View view) {
        ContentValues values;

        EditText item_supplier_name_id = findViewById(R.id.item_supplier_name_id);
        String nameSupplier = item_supplier_name_id.getText().toString();

        SQLiteDatabase db;
        ProductDbHelper dbHelper;

        dbHelper = new ProductDbHelper(this);
        db = dbHelper.getWritableDatabase();

        values = new ContentValues();
        values.put("supplier_name", nameSupplier);
        Long newRowId  = db.insert(ProductContract.ProductEntry.TABLE_NAME_Supplier, null, values);

//        int supplierBobID = newRowId.intValue();

        item_supplier_name_id.setText("");

        Intent intent = new Intent(this, list_suppliers.class);
        startActivity(intent);

    }
}
