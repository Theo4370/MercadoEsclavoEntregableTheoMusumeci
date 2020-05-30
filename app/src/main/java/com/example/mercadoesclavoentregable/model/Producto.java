package com.example.mercadoesclavoentregable.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Producto implements Serializable {
    private String id;
    private String title;
    private Double price;

    @SerializedName("secure_url")
    private String secureUrl;

    @SerializedName("pictures")
    private List<Producto> pictures;
    @SerializedName("thumbnail")
    private String thumbnail;

    private String condition;
   // @SerializedName("descriptions")
    private String descripcion;


    public Producto() {

    }

    public Producto(String id, String title, Double price, String fotoProducto, String condition, String descripcion, List<Producto> pictures, String secureUrl) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.thumbnail = fotoProducto;
        this.condition = condition;
        this.descripcion = descripcion;
        this.secureUrl = secureUrl;
        this.pictures = pictures;
    }

    public String getSecureUrl() {
        return secureUrl;
    }

    public void setSecureUrl(String secureUrl) {
        this.secureUrl = secureUrl;
    }

    public List<Producto> getPictures() {
        return pictures;
    }

    public void setPictures(List<Producto> pictures) {
        this.pictures = pictures;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<Producto> getPictureList() {
        return pictures;
    }

    public void setPictureList(List<Producto> pictureList) {
        this.pictures = pictures;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


}
