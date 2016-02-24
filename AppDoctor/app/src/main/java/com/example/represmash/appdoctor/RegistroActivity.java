package com.example.represmash.appdoctor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void registrarPaciente(View v){
        //Agregar contenido a una base de datos
        Toast.makeText(this, R.string.paciente_registrado, Toast.LENGTH_SHORT).show();
        finish();
    }
}
