package com.example.represmash.appdoctor.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.represmash.appdoctor.Valor;

import java.util.ArrayList;

public class DB {

    DBHelper dbHelper;
    SQLiteDatabase db;

    public DB(Context context){
        dbHelper = new DBHelper(context);
    }

    public Cursor read(){
        return db.query(DBHelper.NAME,
                DBHelper.COLUMNAS,
                null,
                null,
                null,
                null,
                null);
    }

    public ArrayList<Valor> getAll(){
        Cursor cursor = read();

        ArrayList<Valor> lista = new ArrayList<>();

        while(cursor.moveToNext()){
            lista.add(new Valor(cursor.getInt(2),cursor.getInt(3),cursor.getString(1)));
        }

        return lista;
    }

    public long insert(int value,int pasos){
        ContentValues values = new ContentValues();
        values.put(DBHelper.ENTRY_VALUE,value);
        values.put(DBHelper.ENTRY_PASOS,pasos);
        return db.insert(DBHelper.NAME, null, values);
    }

    public long insertWithTimestamp(int value,int pasos,String timestamp){
        ContentValues values = new ContentValues();
        values.put(DBHelper.ENTRY_VALUE,value);
        values.put(DBHelper.ENTRY_PASOS,pasos);
        values.put(DBHelper.ENTRY_TIMESTAMP,timestamp);
        return db.insert(DBHelper.NAME, null, values);
    }

    public void open(){
        db = dbHelper.getReadableDatabase();
    }

    public void close(){
        db.close();
    }
}
