package com.example.represmash.appdoctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void iniciarSesion(View v){

        EditText username = (EditText) findViewById(R.id.usuario);
        EditText pass = (EditText) findViewById(R.id.password);
        Sesion.iniciarSesion( this , username.getText().toString(), pass.getText().toString());

    }
}
