package com.example.ruben.simuladorcalcetin;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Ruben on 29/04/2016.
 */
public class BluetoothAsyncTask extends AsyncTask<String,String,String> {

    private int diametro;
    private int pasos;
    private BluetoothServerSocket server;
    private BluetoothSocket socket;
    private BluetoothAdapter bluetoothAdapter;
    private String mac;

    public BluetoothAsyncTask(BluetoothAdapter bluetoothAdapter, int diametro, int pasos, String mac){
        this.bluetoothAdapter = bluetoothAdapter;
        this.diametro = diametro;
        this.pasos = pasos;
        this.mac = mac;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(bluetoothAdapter != null) {
            //There is bluetooth
            if (bluetoothAdapter.isEnabled()) {
                //Toast.makeText(MainActivity.this, "Bluetooth esta activado", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(MainActivity.this, "Bluetooth no esta activado", Toast.LENGTH_SHORT).show();
                bluetoothAdapter.enable();
            }
            //BluetoothDevice device = bluetoothAdapter.getRemoteDevice(mac);

        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            server = bluetoothAdapter.listenUsingRfcommWithServiceRecord("connection", UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
            socket = server.accept();
            Log.e("asdfg","conectado");
            String r = read();
            process(r);
            r = read();
            process(r);

            socket.close();
            server.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
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

    private void send(String s){
        try {
            OutputStream out = socket.getOutputStream();
            out.write(s.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void process(String s){
        switch(s){
            case "1":
                send(diametro + "\n");
                break;
            case "2":
                send(pasos + "\n");
                break;
        }
    }

    @Override
    protected void onCancelled() {
        try{
            if(socket!=null){
                socket.close();
            }
        }catch(Exception e){

        }
        try{
            if(server!=null){
                server.close();
            }
        }catch(Exception e){

        }
        super.onCancelled();
    }

    public void setDiametro(int diametro){
        this.diametro = diametro;
    }

    public void setPasos(int pasos){
        this.pasos = pasos;
    }
}
