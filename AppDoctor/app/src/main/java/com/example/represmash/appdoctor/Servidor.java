package com.example.represmash.appdoctor;

/**
 * Created by Ruben on 14/03/2016.
 */
public class Servidor {

    public static String SITIO = "";
    public static String PUERTO = "";
    public static String DIRECTORIO = "";
    public static String PROTOCOLO = "http";

    public static String Direccion(String link){
        return String.format("%s://%s%s%s%s", PROTOCOLO, SITIO, PUERTO.equals("") ? "" : ":" + PUERTO, DIRECTORIO, link);
    }
}