package com.example.android.farmfreshinventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.android.farmfreshinventory.data.ProductContract;
import java.io.FileNotFoundException;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText mProductName;
    private EditText mProductUnitPrice;
    private EditText mProductQuantity;
    private EditText mProductSupplierName;
    private EditText mProductSupplierPhone;
    private EditText mProductSupplierEmail;
    private Button mProductAddImageBtn;
    private ImageView mProductImage;
    private Button mIncrementBtn;
    private Button mDecrementBtn;
    private int item_qty = 0;
    private String imageURI = "";
    private static final int LOADER_MANAGER_ID = 0;
    private boolean mProductHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            mProductHasChanged = true;
            return false;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mProductName = (EditText) findViewById(R.id.item_name_id);
        mProductUnitPrice = (EditText) findViewById(R.id.unit_item_price_id);
        mProductQuantity = (EditText) findViewById(R.id.item_quantity_id);
        mIncrementBtn = (Button) findViewById(R.id.increment_qty_btn_id);
        mDecrementBtn = (Button) findViewById(R.id.decrement_qty_btn_id);
        mProductSupplierName = (EditText) findViewById(R.id.supplier_name_id);
        mProductSupplierPhone = (EditText) findViewById(R.id.supplier_phone_id);
        mProductSupplierEmail = (EditText) findViewById(R.id.supplier_email_id);
        mProductAddImageBtn = (Button) findViewById(R.id.upload_image_btn_id);
        mProductImage = (ImageView) findViewById(R.id.item_image_id);

        mProductName.setOnTouchListener(mTouchListener);
        mProductUnitPrice.setOnTouchListener(mTouchListener);
        mProductQuantity.setOnTouchListener(mTouchListener);
        mIncrementBtn.setOnTouchListener(mTouchListener);
        mDecrementBtn.setOnTouchListener(mTouchListener);
        mProductSupplierName.setOnTouchListener(mTouchListener);
        mProductSupplierPhone.setOnTouchListener(mTouchListener);
        mProductSupplierEmail.setOnTouchListener(mTouchListener);
        mProductAddImageBtn.setOnTouchListener(mTouchListener);

        item_qty = Integer.parseInt(mProductQuantity.getText().toString());
        mIncrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIncrementQty();

            }
        });
        mDecrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDecrementQty();
            }
        });
        mProductAddImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });

        if (getIntent().getDataString() != null) {

            getSupportActionBar().setTitle("Edit Product");
            getSupportLoaderManager().initLoader(LOADER_MANAGER_ID, null, this);

        } else {

            getSupportActionBar().setTitle("Add New Product");
            invalidateOptionsMenu();

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        super.onPrepareOptionsMenu(menu);

        if (getIntent().getData() == null) {
            MenuItem menuItem = menu.findItem(R.id.delete_product_id);
            menuItem.setVisible(false);
        }
        return true;

    }

    private void selectImage() {

        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT > 19) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }

        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);

 }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            Uri targetURI = data.getData();
            Bitmap bitmap;

            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetURI));
                mProductImage.setImageBitmap(bitmap);
                imageURI = targetURI.toString();


            } catch (FileNotFoundException e) {

                e.printStackTrace();

            }

        }

    }

    private void mIncrementQty() {

        item_qty = Integer.parseInt(mProductQuantity.getText().toString());
        item_qty = item_qty + 1;
        mProductQuantity.setText(Integer.toString(item_qty));

    }

    private void mDecrementQty() {

        item_qty = Integer.parseInt(mProductQuantity.getText().toString());
        if (item_qty > 0) {
            item_qty = item_qty - 1;
        }
        mProductQuantity.setText(Integer.toString(item_qty));

    }

    private void mInsertProductData() {

        String productName = mProductName.getText().toString().trim();
        int productUnitPrice = Integer.parseInt(mProductUnitPrice.getText().toString());
        int qty = Integer.parseInt(mProductQuantity.getText().toString());
        String SupplierName = mProductSupplierName.getText().toString().trim();
        String SupplierPhone = mProductSupplierPhone.getText().toString().trim();
        String SupplierEmail = mProductSupplierEmail.getText().toString().trim();
        String productImageUri = imageURI;

        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, productName);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_UNIT_PRICE, productUnitPrice);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY, qty);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_NAME, SupplierName);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_EMAIL, SupplierEmail);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_PHONE, SupplierPhone);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE_URI, productImageUri);

        Uri productUri = getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);

        if (productUri != null) {
            Toast.makeText(this, "Product Inserted", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "Error in Inserted", Toast.LENGTH_SHORT).show();
        }

    }


    private void mUpdateProductData() {

        String selection = ProductContract.ProductEntry._ID + "=?";
        long item_id = ContentUris.parseId(getIntent().getData());
        String[] selectionArgs = {String.valueOf(item_id)};
        String productName = mProductName.getText().toString().trim();
        int productUnitPrice = Integer.parseInt(mProductUnitPrice.getText().toString());
        int qty = Integer.parseInt(mProductQuantity.getText().toString());
        String SupplierName = mProductSupplierName.getText().toString().trim();
        String SupplierPhone = mProductSupplierPhone.getText().toString().trim();
        String SupplierEmail = mProductSupplierEmail.getText().toString().trim();
        String productImageUri = imageURI;

        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, productName);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_UNIT_PRICE, productUnitPrice);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY, qty);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_NAME, SupplierName);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_EMAIL, SupplierEmail);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_PHONE, SupplierPhone);
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE_URI, productImageUri);

        int update_count = getContentResolver().update(getIntent().getData(), values, selection, selectionArgs);

        if (update_count != 0) {
            Toast.makeText(this, "Product Updated", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "Error in Product Update", Toast.LENGTH_SHORT).show();
        }
    }

    private void mDeleteProductData() {

        String selection = ProductContract.ProductEntry._ID + "=?";
        long item_id = ContentUris.parseId(getIntent().getData());
        String[] selectionArgs = {String.valueOf(item_id)};

        int delete_count = getContentResolver().delete(getIntent().getData(), selection, selectionArgs);

        if (delete_count != 0) {
            Toast.makeText(this, "Product Deleted", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "Error in Product Delete", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                if (!mProductHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                } else {
                    showAlertDialogForHomeButton();
                    return true;
                }

            case R.id.save_new_product_id:
                if (getIntent().getData() != null) {

                    if (validateUserInputs()) {
                        mUpdateProductData();
                        finish();
                    }

                } else {

                    if (validateUserInputs()) {
                        mInsertProductData();
                        finish();
                    }

                }

              return true;

            case R.id.delete_product_id:
                showAlertDialogForDeleteProduct();
                return true;

            case R.id.order_more_id:
                showAlertDialogForOrderMoreProducts();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    private boolean validateUserInputs() {

        boolean flag = true;

        if (mProductName.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Enter Valid Product Name", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (mProductUnitPrice.getText().toString().length() == 0) {
            Toast.makeText(this, "Enter Valid Product Price", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (mProductQuantity.getText().toString().length() == 0) {
            Toast.makeText(this, "Enter Valid Product Quantity", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (mProductSupplierName.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Enter Valid Supplier Name", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (mProductSupplierPhone.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Enter Valid Supplier Phone", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (mProductSupplierEmail.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Enter Valid Supplier Email", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (imageURI.isEmpty()) {
            Toast.makeText(this, "Upload Product Image", Toast.LENGTH_SHORT).show();
            flag = false;
        }


        if (flag) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void onBackPressed() {

        if (!mProductHasChanged) {
            super.onBackPressed();
            return;
        } else {
            showAlertDialogForBackButton();
        }

    }

    private void showAlertDialogForBackButton() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and quit editing?");
        builder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Keep Editing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    private void showAlertDialogForHomeButton() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and quit editing?");
        builder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NavUtils.navigateUpFromSameTask(EditorActivity.this);
            }
        });
        builder.setNegativeButton("Keep Editing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    private void showAlertDialogForDeleteProduct() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do You Want to Delete the Product ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mDeleteProductData();
                finish();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void showAlertDialogForOrderMoreProducts() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Order Item By Phone or Email");
        builder.setNegativeButton("Phone", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mProductSupplierPhone.getText().toString()));
                startActivity(intent);


            }
        }).setPositiveButton("Email", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String email = mProductSupplierEmail.getText().toString();
                String subject = "Order of Product " + mProductName.getText().toString();

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = new String[]{
                BaseColumns._ID,
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY,
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_UNIT_PRICE,
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE_URI,
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_NAME,
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_EMAIL,
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_PHONE
        };


        String selection = ProductContract.ProductEntry._ID + "=?";
        long item_id = ContentUris.parseId(getIntent().getData());
        String[] selectionArgs = {String.valueOf(item_id)};

        return new CursorLoader(this, getIntent().getData(), projection, selection, selectionArgs, null);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        while (data.moveToNext()) {

            int _id = data.getInt(data.getColumnIndexOrThrow(ProductContract.ProductEntry._ID));
            String cursorProdName = data.getString(data.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME));
            int cursorPrice = data.getInt(data.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_UNIT_PRICE));
            int cursorQuantity = data.getInt(data.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY));
            String cursorSupplierName = data.getString(data.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_NAME));
            String cursorSupplierPhone = data.getString(data.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_PHONE));
            String cursorSupplierEmail = data.getString(data.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_SUPPLIER_EMAIL));

            mProductName.setText(cursorProdName);
            mProductUnitPrice.setText(String.valueOf(cursorPrice));
            mProductQuantity.setText(String.valueOf(cursorQuantity));
            mProductSupplierName.setText(cursorSupplierName);
            mProductSupplierPhone.setText(cursorSupplierPhone);
            mProductSupplierEmail.setText(cursorSupplierEmail);
            Uri imageUriPath = Uri.parse(data.getString(data.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE_URI)));
            mProductImage.setImageURI(imageUriPath);
            imageURI = imageUriPath.toString();

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

}
