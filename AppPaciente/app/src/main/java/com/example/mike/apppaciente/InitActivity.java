package com.example.mike.apppaciente;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class InitActivity extends AppCompatActivity implements AsyncMethod {

    private EditText idPaciente;
    private EditText telDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        idPaciente = (EditText)findViewById(R.id.id);
        telDoctor = (EditText)findViewById(R.id.tel);
    }

    public void guardar(View view){
        try {
            int id = Integer.parseInt(idPaciente.getText().toString());
            String tel = telDoctor.getText().toString();

            HashMap<String,String> params = new HashMap<>();
            params.put("id",String.valueOf(id));

            //Comprobar que ese id es valido
            new PostAsyncTask(this,params,this,"Checando",true).execute(Servidor.Direccion("/doctor/config.php"));

        }catch(Exception e){
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void Haz(Activity activity, String res) {
        if(res.equals("1")){

            int id = Integer.parseInt(idPaciente.getText().toString());
            String tel = telDoctor.getText().toString();

            SharedPreferences sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);

            Editor editor = sharedPreferences.edit();

            editor.putInt("id",id);
            editor.putString("tel",tel);

            editor.commit();

            startActivity(new Intent(this,MainActivity.class));
        }else{
            Toast.makeText(this,"Error, ID no registrado en servidor",Toast.LENGTH_SHORT).show();
        }
    }
}
