package com.example.represmash.appdoctor;

import android.app.Activity;

import java.util.HashMap;

/**
 * Created by Ruben on 04/03/2016.
 */
public class Sesion {

    public static int ID = -1;
    public static String username = "";

    public static boolean iniciarSesion(Activity activity, String username, String password){

        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        try {
            String response = new PostAsyncTask(activity, params, "Iniciando sesión").execute(Servidor.Direccion("/doctor/login.php")).get();


            try {
                int id = Integer.parseInt(response);
                if (id != -1) {
                    Sesion.username = username;
                    Sesion.ID = id;
                    return true;
                }
            } catch (Exception e) {

            }
        }catch(Exception e){

        }
        return false;
    }
}
