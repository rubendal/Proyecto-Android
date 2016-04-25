package com.example.represmash.appdoctor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.represmash.appdoctor.db.DB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Ruben on 12/03/2016.
 */
public class DescargarAsyncTask extends AsyncTask<String, String, String> {

    private Context context;
    private Paciente paciente;
    private HashMap<String, String> postParams;
    private String title;
    private boolean conDialog;

    private ProgressDialog dialog;

    public DescargarAsyncTask(Context context, Paciente paciente, String title) {
        this.context = context;
        this.paciente = paciente;
        this.postParams = paciente.generatePOSTID();
        this.title = title;
        conDialog = true;
        dialog = new ProgressDialog(context);
    }

    public DescargarAsyncTask(Context context, HashMap<String, String> postParams, String title, boolean conDialog) {
        this.context = context;
        this.paciente = paciente;
        this.postParams = paciente.generatePOSTID();
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
            DB db = new DB(context);

            db.open();

            ArrayList<Valor> valores = db.getDataFromPaciente(paciente);
            //Leemos json y comparamos si timestamp, valor y pasos son iguales
            JSONArray json = new JSONArray(s);

            LinkedList<Valor> valoresNuevos = new LinkedList<>();

            for(int i =0;i<json.length();i++){
                JSONObject data = json.getJSONObject(i);

                int id = data.getInt("id");
                int id_paciente = data.getInt("id_paciente");
                String timestamp = data.getString("timestamp");
                String timestamp_servidor = data.getString("timestamp_servidor");
                int valor = data.getInt("valor");
                int pasos = data.getInt("pasos");

                Valor v = new Valor(valor,pasos,timestamp);
                v.setId_paciente(id_paciente);
                valoresNuevos.add(v);


            }

            //Si son iguales no hacemos nada
            //Sino insertamos el registro en la base de datos
            for(Valor v : valoresNuevos){
                if(!valores.contains(v)){
                    db.insertWithTimestamp(v.getId_paciente(),v.getValor(),v.getPasos(),v.getTimestamp());
                }
            }




            db.close();

        }catch(Exception e){

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
