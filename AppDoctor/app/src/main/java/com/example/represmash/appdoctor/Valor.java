package com.example.represmash.appdoctor;

import android.util.Log;

import java.text.SimpleDateFormat;

/**
 * Created by Ruben on 23/02/2016.
 */
public class Valor {

    private String timestamp;
    private int valor;
    private int pasos;
    private int id_paciente;

    public Valor(int valor, int pasos) {
        this.valor = valor;
        this.pasos = pasos;
    }

    public Valor(int valor, int pasos, String timestamp) {
        this.valor = valor;
        this.pasos = pasos;
        this.timestamp = timestamp;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getPasos() {
        return pasos;
    }

    public void setPasos(int pasos) {
        this.pasos = pasos;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Valor){
            Valor v = (Valor)o;
            if(timestamp != null && v.timestamp!=null){
                if(id_paciente == v.id_paciente) {
                    return timestamp.equals(v.timestamp);
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
        return super.equals(o);
    }
}
