package com.weebly.taggtracker.tagtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {

    ListView listView;
    ArrayList<String> listado;

  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        listView = (ListView) findViewById(R.id.listView);

        CargarListado();
        
    }
    

    private void CargarListado(){
        listado = ListaPersonas();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listado);
        listView.setAdapter(adapter);
       
    }

    private ArrayList<String> ListaPersonas(){
        ArrayList<String> datos = new ArrayList<String>();
        BaseHelper helper = new BaseHelper(this, "Demo", null,1);

        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT ID, NOMBRE from PERSONAS";
        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst()) {

            do {
                String linea = c.getInt(0) + " " + c.getString(1);
                datos.add(linea);


            } while (c.moveToNext());
        }
        db.close();
        return datos;

    }

}
