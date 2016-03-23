package com.example.represmash.appdoctor;

import android.util.Log;

/**
 * Created by Ruben on 14/03/2016.
 */
public class Servidor {

    public static String SITIO = "rubendal.heliohost.org";
    public static String PUERTO = "";
    public static String DIRECTORIO = "/project/android";
    public static String PROTOCOLO = "http";

    public static String Direccion(String link){
        return String.format("%s://%s%s%s%s", PROTOCOLO, SITIO, PUERTO.equals("") ? "" : ":" + PUERTO, DIRECTORIO, link);
    }
}
