package com.example.storeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.net.Uri;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.example.storeapp.data.StoreContract;
import com.example.storeapp.data.StoreDbHelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private StoreCursorAdapter s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button a  = (Button)findViewById(R.id.bt);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(MainActivity.this,Edit.class);
                startActivity(b);
            }
        });
        Button dl  = (Button)findViewById(R.id.dl);
        dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContentResolver().delete(StoreContract.CONTENT_URI,null,null);
            }
        });
        ListView lv  = (ListView)findViewById(R.id.list);
        View empty = findViewById(R.id.empty);
        lv.setEmptyView(empty);
        s  = new StoreCursorAdapter(this,null);
        lv.setAdapter(s);
         getLoaderManager().initLoader(0,null,this);
         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                 Uri uri = ContentUris.withAppendedId(StoreContract.CONTENT_URI,id);
                 Intent intent = new Intent(MainActivity.this,Edit.class);
                 intent.setData(uri);
                 startActivity(intent);
             }
         });
    }
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {StoreContract.StoreTable.COLUMN_ITEM_ID, StoreContract.StoreTable.COLUMN_ITEM_NAME, StoreContract.StoreTable.COLUMN_ITEM_QUANTITY};
        return new CursorLoader(this, StoreContract.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
         s.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
           s.swapCursor(null);
    }




}
