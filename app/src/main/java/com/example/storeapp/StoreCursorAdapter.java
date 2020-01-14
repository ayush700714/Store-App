package com.example.storeapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.storeapp.data.StoreContract;

public class StoreCursorAdapter extends CursorAdapter {


    public StoreCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView a = (TextView)view.findViewById(R.id.name2);
        TextView b = (TextView)view.findViewById(R.id.qnt2);
        String name = cursor.getString(cursor.getColumnIndex(StoreContract.StoreTable.COLUMN_ITEM_NAME));
        int qnt  = cursor.getInt(cursor.getColumnIndex(StoreContract.StoreTable.COLUMN_ITEM_QUANTITY));
        a.setText(name);
        b.setText(""+qnt);
    }
}
