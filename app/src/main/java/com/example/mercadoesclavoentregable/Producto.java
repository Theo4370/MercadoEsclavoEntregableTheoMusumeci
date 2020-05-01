package com.example.mercadoesclavoentregable;

public class Producto {
    private String nombreProducto;
    private Double precioProducto;
    private Integer fotoProducto;

    public Producto(String nombreProducto, Double precioProducto, Integer fotoProducto) {
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.fotoProducto = fotoProducto;
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
