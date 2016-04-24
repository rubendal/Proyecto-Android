package com.example.mike.apppaciente;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mike.apppaciente.db.DB;

public class GraficasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas);

        Graph.showGraph(this,R.id.graph);

    }
}