package com.example.storeapp.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

public class StoreContract {

    public StoreContract()
    {}
    public static final String  CONTENT_AUTHORITY= "com.example.storeapp";
    public static final Uri uri = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_STORE="store";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(uri,PATH_STORE);
    public class StoreTable implements BaseColumns
    {
        public static final String  TABLE_NAME="store";


        public static final String COLUMN_ITEM_NAME="name";
        public static final String COLUMN_ITEM_QUANTITY="quantity";
        public static final String COLUMN_ITEM_COSTPRICE="cp";
        public static final String COLUMN_ITEM_SELLPRICE="sp";
        public static final String COLUMN_ITEM_ID=BaseColumns._ID;

    }
}
