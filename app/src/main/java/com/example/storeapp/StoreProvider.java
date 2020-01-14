package com.example.storeapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.storeapp.data.StoreContract;
import com.example.storeapp.data.StoreDbHelper;
import com.example.storeapp.data.StoreContract.StoreTable;

public class StoreProvider extends ContentProvider {

    public StoreDbHelper a;
   public static UriMatcher b= new UriMatcher(UriMatcher.NO_MATCH);
    static
    {
        b.addURI(StoreContract.CONTENT_AUTHORITY,StoreContract.PATH_STORE,100);
        b.addURI(StoreContract.CONTENT_AUTHORITY,StoreContract.PATH_STORE+"/#",101);
    }
    @Override
    public boolean onCreate() {
         a =  new StoreDbHelper(getContext());
        return true;
    }

    public Cursor query(Uri uri,  String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase c = a.getReadableDatabase();
        int match = b.match(uri);
        Cursor cursor;
        switch(match)
        {
            case 100:
                cursor = c.query(StoreTable.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case 101:
                 selection = StoreTable.COLUMN_ITEM_ID + "=?";
                 selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                 cursor = c.query(StoreTable.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                 break;
            default:
                throw new IllegalArgumentException("not valid" + uri);
        }
         cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    public String getType( Uri uri) {
        return null;
    }

    public Uri insert( Uri uri,  ContentValues contentValues) {
        SQLiteDatabase c  = a.getWritableDatabase();
        int match = b.match(uri);
        long e ;
        switch(match)
        {
            case 100:
               e = c.insert(StoreTable.TABLE_NAME,null,contentValues);
               break;
            default:
                throw new IllegalArgumentException("not valid" + uri);
        }
        Cursor cursor = c.query(StoreTable.TABLE_NAME,null,null,null,null,null,null);
        cursor.moveToLast();
        long f = cursor.getLong(cursor.getColumnIndex(StoreTable.COLUMN_ITEM_ID));
        Uri uri2 = ContentUris.withAppendedId(StoreContract.CONTENT_URI,f);
        getContext().getContentResolver().notifyChange(uri,null);
        return uri2;
    }

    @Override
    public int delete( Uri uri,  String s,  String[] strings) {
        int match = b.match(uri);
        SQLiteDatabase c  = a.getWritableDatabase();
        int d;
        switch(match)
        {
            case 100:
                d =c.delete(StoreTable.TABLE_NAME,s,strings);
                break;
            case 101:
                s = StoreTable.COLUMN_ITEM_ID  + "=?";
                strings  = new String[]{String.valueOf(ContentUris.parseId(uri))};
                d =c.delete(StoreTable.TABLE_NAME,s,strings);
                break;
            default:
                throw new IllegalArgumentException("not valid"+uri);
        }
        if(d!=0)
        {
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return d;
    }

    @Override
    public int update( Uri uri,ContentValues contentValues, String s,  String[] strings) {
        int match = b.match(uri);
        SQLiteDatabase c  = a.getWritableDatabase();
        int d;
        switch(match)
        {
            case 100:
                d =c.update(StoreTable.TABLE_NAME,contentValues,s,strings);
                break;
            case 101:
                s = StoreTable.COLUMN_ITEM_ID  + "=?";
                strings  = new String[]{String.valueOf(ContentUris.parseId(uri))};
                d =c.update(StoreTable.TABLE_NAME,contentValues,s,strings);
                break;
            default:
                throw new IllegalArgumentException("not valid"+uri);
        }
        if(d!=0)
        {
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return d;
    }
}
