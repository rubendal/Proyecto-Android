package com.antiquis_sanus.antiquis;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.antiquis_sanus.antiquis.db.DB;
import com.antiquis_sanus.antiquis.db.DBHelper;
import com.antiquis_sanus.antiquis.db.Definition;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import com.antiquis_sanus.antiquis.background.AlarmService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private boolean realtime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        updateGraph();
        //Start Service





        new Thread(new Runnable() {
            @Override
            public void run() {
                while(realtime) {
                    updateGraph();
                    try {
                        Thread.sleep(1000 * 30);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public void updateGraph(){
        //Load data

        DB db = new DB(this.getApplicationContext());
        Cursor cursor = db.read();
        List<Integer> values = new LinkedList<>();
        List<Integer> pasos = new LinkedList<>();
        final List<String> dates = new LinkedList<>();
        while(cursor.moveToNext()){
            values.add(cursor.getInt(cursor.getColumnIndexOrThrow(Definition.Entry.ENTRY_VALUE)));
            dates.add(cursor.getString(cursor.getColumnIndexOrThrow(Definition.Entry.ENTRY_TIMESTAMP)));
            pasos.add(cursor.getInt(cursor.getColumnIndexOrThrow(Definition.Entry.ENTRY_PASOS)));
            Log.d("Pasos","Pasos " + cursor.getInt(cursor.getColumnIndexOrThrow(Definition.Entry.ENTRY_PASOS)));
        }
        LinkedList<DataPoint> points = new LinkedList<>();
        for(int i=0;i<values.size();i++){
            try{
                //java.util.Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dates.get(i));
                //date.setHours(date.getHours() - 6);
                points.add(new DataPoint(i, values.get(i)));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        LinkedList<DataPoint> pointsp = new LinkedList<>();
        for(int i=0;i<pasos.size();i++){
            try{
                //java.util.Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dates.get(i));
                //date.setHours(date.getHours() - 6);
                pointsp.add(new DataPoint(i, pasos.get(i)));
            }catch(Exception e){
                e.printStackTrace();
            }
        }


        DataPoint[] p = new DataPoint[points.size()];
        for(int i=0;i<p.length;i++){
            p[i] = points.get(i);
        }
        final LineGraphSeries<DataPoint> series = new LineGraphSeries<>(p);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GraphView graph = (GraphView) findViewById(R.id.graph);
                graph.addSeries(series);
                graph.getGridLabelRenderer().setNumHorizontalLabels(4);
            }
        });



        DataPoint[] p2 = new DataPoint[pointsp.size()];
        for(int i=0;i<p2.length;i++){
            p2[i] = pointsp.get(i);
        }
        final LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(p2);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GraphView graphp = (GraphView) findViewById(R.id.pasos);
                graphp.addSeries(series2);
                graphp.getGridLabelRenderer().setNumHorizontalLabels(4);
            }
        });


/*        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this){
            @Override
            public String formatLabel(double value, boolean isValueX){
                if(isValueX){
                    Log.d("Graph",super.formatLabel(value,isValueX));
                    return super.formatLabel(value,isValueX);
                }else{
                    return super.formatLabel(value,isValueX);
                }
            }
        });*/
       /* if(dates.size() > 2) {
            int m = dates.size() >>> 1;
            String mid = dates.get(m).split(" ")[1];
            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
            Log.d("Graph", mid);
            //staticLabelsFormatter.setHorizontalLabels(new String[]{dates.get(0).split(" ")[1],mid,dates.get(dates.size()-1).split(" ")[1]});
            staticLabelsFormatter.setHorizontalLabels(new String[]{"","",""});
            graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        }*/
    }

    public boolean serviceRunning(){
        return isMyServiceRunning(AlarmService.class);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        int i = R.id.service_button;
        MenuItem m = menu.findItem(i);
        if(m!=null) {
            if (!serviceRunning()) {
                Log.d("Service", "Service not started");
                m.setTitle("Habilitar servicio");
            } else {
                Log.d("Service", "Service is running");
                m.setTitle("Deshabilitar servicio");
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.service_button) {
            if(!serviceRunning()){
                startService(new Intent(this.getApplicationContext(), AlarmService.class));
                Toast.makeText(this,"Servicio habilitado",Toast.LENGTH_SHORT).show();
                item.setTitle("Deshabilitar servicio");
            }else{
                stopService(new Intent(this.getApplicationContext(), AlarmService.class));
                Toast.makeText(this,"Servicio deshabilitado",Toast.LENGTH_SHORT).show();
                item.setTitle("Habilitar servicio");
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
