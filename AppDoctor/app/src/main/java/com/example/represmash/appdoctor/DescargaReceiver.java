package com.example.represmash.appdoctor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;

import com.example.represmash.appdoctor.db.DBServer;

import java.util.Set;

/**
 * Created by Ruben on 24/04/2016.
 */
public class DescargaReceiver extends BroadcastReceiver {
    private long UPDATE = 1000 * 60 * 5;

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock w = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CHECK");
        w.acquire();

        SharedPreferences sharedPreferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        Sesion.username = sharedPreferences.getString("username","");
        Sesion.ID = sharedPreferences.getInt("id",0);

        Set<String> set = sharedPreferences.getStringSet("pacientes",null);

        if(set!=null) {
            for(String s : set) {
                try{
                    int id = Integer.parseInt(s);
                    Paciente paciente = new Paciente(id);
                    DBServer.download(context,paciente);
                }catch(Exception e){

                }

            }
        }

        w.release();
    }

    public void SetAlarm(Context context)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Service.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), UPDATE , pi);
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Service.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
