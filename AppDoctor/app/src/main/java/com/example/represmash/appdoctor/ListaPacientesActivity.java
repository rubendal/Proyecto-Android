package com.example.represmash.appdoctor;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListaPacientesActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacientes);

        HashMap<String, String> params = new HashMap<>();
        params.put("id_doctor", Integer.toString(Sesion.ID));
        ArrayList<Paciente> pacientes = new ArrayList<>();
        try {
            String json = new PostAsyncTask(this, params, "Obteniendo lista").execute(Servidor.Direccion("/doctor/pacientes.php")).get();

            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject data = jsonArray.getJSONObject(i);
                    int id = data.getInt("id");
                    String nombre = data.getString("nombre");
                    String telefono = data.getString("telefono");
                    int edad = data.getInt("edad");
                    int genero = data.getInt("genero");
                    String contacto_emergencia = data.getString("contacto_emergencia");
                    String telefono_emergencia = data.getString("telefono_emergencia");

                    Paciente paciente = new Paciente(nombre, telefono, edad, genero, contacto_emergencia, telefono_emergencia);
                    paciente.setId(id);
                    pacientes.add(paciente);
                }
            } catch (Exception e) {
                finish();
            }
        }catch(Exception e){
            finish();
        }
        setListAdapter(new PacienteAdapter(this,pacientes));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Paciente paciente = (Paciente)getListAdapter().getItem(position);

        Intent i = new Intent(this, MenuActivity.class);
        i.putExtra("paciente",paciente);
        startActivity(i);
    }
}
