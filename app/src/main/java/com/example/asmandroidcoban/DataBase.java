package com.example.asmandroidcoban;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {
    public static final String NAME_DATABASE = "listLop";
    public static final int VERSION = 1;

    public DataBase(@Nullable Context context) {
        super(context, NAME_DATABASE, null, VERSION);
    }

    public void querSQL(String code_SQL){
        SQLiteDatabase dataBase = getWritableDatabase();
        dataBase.execSQL(code_SQL);
    }

    public Cursor getData(String code_SQL){
        SQLiteDatabase database = getReadableDatabase();
        return  database.rawQuery(code_SQL,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
