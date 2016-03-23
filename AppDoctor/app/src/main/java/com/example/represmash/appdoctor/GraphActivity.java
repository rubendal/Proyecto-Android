package com.example.represmash.appdoctor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.represmash.appdoctor.db.DB;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        DB db = new DB(this);
        db.open();

        //Datos de prueba
        db.insert(100,7);
        db.insert(110,13);
        db.insert(105,20);
        db.insert(102,3);
        db.insert(111,30);

        db.close();

        Graph.showGraph(this,R.id.graph);

    }
}
