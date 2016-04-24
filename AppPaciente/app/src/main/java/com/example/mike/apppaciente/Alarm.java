package com.example.mike.apppaciente;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.example.mike.apppaciente.db.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


public class Alarm extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock w = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CHECK");
        w.acquire();

        //Toast.makeText(context, "Prueba de alarma de servicio", Toast.LENGTH_LONG).show();

        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter != null) {
            //There is bluetooth
            if (bluetoothAdapter.isEnabled()) {
                //Toast.makeText(MainActivity.this, "Bluetooth esta activado", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(MainActivity.this, "Bluetooth no esta activado", Toast.LENGTH_SHORT).show();
                bluetoothAdapter.enable();
            }

            BluetoothAsyncTask asyncTask = new BluetoothAsyncTask(context,bluetoothAdapter);
            asyncTask.execute();
        }
        w.release();
    }

    public void SetAlarm(Context context)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        Log.d("Alarm", "Asigna repeat");
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60, pi); // Cada 10 minutos activa la alarma
        Log.d("Alarm", "Lo asigno?");
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
