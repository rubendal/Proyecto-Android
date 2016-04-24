package com.example.mike.apppaciente;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.mike.apppaciente.db.DB;

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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ruben on 12/03/2016.
 */
public class AlertaAsyncTask extends AsyncTask<String, String, String> {

    private Context context;
    private HashMap<String, String> postParams;
    private String title;
    private boolean conDialog;

    private ProgressDialog dialog;

    public AlertaAsyncTask(Context context, int paciente_id, String title) {
        this.context = context;
        postParams = new HashMap<>();
        postParams.put("id_paciente",String.valueOf(paciente_id));
        this.title = title;
        conDialog = true;
        dialog = new ProgressDialog(context);
    }

    public AlertaAsyncTask(Context context, int paciente_id, String title, boolean conDialog) {
        this.context = context;
        postParams = new HashMap<>();
        postParams.put("id_paciente",String.valueOf(paciente_id));
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
            JSONArray json = new JSONArray(s);
            for(int i=0;i<json.length();i++){
                JSONObject data = json.getJSONObject(i);
                int id = data.getInt("id");
                String timestamp = data.getString("timestamp");
                String alerta = data.getString("alerta");
                int vista = data.getInt("vista");
                if(vista == 0) {
                    crearNotificacion("Alerta recibida", alerta, id);
                }
            }
        }catch(Exception e){

        }
    }

    private void crearNotificacion(String title, String text, int id){
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(text);
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
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
