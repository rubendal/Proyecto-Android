package com.example.represmash.appdoctor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class AlertaActivity extends AppCompatActivity implements AsyncMethodActivity {

    private Paciente paciente;
    private EditText alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta);

        paciente = (Paciente)getIntent().getSerializableExtra("paciente");
        alerta = (EditText)findViewById(R.id.alerta);
    }

    public void enviar(View v){

        HashMap<String,String> params = new HashMap<>();
        params.put("paciente", paciente.getId() + "");
        params.put("alerta", alerta.getText().toString());
        new PostAsyncTask(this, params, "Enviando alerta",this).execute(Servidor.Direccion("/doctor/alerta.php"));

    }

    @Override
    public void Haz(String res) {
        try{
            int i = Integer.parseInt(res);
            if (i != -1) {
                Toast.makeText(this, R.string.alerta_enviada,Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        }
    }
}
