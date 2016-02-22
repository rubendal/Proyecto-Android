package com.antiquis_sanus.antiquis.background;

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

import com.antiquis_sanus.antiquis.MainActivity;
import com.antiquis_sanus.antiquis.R;
import com.antiquis_sanus.antiquis.db.DB;

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
        if(bluetoothAdapter != null){
            //There is bluetooth
            if(bluetoothAdapter.isEnabled()){
               //Toast.makeText(MainActivity.this, "Bluetooth esta activado", Toast.LENGTH_SHORT).show();
            }else{
                //Toast.makeText(MainActivity.this, "Bluetooth no esta activado", Toast.LENGTH_SHORT).show();
                bluetoothAdapter.enable();
            }

            new Thread(new Runnable() {
                BluetoothSocket tmp = null;

                private void connect() throws Exception{
                    BluetoothDevice device = bluetoothAdapter.getRemoteDevice("00:06:66:4E:47:73");
                    Log.d("Bluetooth", "Conectando");
                    tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
                    Thread.sleep(3000);
                    //1 prender, 2 apagar
                    tmp.connect();
                    Log.d("Bluetooth", "Sleep");
                    Log.d("Bluetooth", tmp.getRemoteDevice().toString());
                }

                private String read(){
                    try {
                        //Thread.sleep(5000);
                        InputStream input = tmp.getInputStream();
                        int j = 0;
                        byte[] buffer = new byte[1024];

                        int length = input.read(buffer);
                        Log.d("Bluetooth", "Recibi " + length + " bytes");
                        StringBuilder sb = new StringBuilder();
                        for (byte b : buffer) {
                            if (b != 0 && b != 13) {
                                sb.append((char) b);
                            }
                        }
                        String n = sb.toString(); //new String(buffer,0,length-2);
                        Log.d("Bluetooth", "Cadena " + n);

                        return n;
                    }catch(Exception e){
                        e.printStackTrace();
                        return "";
                    }
                }

                private String send(Integer id){
                    try{
                        Log.d("Bluetooth", "Conectado");
                        OutputStream out = tmp.getOutputStream();
                        out.write(id.toString().getBytes());
                        Log.d("Bluetooth", id +" enviado");
                        Thread.sleep(1000);
                        String n = "";
                        do {
                            n += read();
                        }while(!n.contains("\n"));

                        return n;

                    }catch(Exception e){
                        e.printStackTrace();
                        return "";
                    }
                }

                private void process(String res, String pasos){
                    //Es un numero procesado correctamente
                    int val = Integer.parseInt(res.replace("\n","").replace("a",""));
                    int pas = Integer.parseInt(pasos.replace("\n", "").replace("a", ""));
                    DB db = new DB(context);
                    if(db.insert(val,pas)!=-1){
                        Log.d("DB","Se inserto el valor " + val + " , " + pas);
                    }else {
                        Log.d("DB", "No se inserto el valor " + val + " , " + pas);
                    }
                }

                @Override
                public void run() {
                    try {
                        connect();
                        String res = send(1);
                        String pasos = send(2);
                        //createNotification(context,"Valor recibido: " + res);
                        //createNotification(context,"Pasos recibidos: " + pasos);
                        //Toast.makeText(context,"Valor recibido: " + res,Toast.LENGTH_SHORT).show();
                        //Toast.makeText(context,"Pasos recibidos: " + pasos,Toast.LENGTH_SHORT).show();
                        process(res, pasos);
                        Log.d("Bluetooth", "Cerrado");
                        tmp.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        w.release();
    }
/*
    public void createNotification(Context context, String msg){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }*/

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
