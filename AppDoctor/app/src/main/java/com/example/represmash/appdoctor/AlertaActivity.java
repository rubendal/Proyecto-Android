package com.example.represmash.appdoctor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AlertaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta);
    }

    public void enviar(View v){
        Toast.makeText(this, R.string.alerta_enviada,Toast.LENGTH_SHORT).show();
        finish();
    }
}
