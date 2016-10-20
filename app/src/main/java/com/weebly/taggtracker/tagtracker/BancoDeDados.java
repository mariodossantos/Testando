package com.weebly.taggtracker.tagtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by Barbara on 17/10/2016.
 */

public final class BancoDeDados  {
     //Um bom link: https://developer.android.com/training/basics/data-storage/databases.html?hl=pt-br#DbHelper
    public static final String nomeDB = "tagtracker";
    public  BancoDeDadosHelper bdhelper;

    public BancoDeDados(){}

    private static final String TEXT_TYPE = " TEXT";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + tabelaTags.nomeTabela + " (" +
                    tabelaTags.colunaID + " INTEGER PRIMARY KEY," +
                    tabelaTags.colunaTitulo + TEXT_TYPE + " not null ); " +

            "CREATE TABLE " + tabelaChecklists.nomeTabela + " (" +
                    tabelaChecklists.colunaID + " INTEGER PRIMARY KEY," +
                    tabelaChecklists.colunaTitulo + TEXT_TYPE + " not null));" +

            "CREATE TABLE " + tabelaAssocia.nomeTabela + "( " +
                    tabelaAssocia.tagsID + " int not null, " +
                    tabelaAssocia.checklistsID + " int not null, " +
                    "FOREIGN KEY (" + tabelaAssocia.tagsID + ") REFERENCES tags( " + tabelaTags.colunaID + "), " +
                    "FOREIGN KEY (" + tabelaAssocia.checklistsID + ") REFERENCES checklists(" + tabelaChecklists.colunaID + "))";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + tabelaAssocia.nomeTabela + "; " +
            "DROP TABLE IF EXISTS " + tabelaChecklists.nomeTabela + "; " +
            "DROP TABLE IF EXISTS " + tabelaTags.nomeTabela;


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
        public static final String checklistsID = "checklistsID";
        public static final String tagsID = "tagsID";
    }

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



    /* ********************************************
     * FUNÇÕES
     ******************************************* */
    public BancoDeDadosHelper instanciaBD(Context contexto) {
        //Para acessar o banco de dados, só instanciar o helper
        bdhelper = new BancoDeDadosHelper(contexto);
        return  bdhelper;
    }

    //INSERÇÃO
    public void insereChecklist(int id, String titulo) {
        // Gets the data repository in write mode
        SQLiteDatabase db = bdhelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(tabelaChecklists.colunaID, id);
                values.put(tabelaChecklists.colunaTitulo, titulo);

        // Insert the new row, returning the primary key value of the new row
                long newRowId;
                newRowId = db.insert(
                        tabelaChecklists.nomeTabela,
                        null,
                        values);
    }

    public void insereTags(int id, String titulo) {
        // Gets the data repository in write mode
        SQLiteDatabase db = bdhelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(tabelaTags.colunaID, id);
        values.put(tabelaTags.colunaTitulo, titulo);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                tabelaTags.nomeTabela,
                null,
                values);
    }

    public void insereAssocia(int idChecklists, int idTags) {
        // Gets the data repository in write mode
        SQLiteDatabase db = bdhelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(tabelaAssocia.checklistsID, idChecklists);
        values.put(tabelaAssocia.tagsID, idTags);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                tabelaAssocia.nomeTabela,
                null,
                values);
    }

    //LEITURA
    public ArrayList<String> leChecklist(){
        ArrayList<String> resp = new ArrayList<String>();

        SQLiteDatabase db = bdhelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + tabelaChecklists.nomeTabela, null);

        if(cursor.moveToFirst()) {
            do {
                String linea = cursor.getInt(0) + " " + cursor.getString(1);
                resp.add(linea);
            } while (cursor.moveToNext());
        }
        db.close();
        return resp;
    }

    public ArrayList<String> leTags(){
        ArrayList<String> resp = new ArrayList<String>();

        SQLiteDatabase db = bdhelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + tabelaTags.nomeTabela, null);

        if(cursor.moveToFirst()) {
            do {
                String linea = cursor.getInt(0) + " " + cursor.getString(1);
                resp.add(linea);
            } while (cursor.moveToNext());
        }
        db.close();
        return resp;
    }

    public ArrayList<String> leItensListas(String idChecklist) {
        ArrayList<String> resp = new ArrayList<String>();

        SQLiteDatabase db = bdhelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(" select " + tabelaTags.colunaTitulo + " from " + tabelaTags.nomeTabela +
                " where " + tabelaTags.colunaID + " in ( select " + tabelaAssocia.tagsID +
                " from " + tabelaAssocia.nomeTabela + " as a  where " + tabelaAssocia.checklistsID + " = " +
                idChecklist, null);

        if(cursor.moveToFirst()) {
            do {
                String linea = cursor.getString(0);
                resp.add(linea);
            } while (cursor.moveToNext());
        }
        db.close();
        return resp;
    }

    //REMOÇÃO
    public void deletaAssocia(int checklistsID, int tagsID){
        SQLiteDatabase db = bdhelper.getWritableDatabase();

        if (tagsID != 0 && checklistsID != 0){
            // Define 'where' part of query.
            String selection = tabelaAssocia.checklistsID + " = ? and " +
                    tabelaAssocia.tagsID + " = ? ";
            // Specify arguments in placeholder order.
            String[] selectionArgs = {String.valueOf(checklistsID), String.valueOf(tagsID)};
            // Issue SQL statement.
            db.delete(tabelaAssocia.nomeTabela, selection, selectionArgs);
        } else if (tagsID == 0) {
            String selection2 = tabelaAssocia.checklistsID + " = ?";
            String[] selectionArgs2 = { String.valueOf(checklistsID) };
            db.delete(tabelaAssocia.nomeTabela, selection2, selectionArgs2);
        } else if (checklistsID == 0){
            String selection3 = tabelaAssocia.tagsID + " = ?";
            String[] selectionArgs3 = { String.valueOf(tagsID) };
            db.delete(tabelaAssocia.nomeTabela, selection3, selectionArgs3);
        }
    }

    public void deletaChecklists(int checklistsID){
        SQLiteDatabase db = bdhelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = tabelaChecklists.colunaID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(checklistsID) };
        // Issue SQL statement.
        deletaAssocia(checklistsID, 0);
        db.delete(tabelaChecklists.nomeTabela, selection, selectionArgs);
    }

    public void deletaTags(int tagsID){
        SQLiteDatabase db = bdhelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = tabelaTags.colunaID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(tagsID) };
        // Issue SQL statement.
        deletaAssocia(0, tagsID);
        db.delete(tabelaTags.nomeTabela, selection, selectionArgs);
    }

    //ATUALIZAÇÃO
    public void atualizaChecklists(String titulo, int checklistsID) {
        SQLiteDatabase db = bdhelper.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(tabelaChecklists.colunaTitulo, titulo);

        // Which row to update, based on the ID
        String selection = tabelaChecklists.colunaID + " = ?";
        String[] selectionArgs = {String.valueOf(checklistsID)};

        int count = db.update(
                tabelaChecklists.nomeTabela,
                values,
                selection,
                selectionArgs);
    }

    public void atualizatags(String titulo, int tagsID) {
        SQLiteDatabase db = bdhelper.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(tabelaTags.colunaTitulo, titulo);

        // Which row to update, based on the ID
        String selection = tabelaTags.colunaID + " = ?";
        String[] selectionArgs = {String.valueOf(tagsID)};

        int count = db.update(
                tabelaTags.nomeTabela,
                values,
                selection,
                selectionArgs);
    }

}




