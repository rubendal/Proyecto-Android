package com.example.represmash.appdoctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        paciente = (Paciente)getIntent().getSerializableExtra("paciente");

        TextView nombre = (TextView)findViewById(R.id.nombre_paciente);
        TextView contacto = (TextView)findViewById(R.id.contacto_emergencia);
        TextView telefono = (TextView)findViewById(R.id.telefono_emergencia);

        nombre.setText(paciente.getNombre());
        contacto.setText(paciente.getNombre_emergencia());
        telefono.setText(paciente.getTelefono_emergencia());
    }

    public void verGraficas(View v){
        Intent i = new Intent(this, GraphActivity.class);
        i.putExtra("paciente",paciente);
        startActivity(i);
    }

    public void mandarAlerta(View v){
        Intent i = new Intent(this, AlertaActivity.class);
        i.putExtra("paciente",paciente);
        startActivity(i);
    }

    public void llamar(View v){

        //Llamar al telefono del paciente

        Toast.makeText(this, String.format("%s %s","Hacer llamada a: ",paciente.getTelefono()),Toast.LENGTH_SHORT).show();
    }
}
