package com.example.represmash.appdoctor.db;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.represmash.appdoctor.AsyncMethod;
import com.example.represmash.appdoctor.GraphActivity;
import com.example.represmash.appdoctor.Paciente;
import com.example.represmash.appdoctor.PostAsyncTask;
import com.example.represmash.appdoctor.Servidor;
import com.example.represmash.appdoctor.Sesion;
import com.example.represmash.appdoctor.Valor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Ruben on 24/03/2016.
 */
public class DBServer implements AsyncMethod {

    private static Paciente paciente;

    //AppPaciente
    public static void upload(Activity activity){
        DB db = new DB(activity);

        db.open();

        ArrayList<Valor> valores = db.getAll();

        db.close();
    }

    //AppDoctor
    public static void download(Activity activity,Paciente paciente){
        DBServer.paciente = paciente;
        new PostAsyncTask(activity, paciente.generatePOSTID(), new DBServer(),"Cargando", true).execute(Servidor.Direccion("/doctor/descargar.php"));

    }

    @Override
    public void Haz(Activity activity, String res) {
            try{
                DB db = new DB(activity);

                db.open();

                ArrayList<Valor> valores = db.getDataFromPaciente(paciente);
                //Leemos json y comparamos si timestamp, valor y pasos son iguales
                JSONArray json = new JSONArray(res);

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

        /*Intent i = new Intent(activity, GraphActivity.class);
        i.putExtra("paciente",paciente);
        activity.startActivity(i);*/

        }
}
