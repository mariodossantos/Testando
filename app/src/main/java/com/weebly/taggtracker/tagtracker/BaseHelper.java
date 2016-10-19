package com.weebly.taggtracker.tagtracker;

/**
 * Created by marioamigos on 17/10/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marioamigos on 15/10/2016.
 */

public class BaseHelper extends SQLiteOpenHelper {


    String tabla = "CREATE TABLE LISTA(ID INTEGER PRIMARY KEY, NOMBRE TEXT)";
    public BaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table personas");
        db.execSQL(tabla);

    }
}