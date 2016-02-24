package com.example.represmash.appdoctor;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaPacientesActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacientes);

        //Contenido estatico
        ArrayList<Paciente> pacientes = new ArrayList<>();
        pacientes.add(new Paciente("Pablo","5511223344",78,0,"Rodrigo","5512345678"));
        pacientes.add(new Paciente("Laura","5511111111",82,0,"Jose","5512572301"));
        pacientes.add(new Paciente("Erick","5511222222",73,0,"Angel","5538940923"));


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
