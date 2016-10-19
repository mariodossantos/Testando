package com.weebly.taggtracker.tagtracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_nombre;
    Button bt_guardar, bt_mostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et_nombre = (EditText) findViewById(R.id.et_nombre);


        bt_guardar = (Button) findViewById(R.id.bt_guardar);
        bt_mostrar = (Button) findViewById(R.id.bt_mostrar);

        bt_guardar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                guardar(et_nombre.getText().toString());
            }
        });

        bt_mostrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Listado.class));
            }
        });


    }



    private void guardar(String Nombre){
        BaseHelper helper = new BaseHelper(this, "Demo", null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        try{
            ContentValues c = new ContentValues();
            c.put("Nombre", Nombre);

            db.insert("PERSONAS",null, c);
            db.close();
            Toast.makeText(this,"CheckList Inserida com sucesso ",Toast.LENGTH_SHORT).show();



        }catch (Exception e){
            Toast.makeText(this,"ERROR" +e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }
}
