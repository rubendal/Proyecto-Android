package com.example.represmash.appdoctor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Ruben on 04/03/2016.
 */
public class Sesion implements AsyncMethod {

    public static int ID = -1;
    public static String username = "";

    public static void iniciarSesion(Activity activity, String username, String password){

        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        Sesion.username = username;

        new PostAsyncTask(activity, params, new Sesion(), "Iniciando sesión",true).execute(Servidor.Direccion("/doctor/login.php"));
    }

    public static HashMap<String, String> sesionParams(){
        HashMap<String, String> params = new HashMap<>();
        params.put("id_doctor", Integer.toString(ID));
        return params;
    }

    @Override
    public void Haz(Activity activity, String res) {
        try {
            try {
                int id = Integer.parseInt(res);
                if (id != -1) {
                    //Sesion.username = username;
                    Sesion.ID = id;
                    SharedPreferences sharedPreferences = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putInt("id",id);
                    editor.putString("username",username);

                    editor.commit();
                    Intent i = new Intent(activity, InitActivity.class);
                    activity.startActivity(i);
                    activity.finish();
                }else{
                    //Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
                    Snackbar.make(activity.findViewById(android.R.id.content), R.string.error, Snackbar.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Snackbar.make(activity.findViewById(android.R.id.content), R.string.error, Snackbar.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Snackbar.make(activity.findViewById(android.R.id.content), R.string.error, Snackbar.LENGTH_SHORT).show();
        }
    }
}
