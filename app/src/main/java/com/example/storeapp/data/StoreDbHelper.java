package com.example.storeapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.storeapp.data.StoreContract.StoreTable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StoreDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="store.db";
    public static final int DATABASE_VERSION=1;

    public StoreDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       String CREATE_TABLE="CREATE TABLE "+StoreTable.TABLE_NAME + "(" + StoreTable.COLUMN_ITEM_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT , "+
                StoreTable.COLUMN_ITEM_NAME + " TEXT NOT NULL," +
                StoreTable.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL,"+
                StoreTable.COLUMN_ITEM_COSTPRICE+" INTEGER NOT NULL,"+
                StoreTable.COLUMN_ITEM_SELLPRICE+" INTEGER NOT NULL );";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
