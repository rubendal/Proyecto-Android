package com.example.mike.apppaciente;

import java.util.HashMap;

public class Valor {

    private int id_db;
    private String timestamp;
    private int valor;
    private int pasos;
    private int id_paciente;
    private int subido;

    public Valor(int valor, int pasos) {
        this.valor = valor;
        this.pasos = pasos;
    }

    public Valor(int valor, int pasos, String timestamp) {
        this.valor = valor;
        this.pasos = pasos;
        this.timestamp = timestamp;
    }

    public Valor(int id_db,int valor, int pasos, String timestamp,int subido) {
        this.id_db = id_db;
        this.valor = valor;
        this.pasos = pasos;
        this.timestamp = timestamp;
        this.subido = subido;
    }

    public int getId_db() {
        return id_db;
    }

    public void setId_db(int id_db) {
        this.id_db = id_db;
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

    public int getSubido() {
        return subido;
    }

    public void setSubido(int subido) {
        this.subido = subido;
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

    public HashMap<String,String> generarParams(){
        HashMap<String,String> params = new HashMap<>();
        params.put("valor",String.valueOf(valor));
        params.put("pasos",String.valueOf(pasos));
        params.put("timestamp",String.valueOf(timestamp));
        return params;
    }
}
