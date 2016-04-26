package com.example.mike.apppaciente;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;

import com.example.mike.apppaciente.db.DBServer;

/**
 * Created by Ruben on 24/04/2016.
 */
public class AlertaReceiver extends BroadcastReceiver {
    private long UPDATE = 1000 * 60 * 5;

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock w = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CHECK");
        w.acquire();

        SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        Paciente.paciente = new Paciente(sharedPreferences.getString("nombre",""),sharedPreferences.getString("telefono",""),sharedPreferences.getInt("edad",0),sharedPreferences.getInt("genero",0),
                sharedPreferences.getString("nombre_emergencia",""),sharedPreferences.getString("telefono_emergencia",""));
        Paciente.paciente.setId(sharedPreferences.getInt("id",0));
        AlertaAsyncTask asyncTask = new AlertaAsyncTask(context,Paciente.paciente.getId(),"Cargando",false);
        asyncTask.execute(Servidor.Direccion("/doctor/recalerta.php"));

        w.release();
    }

    public void SetAlarm(Context context)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertaReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), UPDATE , pi);
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlertaReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
