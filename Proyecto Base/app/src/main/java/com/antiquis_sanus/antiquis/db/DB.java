package com.antiquis_sanus.antiquis.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DB {

    DBHelper dbHelper;
    SQLiteDatabase db;

    public DB(Context context){
        dbHelper = new DBHelper(context);
    }

    public Cursor read(){
        db = dbHelper.getReadableDatabase();

        return db.query(Definition.Entry.NAME,
                new String[]{Definition.Entry.ENTRY_ID,Definition.Entry.ENTRY_TIMESTAMP,Definition.Entry.ENTRY_VALUE, Definition.Entry.ENTRY_PASOS},
                null,
                null,
                null,
                null,
                null);
    }

    public long insert(int value,int pasos){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Definition.Entry.ENTRY_VALUE,value);
        values.put(Definition.Entry.ENTRY_PASOS,pasos);
        return db.insert(Definition.Entry.NAME,null,values);
    }
}
