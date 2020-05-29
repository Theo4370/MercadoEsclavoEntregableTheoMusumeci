package com.example.mercadoesclavoentregable.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Producto implements Serializable {
    private String id;
    private String title;
    private Double price;

    @SerializedName("thumbnail")
    private String fotoProducto;

    private String condition;


    public Producto() {

    }

    public Producto(String id, String title, Double price, String fotoProducto, String condition) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.fotoProducto = fotoProducto;
        this.condition = condition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFotoProducto() {
        return fotoProducto;
    }

    public void setFotoProducto(String fotoProducto) {
        this.fotoProducto = fotoProducto;
    }

    public String getDescripcionProducto() {
        return condition;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.condition = condition;
    }


}
