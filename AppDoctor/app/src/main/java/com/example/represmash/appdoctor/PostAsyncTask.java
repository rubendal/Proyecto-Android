package com.example.represmash.appdoctor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

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
public class PostAsyncTask extends AsyncTask<String, String, String> {

    private Activity activity;
    private HashMap<String, String> postParams;
    private String title;
    private boolean conDialog;
    private AsyncMethod asyncMethod;
    private AsyncMethodActivity asyncMethodActivity;

    private ProgressDialog dialog;

    public PostAsyncTask(Activity activity, HashMap<String, String> postParams, String title, AsyncMethodActivity asyncMethodActivity) {
        this.activity = activity;
        this.postParams = postParams;
        this.title = title;
        conDialog = true;
        this.asyncMethodActivity = asyncMethodActivity;
        dialog = new ProgressDialog(activity);
    }

    public PostAsyncTask(Activity activity, HashMap<String, String> postParams, String title, AsyncMethodActivity asyncMethodActivity, boolean conDialog) {
        this.activity = activity;
        this.postParams = postParams;
        this.title = title;
        this.conDialog = conDialog;
        this.asyncMethodActivity = asyncMethodActivity;
        dialog = new ProgressDialog(activity);
    }

    public PostAsyncTask(Activity activity, HashMap<String, String> postParams, AsyncMethod cl, String title) {
        this.activity = activity;
        this.postParams = postParams;
        this.title = title;
        conDialog = true;
        dialog = new ProgressDialog(activity);
        this.asyncMethod = cl;
    }

    public PostAsyncTask(Activity activity, HashMap<String, String> postParams, AsyncMethod cl,String title, boolean conDialog) {
        this.activity = activity;
        this.postParams = postParams;
        this.title = title;
        this.conDialog = conDialog;
        dialog = new ProgressDialog(activity);
        this.asyncMethod = cl;
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
        if(asyncMethodActivity!=null) {
            asyncMethodActivity.Haz(s);
        }else{
            asyncMethod.Haz(activity,s);
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
