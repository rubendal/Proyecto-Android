package com.antiquis_sanus.antiquis.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DBHelper extends SQLiteOpenHelper{

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Definition.Entry.NAME + " (" +
                    Definition.Entry.ENTRY_ID + " INTEGER PRIMARY KEY," +
                    Definition.Entry.ENTRY_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    Definition.Entry.ENTRY_VALUE + " INT," +
                    Definition.Entry.ENTRY_PASOS + " INT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Definition.Entry.NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "registers.sqlite";

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
