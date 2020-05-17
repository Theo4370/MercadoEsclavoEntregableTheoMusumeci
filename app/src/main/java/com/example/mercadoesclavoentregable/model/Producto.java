package com.example.mercadoesclavoentregable.model;

import java.io.Serializable;

public class Producto implements Serializable {
    private String nombreProducto;
    private Double precioProducto;
    private Integer fotoProducto;
    private String descripcionProducto;

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public Producto(String nombreProducto, Double precioProducto, Integer fotoProducto, String descripcionProducto) {
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.fotoProducto = fotoProducto;
        this.descripcionProducto = descripcionProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(Double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public Integer getFotoProducto() {
        return fotoProducto;
    }

    public void setFotoProducto(Integer fotoProducto) {
        this.fotoProducto = fotoProducto;
    }
}
