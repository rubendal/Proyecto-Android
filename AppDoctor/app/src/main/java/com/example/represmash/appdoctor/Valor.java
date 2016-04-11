package com.example.represmash.appdoctor;

import java.text.SimpleDateFormat;

/**
 * Created by Ruben on 23/02/2016.
 */
public class Valor {

    private String timestamp;
    private int valor;
    private int pasos;

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

    @Override
    public boolean equals(Object o) {
        if(o instanceof Valor){
            Valor v = (Valor)o;
            if(timestamp != null && v.timestamp!=null){
                return timestamp.equals(v.timestamp);
            }
        }
        return super.equals(o);
    }
}
