package com.example.mercadoesclavoentregable.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private String nombreCompleto;
    private String apodo;
    private String edad;
    private String ciudad;

    public UserInfo() {
    }

    public UserInfo(String nombreCompleto, String apodo, String edad, String ciudad) {
        this.nombreCompleto = nombreCompleto;
        this.apodo = apodo;
        this.edad = edad;
        this.ciudad = ciudad;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }


}
