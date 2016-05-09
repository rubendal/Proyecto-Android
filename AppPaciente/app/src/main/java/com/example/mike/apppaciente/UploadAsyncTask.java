package com.example.mike.apppaciente;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mike.apppaciente.db.DB;

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
 * Created by Ruben on 12/03/2016.
 */
public class UploadAsyncTask extends AsyncTask<String, String, String> {

    private Context context;
    private Valor valor;
    private HashMap<String, String> postParams;
    private String title;
    private boolean conDialog;

    private ProgressDialog dialog;

    public UploadAsyncTask(Context context, Valor valor, String title) {
        this.context = context;
        this.valor = valor;
        postParams = valor.generarParams();
        this.title = title;
        conDialog = true;
        dialog = new ProgressDialog(context);
    }

    public UploadAsyncTask(Context context, Valor valor, String title, boolean conDialog) {
        this.context = context;
        this.valor = valor;
        postParams = valor.generarParams();
        this.title = title;
        this.conDialog = conDialog;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setTitle(title);
        dialog.setCancelable(false);
        if(conDialog) {
            dialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder builder = new StringBuilder();
        HttpURLConnection connection = null;

        try
        {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(generarPOST(postParams));

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
        return builder.toString();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(conDialog) {
            dialog.dismiss();
        }
        try{
            if(s.equals("1")){
                valor.setSubido(1);
                DB db = new DB(context);
                db.open();

                db.update(valor);

                Log.e("Servidor","Se subio un dato");
                db.close();
            }else{
                Log.e("Servidor","No se subio un dato");
                //No se subio el registro por lo que no lo modificamos en la base de datos
            }

        }catch(Exception e){
            Log.e("Servidor","No se subio un dato por un error");
        }
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
}
