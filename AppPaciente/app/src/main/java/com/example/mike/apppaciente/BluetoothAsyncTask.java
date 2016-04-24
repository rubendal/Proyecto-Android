package com.example.mike.apppaciente;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.mike.apppaciente.db.DB;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Ruben on 23/04/2016.
 */
public class BluetoothAsyncTask extends AsyncTask<String, String, String> {

    private Context context;
    private BluetoothAdapter adapter;
    private BluetoothDevice device;
    private BluetoothSocket socket;


    public BluetoothAsyncTask(Context context, BluetoothAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        try {
            device = adapter.getRemoteDevice("00:06:66:4E:47:73");
            socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
            Thread.sleep(3000);
            socket.connect();
        }catch(Exception e){
            super.cancel(true);
        }
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        if(socket!=null){
            if(socket.isConnected()){
                try {
                    String res = send(1);
                    String pasos = send(2);
                    //Toast.makeText(context,"Valor recibido: " + res,Toast.LENGTH_SHORT).show();
                    //Toast.makeText(context,"Pasos recibidos: " + pasos,Toast.LENGTH_SHORT).show();
                    process(res, pasos);
                    Log.d("Bluetooth", "Cerrado");
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    private void cancelarAsyncTask(){
        //Toast.makeText(context, "Error",Toast.LENGTH_SHORT).show();
        if(socket!=null){
            if(socket.isConnected()){
                try {
                    socket.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private String read(){
        try {
            //Thread.sleep(5000);
            InputStream input = socket.getInputStream();
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

    private String send(Integer id){
        try{
            Log.d("Bluetooth", "Conectado");
            OutputStream out = socket.getOutputStream();
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
}
