package com.example.represmash.appdoctor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ruben on 04/03/2016.
 */
public class HttpHandler {

    private static ProgressDialog dialog;

    private static void initializeDialog(Activity activity){
        dialog = new ProgressDialog(activity);
    }

    private static void startDialog(String title){
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.show();
    }

    private static String generarPOST(HashMap<String,String> params){
        StringBuilder out = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                out.append("&");

            try {
                out.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                out.append("=");
                out.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }catch(Exception e){

            }
        }
        return out.toString();
    }

    public static String sendPostRequest(Activity activity, final String url_s, final HashMap<String, String> params, String dialogTitle){
        initializeDialog(activity);
        startDialog(dialogTitle);
        final StringBuilder builder = new StringBuilder();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try
                {
                    URL url = new URL(url_s);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Accept-Charset", "utf-8");
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    OutputStream os = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(generarPOST(params));

                    writer.flush();
                    writer.close();
                    os.close();

                    connection.connect();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));

                    String line = "";
                    while ((line = reader.readLine()) != null)
                    {
                        builder.append(line);
                    }
                }catch (Exception ex)
                {
                    Log.e("Error", "" + ex);
                }
                finally
                {
                    connection.disconnect();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        }catch(Exception e){

        }

        dialog.dismiss();
        return builder.toString();
    }

    public static String sendRequest(Activity activity,final String url_s, String dialogTitle){
        initializeDialog(activity);
        startDialog(dialogTitle);
        final StringBuilder builder = new StringBuilder();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try
                {
                    URL url = new URL(url_s);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Accept-Charset", "utf-8");
                    connection.connect();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));

                    String line = "";
                    while ((line = reader.readLine()) != null)
                    {
                        builder.append(line);
                    }
                }catch (Exception ex)
                {
                    Log.e("Error", "" + ex);
                }
                finally
                {
                    connection.disconnect();
                }
            }
        });


        thread.start();
        try {
            thread.join();
        }catch(Exception e){

        }

        dialog.dismiss();
        return builder.toString();
    }
}
