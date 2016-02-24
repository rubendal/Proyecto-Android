package com.example.represmash.appdoctor;

import java.io.Serializable;

/**
 * Created by Ruben on 23/02/2016.
 */
public class Paciente implements Serializable{

    private String nombre, telefono;
    private int edad, genero;

    private String nombre_emergencia, telefono_emergencia;

    public Paciente(String nombre, String telefono, int edad, int genero, String nombre_emergencia, String telefono_emergencia) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.edad = edad;
        this.genero = genero;
        this.nombre_emergencia = nombre_emergencia;
        this.telefono_emergencia = telefono_emergencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public String getNombre_emergencia() {
        return nombre_emergencia;
    }

    public void setNombre_emergencia(String nombre_emergencia) {
        this.nombre_emergencia = nombre_emergencia;
    }

    public String getTelefono_emergencia() {
        return telefono_emergencia;
    }

    public void setTelefono_emergencia(String telefono_emergencia) {
        this.telefono_emergencia = telefono_emergencia;
    }
}
