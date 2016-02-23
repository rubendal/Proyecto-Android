package com.example.represmash.appdoctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }

    public void registrar(View v){
        Intent i = new Intent(this, RegistroActivity.class);
        startActivity(i);
    }

    public void verPacientes(View v){
        Intent i = new Intent(this, ListaPacientesActivity.class);
        startActivity(i);
    }
}
