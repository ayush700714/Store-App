package com.example.storeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storeapp.data.StoreContract;
import com.example.storeapp.data.StoreDbHelper;

public class Edit extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText c,d,e,f;
    private Uri mCurrentUri;
    private Boolean mHasChanged=false;
    private Button bt;
    private View.OnTouchListener mTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mHasChanged=true;
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);



        c = (EditText)findViewById(R.id.name);
        d = (EditText)findViewById(R.id.qnt);
        e = (EditText)findViewById(R.id.cp);
        f = (EditText)findViewById(R.id.sp);

        bt = (Button)findViewById(R.id.delt);

        c.setOnTouchListener(mTouch);
        d.setOnTouchListener(mTouch);
        e.setOnTouchListener(mTouch);
        f.setOnTouchListener(mTouch);
        Intent intent = getIntent();
        Uri uri = intent.getData();
        mCurrentUri=uri;
        if(uri==null)
        {
            setTitle("Add An Object");
            bt.setVisibility(View.GONE);
        }
        else
        {
            setTitle("Edit An Object");

            getLoaderManager().initLoader(0,null,this);
        }


    }
    public void saved(View view)
    {
        String name = c.getText().toString().trim();
        String qnt =d.getText().toString().trim();
        int cd;
        if(qnt.isEmpty())
        {
            cd=0;
        }
        else
        {
            cd = Integer.parseInt(qnt);
        }
        String cp = e.getText().toString().trim();
        int cp2;
        if(cp.isEmpty())
        {
            cp2=0;
        }
        else
        {
            cp2 = Integer.parseInt(cp);
        }
        String sp = f.getText().toString().trim();
        int sp2;
        if(sp.isEmpty())
        {
            sp2=0;
        }
        else
        {
            sp2 = Integer.parseInt(sp);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(StoreContract.StoreTable.COLUMN_ITEM_NAME,name);
        contentValues.put(StoreContract.StoreTable.COLUMN_ITEM_QUANTITY,cd);
        contentValues.put(StoreContract.StoreTable.COLUMN_ITEM_SELLPRICE,sp2);
        contentValues.put(StoreContract.StoreTable.COLUMN_ITEM_COSTPRICE,cp2);
        if(mCurrentUri==null)
        {

            Uri id = getContentResolver().insert(StoreContract.CONTENT_URI,contentValues);
            Toast.makeText(Edit.this,"SAVED",Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
              int rowUpdated = getContentResolver().update(mCurrentUri,contentValues,null,null);
            Toast.makeText(Edit.this,"UPDATED",Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void deleted(View view)
    {
        getContentResolver().delete(mCurrentUri,null,null);
        finish();
    }
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {StoreContract.StoreTable.COLUMN_ITEM_ID, StoreContract.StoreTable.COLUMN_ITEM_NAME, StoreContract.StoreTable.COLUMN_ITEM_QUANTITY,StoreContract.StoreTable.COLUMN_ITEM_COSTPRICE, StoreContract.StoreTable.COLUMN_ITEM_SELLPRICE};
        String selection = StoreContract.StoreTable.COLUMN_ITEM_ID + "=?";
        String[] selectionArgs ={String.valueOf(ContentUris.parseId(mCurrentUri))};
        return new CursorLoader(this, StoreContract.CONTENT_URI,projection,selection,selectionArgs,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if(cursor.moveToFirst())
        {
            String name = cursor.getString(cursor.getColumnIndex(StoreContract.StoreTable.COLUMN_ITEM_NAME));
            int qnt = cursor.getInt(cursor.getColumnIndex(StoreContract.StoreTable.COLUMN_ITEM_QUANTITY));
            int cp= cursor.getInt(cursor.getColumnIndex(StoreContract.StoreTable.COLUMN_ITEM_COSTPRICE));
            int sp = cursor.getInt(cursor.getColumnIndex(StoreContract.StoreTable.COLUMN_ITEM_SELLPRICE));
            c.setText(name);
            d.setText(""+qnt);
            e.setText(""+cp);
            f.setText(""+sp);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
    @Override
    public void onBackPressed()
    {
         if(!mHasChanged)
         {
             super.onBackPressed();
             return;
         }
         onUnsavedChange();
    }
    public void onUnsavedChange()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and Quit Editing");
        builder.setTitle("Unsaved Changes");
        builder.setPositiveButton("DISCARD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("KEEP EDITING", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
