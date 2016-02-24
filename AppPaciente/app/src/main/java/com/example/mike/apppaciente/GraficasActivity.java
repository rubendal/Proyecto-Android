package com.example.mike.apppaciente;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mike.apppaciente.db.DB;

public class GraficasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas);

        DB db = new DB(this);
        db.open();

        //Datos de prueba
        db.insert(100,7);
        db.insert(110,13);
        db.insert(105,20);
        db.insert(102,3);
        db.insert(111,30);

        db.close();

        Graph.showGraph(this,R.id.graph,R.id.pasos);

    }
}