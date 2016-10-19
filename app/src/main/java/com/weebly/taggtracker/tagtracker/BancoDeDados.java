package com.weebly.taggtracker.tagtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Barbara on 17/10/2016.
 */

public final class BancoDeDados  {
     //Um bom link: https://developer.android.com/training/basics/data-storage/databases.html?hl=pt-br#DbHelper
    public static final String nomeDB = "tagtracker";

    public BancoDeDados(){}

    public static abstract class tabelaChecklists implements BaseColumns {
        public static final String nomeTabela = "checklists";
        public static final String colunaID = "ID";
        public static final String colunaTitulo = "titulo";
    }

    public static abstract class tabelaTags implements BaseColumns {
        public static final String nomeTabela = "tags";
        public static final String colunaID = "ID";
        public static final String colunaTitulo = "titulo";
    }


    public static abstract class tabelaAssocia implements BaseColumns {
        public static final String nomeTabela = "associa";
        public static final String checklistsID = "tags";
        public static final String tagsID = "ID";
    }


    private static final String TEXT_TYPE = " TEXT";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + tabelaTags.nomeTabela + " (" +
                    tabelaTags.colunaID + " INTEGER PRIMARY KEY," +
                    tabelaTags.colunaTitulo + TEXT_TYPE + " ) " +
            "CREATE TABLE " + tabelaChecklists.nomeTabela + " (" +
                    tabelaChecklists.colunaID + " INTEGER PRIMARY KEY," +
                    tabelaChecklists.colunaTitulo + TEXT_TYPE + " ))" +
            "CREATE TABLE " + tabelaAssocia.nomeTabela + " (" +
                    tabelaAssocia.checklistsID + " FOREIGN KEY REFERENCES " + tabelaChecklists.nomeTabela + "(" + tabelaChecklists.colunaID + "), " +
                    tabelaAssocia.tagsID + " FOREIGN KEY REFERENCES " + tabelaTags.nomeTabela + "(" + tabelaTags.colunaID + "))";

    private static final String SQL_DELETE_CHECKLISTS =
            "DROP TABLE IF EXISTS " + tabelaChecklists.nomeTabela;

    private static final String SQL_DELETE_TAGS =
            "DROP TABLE IF EXISTS " + tabelaTags.nomeTabela;

    private static final String SQL_DELETE_ASSOCIA =
            "DROP TABLE IF EXISTS " + tabelaAssocia.nomeTabela;



    public class BancoDeDadosHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = nomeDB + ".db";

        public BancoDeDadosHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}



}
