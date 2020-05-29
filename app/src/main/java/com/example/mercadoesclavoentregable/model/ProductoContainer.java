package com.example.mercadoesclavoentregable.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductoContainer implements Serializable {
    @SerializedName("query")
    private String query;

    @SerializedName("results")
    private ArrayList<Producto> productoList;

    public ProductoContainer() {
    }

    public ProductoContainer(String query, ArrayList<Producto> productoList) {
        this.query = query;
        this.productoList = productoList;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(ArrayList<Producto> productoList) {
        this.productoList = productoList;
    }
}
