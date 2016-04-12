package com.example.represmash.appdoctor.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.represmash.appdoctor.Paciente;
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
            while(cursor.moveToNext()){
                Valor v = new Valor(cursor.getInt(3),cursor.getInt(4),cursor.getString(2));
                v.setId_paciente(cursor.getInt(1));
                lista.add(v);
            }
        }

        return lista;
    }

    public ArrayList<Valor> getDataFromPaciente(Paciente paciente){
        Cursor cursor = db.query(DBHelper.NAME,
                DBHelper.COLUMNAS,
                DBHelper.ENTRY_ID_PACIENTE + " = " + paciente.getId(),
                null,
                null,
                null,
                null);

        ArrayList<Valor> lista = new ArrayList<>();

        while(cursor.moveToNext()){
            Valor v = new Valor(cursor.getInt(3),cursor.getInt(4),cursor.getString(2));
            v.setId_paciente(cursor.getInt(1));
            lista.add(v);
        }

        return lista;
    }

    public long insert(int id_paciente, int value,int pasos){
        ContentValues values = new ContentValues();
        values.put(DBHelper.ENTRY_ID_PACIENTE,id_paciente);
        values.put(DBHelper.ENTRY_VALUE,value);
        values.put(DBHelper.ENTRY_PASOS,pasos);
        return db.insert(DBHelper.NAME, null, values);
    }

    public long insertWithTimestamp(int id_paciente, int value,int pasos,String timestamp){
        ContentValues values = new ContentValues();
        values.put(DBHelper.ENTRY_ID_PACIENTE,id_paciente);
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
