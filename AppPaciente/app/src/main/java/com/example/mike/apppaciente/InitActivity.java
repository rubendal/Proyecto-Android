package com.example.mike.apppaciente;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InitActivity extends AppCompatActivity implements AsyncMethod {

    private EditText idPaciente;
    private EditText telDoctor;
    private EditText macAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        idPaciente = (EditText)findViewById(R.id.id);
        telDoctor = (EditText)findViewById(R.id.tel);
        macAddress = (EditText)findViewById(R.id.mac);
    }

    public void guardar(View view){
        try {
            final int id = Integer.parseInt(idPaciente.getText().toString());
            String tel = telDoctor.getText().toString();
            String mac = macAddress.getText().toString();

            HashMap<String,String> params = new HashMap<>();

            RequestQueue queue = Volley.newRequestQueue(this);
            final InitActivity ref = this;
            StringRequest postRequest = new StringRequest(Request.Method.POST, Servidor.Direccion("/doctor/config.php"),
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response);
                            if(response.equals("1")){

                                int id = Integer.parseInt(idPaciente.getText().toString());
                                String tel = telDoctor.getText().toString();
                                String mac = macAddress.getText().toString();

                                SharedPreferences sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);

                                Editor editor = sharedPreferences.edit();

                                editor.putInt("id",id);
                                editor.putString("tel",tel);
                                editor.putString("mac",mac);

                                editor.commit();

                                startActivity(new Intent(ref,MainActivity.class));
                            }else{
                                Toast.makeText(ref, R.string.no_id,Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response", error.getMessage());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("name", "Alif");
                    params.put("id",String.valueOf(id));

                    return params;
                }
            };
            queue.add(postRequest);

            //Comprobar que ese id es valido
            //new PostAsyncTask(this,params,this,getString(R.string.checando),true).execute(Servidor.Direccion("/doctor/config.php"));

        }catch(Exception e){
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void Haz(Activity activity, String res) {
        if(res.equals("1")){

            int id = Integer.parseInt(idPaciente.getText().toString());
            String tel = telDoctor.getText().toString();
            String mac = macAddress.getText().toString();

            SharedPreferences sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);

            Editor editor = sharedPreferences.edit();

            editor.putInt("id",id);
            editor.putString("tel",tel);
            editor.putString("mac",mac);

            editor.commit();

            startActivity(new Intent(this,MainActivity.class));
        }else{
            Toast.makeText(this, R.string.no_id,Toast.LENGTH_SHORT).show();
        }
    }
}
