package com.example.represmash.appdoctor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void registrarPaciente(View v){
        //Agregar contenido a una base de datos

        EditText nombre = (EditText) findViewById(R.id.nombre);
        EditText telefono = (EditText) findViewById(R.id.telefono);
        EditText edad = (EditText) findViewById(R.id.edad);
        EditText contacto = (EditText) findViewById(R.id.contacto);
        EditText telefonoContacto = (EditText) findViewById(R.id.telefono_contacto);
        RadioGroup genero = (RadioGroup)findViewById(R.id.genero);

        int g = 0;
        switch(genero.getCheckedRadioButtonId()){
            case R.id.M:
                g=0;
                break;

            case R.id.F:
                g=1;
                break;
        }
        Paciente paciente = new Paciente(nombre.getText().toString(), telefono.getText().toString(),
                Integer.parseInt(edad.getText().toString()),g,contacto.getText().toString(), telefonoContacto.getText().toString());

        try{
            String res = new PostAsyncTask(this, paciente.generatePOSTParams(Sesion.ID), "Registrando paciente").execute(Servidor.Direccion() + "/doctor/registro.php").get();
            int i = Integer.parseInt(res);
            if(i!=-1){
                Toast.makeText(this, R.string.paciente_registrado, Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }


    }
}
