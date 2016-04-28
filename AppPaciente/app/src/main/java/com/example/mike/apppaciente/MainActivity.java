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
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Usamos SharedPreferences para obtener el paciente, los datos excepto id estan vacios
        SharedPreferences sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);

        Paciente.paciente = new Paciente(sharedPreferences.getString("nombre",""),sharedPreferences.getString("tel",""),sharedPreferences.getInt("edad",0),sharedPreferences.getInt("genero",0),
                sharedPreferences.getString("nombre_emergencia",""),sharedPreferences.getString("telefono_emergencia",""));
        Paciente.paciente.setId(sharedPreferences.getInt("id", 0));

        TextView bienvenido = (TextView) findViewById(R.id.bienvenido);
        bienvenido.setText("Bienvenido, " + Paciente.paciente.getNombre());

        Graph.showGraph(this, R.id.graph2);

    }


    public void activityEmergencia(View view){

        if(Paciente.paciente != null) {
            if(!Paciente.paciente.getTelefono().isEmpty()) {
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Paciente.paciente.getTelefono()));
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(i);
                } else {
                    System.out.println("No hay permiso");
                }
            } else { System.out.println("No hay telefono de paciente");}
        } else { System.out.println("Es null"); }
        //startActivity(new Intent(MainActivity.this, EmergenciaActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this,SubirService.class));
        startService(new Intent(this,AlertaService.class));
        //startService(new Intent(this,AlarmService.class));

    }
}
