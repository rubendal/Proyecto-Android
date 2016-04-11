package com.example.mike.apppaciente;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Usamos SharedPreferences para obtener el paciente
        SharedPreferences sharedPreferences = getSharedPreferences("paciente", Context.MODE_PRIVATE);

        paciente = new Paciente(sharedPreferences.getString("nombre",""),sharedPreferences.getString("telefono",""),sharedPreferences.getInt("edad",0),sharedPreferences.getInt("genero",0),
                sharedPreferences.getString("nombre_emergencia",""),sharedPreferences.getString("telefono_emergencia",""));
    }

    public void activityGraficas(View view){
        startActivity(new Intent(MainActivity.this, GraficasActivity.class));
    }

    public void activityEmergencia(View view){

        if(paciente != null) {
            if(!paciente.getTelefono_emergencia().isEmpty()) {
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + paciente.getTelefono()));
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(i);
                }
            }
        }
        //startActivity(new Intent(MainActivity.this, EmergenciaActivity.class));
    }

}
