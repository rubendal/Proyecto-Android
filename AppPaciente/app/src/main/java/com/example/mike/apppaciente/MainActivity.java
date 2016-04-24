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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Usamos SharedPreferences para obtener el paciente
        SharedPreferences sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);

        Paciente.paciente = new Paciente(sharedPreferences.getString("nombre",""),sharedPreferences.getString("telefono",""),sharedPreferences.getInt("edad",0),sharedPreferences.getInt("genero",0),
                sharedPreferences.getString("nombre_emergencia",""),sharedPreferences.getString("telefono_emergencia",""));
        Paciente.paciente.setId(sharedPreferences.getInt("id",0));
    }

    public void activityGraficas(View view){
        startActivity(new Intent(MainActivity.this, GraficasActivity.class));
    }

    public void activityEmergencia(View view){

        if(Paciente.paciente != null) {
            if(!Paciente.paciente.getTelefono_emergencia().isEmpty()) {
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Paciente.paciente.getTelefono()));
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(i);
                }
            }
        }
        //startActivity(new Intent(MainActivity.this, EmergenciaActivity.class));
    }

}
