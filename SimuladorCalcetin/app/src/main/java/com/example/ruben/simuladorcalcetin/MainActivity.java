package com.example.ruben.simuladorcalcetin;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private final Handler handler = new Handler();
    private String mac = "";
    private int diametro = 130;
    private int pasos = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        handler.post(b);
        //handler.postDelayed(b,1000*60);
    }

    public void enviar(View view){
        handler.removeCallbacks(b);
        EditText d = (EditText)findViewById(R.id.diametro);
        EditText p = (EditText)findViewById(R.id.pasos);
        diametro = Integer.parseInt(d.getText().toString());
        pasos = Integer.parseInt(p.getText().toString());
        handler.removeCallbacks(b);
        handler.post(b);
        //handler.postDelayed(b,1000*60);

    }

    private Runnable b = new Runnable() {

        private BluetoothServerSocket server;
        private BluetoothSocket socket;

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
        public void run() {
            if(bluetoothAdapter != null) {
                //There is bluetooth
                if (bluetoothAdapter.isEnabled()) {
                    //Toast.makeText(MainActivity.this, "Bluetooth esta activado", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(MainActivity.this, "Bluetooth no esta activado", Toast.LENGTH_SHORT).show();
                    bluetoothAdapter.enable();
                }
                //BluetoothDevice device = bluetoothAdapter.getRemoteDevice(mac);
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
            }
            //handler.postDelayed(b,1000*60);
        }
    };
}
