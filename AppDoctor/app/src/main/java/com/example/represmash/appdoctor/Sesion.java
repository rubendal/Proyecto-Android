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
        String response = HttpHandler.sendPostRequest(activity, "http://192.168.100.17/doctor/login.php", params, "Iniciando sesión");



        try{
            int id = Integer.parseInt(response);
            if(id != -1){
                Sesion.username = username;
                Sesion.ID = id;
                return true;
            }
        }catch(Exception e){

        }
        return false;
    }
}
