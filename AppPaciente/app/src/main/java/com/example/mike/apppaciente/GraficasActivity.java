package com.example.mike.apppaciente;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mike.apppaciente.db.*;

import com.jjoe64.graphview.GraphView;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.LinkedList;
import java.util.List;



public class GraficasActivity extends AppCompatActivity {

    private boolean realtime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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

    public void updateGraph() {
        //Load data

        DB db = new DB(this.getApplicationContext());
        Cursor cursor = db.read();
        List<Integer> values = new LinkedList<>();
        List<Integer> pasos = new LinkedList<>();
        final List<String> dates = new LinkedList<>();
        while (cursor.moveToNext()) {
            values.add(cursor.getInt(cursor.getColumnIndexOrThrow(Definition.Entry.ENTRY_VALUE)));
            dates.add(cursor.getString(cursor.getColumnIndexOrThrow(Definition.Entry.ENTRY_TIMESTAMP)));
            pasos.add(cursor.getInt(cursor.getColumnIndexOrThrow(Definition.Entry.ENTRY_PASOS)));
            Log.d("Pasos", "Pasos " + cursor.getInt(cursor.getColumnIndexOrThrow(Definition.Entry.ENTRY_PASOS)));
        }
        LinkedList<DataPoint> points = new LinkedList<>();
        for (int i = 0; i < values.size(); i++) {
            try {
                //java.util.Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dates.get(i));
                //date.setHours(date.getHours() - 6);
                points.add(new DataPoint(i, values.get(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LinkedList<DataPoint> pointsp = new LinkedList<>();
        for (int i = 0; i < pasos.size(); i++) {
            try {
                //java.util.Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dates.get(i));
                //date.setHours(date.getHours() - 6);
                pointsp.add(new DataPoint(i, pasos.get(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        DataPoint[] p = new DataPoint[points.size()];
        for (int i = 0; i < p.length; i++) {
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
        for (int i = 0; i < p2.length; i++) {
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

}
