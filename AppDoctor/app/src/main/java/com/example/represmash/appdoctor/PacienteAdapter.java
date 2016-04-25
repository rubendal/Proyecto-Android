package com.example.represmash.appdoctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ruben on 23/02/2016.
 */
public class PacienteAdapter extends ArrayAdapter<Paciente> {

    public PacienteAdapter(Context context, List<Paciente> lista){
        super(context,0,lista);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Paciente paciente = getItem(position);

        if(convertView == null){
            convertView =  LayoutInflater.from(getContext()).inflate(R.layout.renglon_paciente, parent, false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.nombre_paciente);
        TextView edad = (TextView)convertView.findViewById(R.id.edad_paciente);
        TextView telefono = (TextView)convertView.findViewById(R.id.telefono_paciente);
        TextView id = (TextView)convertView.findViewById(R.id.id_pac);
        ImageView img = (ImageView)convertView.findViewById(R.id.img_paciente);

        name.setText(paciente.getNombre());
        edad.setText(String.format("%s: %d",convertView.getResources().getString(R.string.edad) ,paciente.getEdad()));
        id.setText(String.format("ID: %d",paciente.getId()));
        telefono.setText(paciente.getTelefono());

        return convertView;
    }
}
