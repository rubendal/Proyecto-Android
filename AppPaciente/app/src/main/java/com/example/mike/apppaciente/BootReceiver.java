package com.example.mike.apppaciente;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Ruben on 23/04/2016.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        Paciente.paciente = new Paciente(sharedPreferences.getString("nombre",""),sharedPreferences.getString("telefono",""),sharedPreferences.getInt("edad",0),sharedPreferences.getInt("genero",0),
                sharedPreferences.getString("nombre_emergencia",""),sharedPreferences.getString("telefono_emergencia",""));
        Paciente.paciente.setId(sharedPreferences.getInt("id",0));
        context.startService(new Intent(context,AlarmService.class));
        context.startService(new Intent(context,SubirService.class));
    }
}
