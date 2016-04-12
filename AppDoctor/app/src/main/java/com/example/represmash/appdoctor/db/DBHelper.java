package com.example.represmash.appdoctor.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DBHelper extends SQLiteOpenHelper{

    public static final String NAME = "register";
    public static final String ENTRY_ID = "id";
    public static final String ENTRY_ID_PACIENTE = "id_paciente";
    public static final String ENTRY_TIMESTAMP = "timestamp";
    public static final String ENTRY_VALUE = "value";
    public static final String ENTRY_PASOS = "pasos";

    public static final String[] COLUMNAS = {ENTRY_ID, ENTRY_ID_PACIENTE, ENTRY_TIMESTAMP, ENTRY_VALUE, ENTRY_PASOS};

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NAME + " (" +
                    ENTRY_ID + " INTEGER PRIMARY KEY," +
                    ENTRY_ID_PACIENTE + " INTEGER," +
                    ENTRY_TIMESTAMP + " TIMESTAMP DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime'))," +
                    ENTRY_VALUE + " INT," +
                    ENTRY_PASOS + " INT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "registros.sqlite";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer){
        onUpgrade(db, oldVer, newVer);
    }
}
