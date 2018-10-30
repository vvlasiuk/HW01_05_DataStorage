package com.example.android.farmfreshinventory.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.farmfreshinventory.R;

public class ProductAdapter extends CursorAdapter {

    public ProductAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        ImageView productImg = (ImageView) view.findViewById(R.id.product_image_id);
        TextView productName = (TextView) view.findViewById(R.id.product_name_id);
        TextView productUnitPrice = (TextView) view.findViewById(R.id.product_unit_price_id);
        TextView productQuantity = (TextView) view.findViewById(R.id.product_quantity_id);
        ImageButton saleButton = (ImageButton) view.findViewById(R.id.product_sale_btn_id);
        TextView productStatus = (TextView) view.findViewById(R.id.product_status_id);

        int _id = cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry._ID));
        String cursorImageURI = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE_URI));
        String cursorProdName = cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME));
        int cursorPrice = cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_UNIT_PRICE));
        int cursorQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY));

        productImg.setImageURI(Uri.parse(cursorImageURI));
        productName.setText(cursorProdName);
        productUnitPrice.setText(Integer.toString(cursorPrice));
        productQuantity.setText(Integer.toString(cursorQuantity));
        productStatus.setText("In Stock");

        Product productObj = new Product(_id,cursorQuantity);
        saleButton.setTag(productObj);

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Product obj = (Product) v.getTag();
                String selection = ProductContract.ProductEntry._ID + "= ?";
                String[] selectionArgs = {Integer.toString(obj.getmProductId())};
                Uri updateURI = Uri.withAppendedPath(ProductContract.ProductEntry.CONTENT_URI, Integer.toString(obj.getmProductId()));
                if (obj.getmProductQuantity() > 0) {
                    ContentValues values = new ContentValues();
                    values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY, obj.getmProductQuantity() - 1);
                    int count = context.getContentResolver().update(updateURI, values, selection, selectionArgs);
                }

            }
        });

        if (cursorQuantity == 0) {
            productStatus.setText("Out of Stock");

        }

    }
}
