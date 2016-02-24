package com.example.mike.apppaciente;

public class Valor {

    private int valor;
    private int pasos;

    public Valor(int valor, int pasos) {
        this.valor = valor;
        this.pasos = pasos;
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
}
