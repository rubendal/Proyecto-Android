package com.example.mike.apppaciente.db;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.mike.apppaciente.AsyncMethod;
import com.example.mike.apppaciente.GraficasActivity;
import com.example.mike.apppaciente.Paciente;
import com.example.mike.apppaciente.PostAsyncTask;
import com.example.mike.apppaciente.Servidor;
import com.example.mike.apppaciente.UploadAsyncTask;
import com.example.mike.apppaciente.Valor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Ruben on 24/03/2016.
 */
public class DBServer {

    private static Paciente paciente;

    //AppPaciente
    public static void upload(Activity activity){
        DB db = new DB(activity);

        db.open();

        ArrayList<Valor> valores = db.getAll();

        for(Valor valor :valores){
            if(valor.getSubido()==0) { //Este valor no se a subido
                UploadAsyncTask asyncTask = new UploadAsyncTask(activity, valor, "Cargando", false);
                asyncTask.execute(Servidor.Direccion("/doctor/subir.php"));
            }
        }

        db.close();
    }

}
