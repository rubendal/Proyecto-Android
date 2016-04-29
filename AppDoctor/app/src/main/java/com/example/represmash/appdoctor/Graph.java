package com.example.represmash.appdoctor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import com.example.represmash.appdoctor.db.*;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Ruben on 23/02/2016.
 */
public class Graph {

    public static void showGraph(final Activity activity, @IdRes final int graph, Paciente paciente){
        DB db = new DB(activity.getApplicationContext());
        db.open();
        ArrayList<Valor> valores = db.getDataFromPaciente(paciente);

        LineChart lineChart = (LineChart)activity.findViewById(graph);

        //lineChart.clear();

        lineChart.setDescription("");

        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);

        ArrayList<String> x = new ArrayList<>();

        ArrayList<Entry> y1 = new ArrayList<>();
        ArrayList<Entry> y2 = new ArrayList<>();
        for (int i = 0; i < valores.size(); i++) {
            //x.add(Integer.toString(i));
            x.add(valores.get(i).getTimestamp());
            y1.add(new Entry(valores.get(i).getValor(),i));
            y2.add(new Entry(valores.get(i).getPasos(), i));

        }

        LineDataSet set1 = new LineDataSet(y1, activity.getString(R.string.diametro));
        LineDataSet set2 = new LineDataSet(y2, activity.getString(R.string.pasos));

        set1.setColor(Color.RED);
        set1.setCircleColor(Color.RED);
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set1.setFillAlpha(65);
        set1.setDrawCircleHole(false);

        set2.setColor(Color.BLUE);
        set2.setCircleColor(Color.BLUE);
        set2.setLineWidth(2f);
        set2.setCircleRadius(3f);
        set2.setFillAlpha(65);
        set2.setDrawCircleHole(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        LineData data = new LineData(x, dataSets);
        lineChart.setData(data);

        lineChart.notifyDataSetChanged();
        lineChart.invalidate();

        db.close();
    }
}
