package com.example.represmash.appdoctor;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import com.example.represmash.appdoctor.db.*;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Ruben on 23/02/2016.
 */
public class Graph {

    public static void showGraph(final Activity activity, @IdRes final int graphViewValores, @IdRes final int graphViewPasos){
        DB db = new DB(activity.getApplicationContext());
        db.open();
        ArrayList<Valor> valores = db.getAll();

        LinkedList<DataPoint> points = new LinkedList<>();
        for(int i=0;i<valores.size();i++){
            try{
                //java.util.Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dates.get(i));
                //date.setHours(date.getHours() - 6);
                points.add(new DataPoint(i, valores.get(i).getValor()));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        LinkedList<DataPoint> pointsp = new LinkedList<>();
        for(int i=0;i<valores.size();i++){
            try{
                //java.util.Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dates.get(i));
                //date.setHours(date.getHours() - 6);
                pointsp.add(new DataPoint(i, valores.get(i).getPasos()));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        DataPoint[] p = new DataPoint[points.size()];
        for(int i=0;i<p.length;i++){
            p[i] = points.get(i);
        }
        final LineGraphSeries<DataPoint> series = new LineGraphSeries<>(p);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GraphView graph = (GraphView) activity.findViewById(graphViewValores);
                graph.addSeries(series);
                graph.getGridLabelRenderer().setNumHorizontalLabels(4);
            }
        });



        DataPoint[] p2 = new DataPoint[pointsp.size()];
        for(int i=0;i<p2.length;i++){
            p2[i] = pointsp.get(i);
        }
        final LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(p2);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GraphView graphp = (GraphView) activity.findViewById(graphViewPasos);
                graphp.addSeries(series2);
                graphp.getGridLabelRenderer().setNumHorizontalLabels(4);
            }
        });

        db.close();
    }
}
