package com.example.mercadoesclavoentregable.model;

import com.google.gson.annotations.SerializedName;
import com.google.type.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
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

    //DESCRIPCION DEL PRODUCTO
    @SerializedName("plain_text")
    private String plainText;



    @SerializedName("seller_address")
    private Producto sellerAdress;
    @SerializedName("city")
    private Producto city;
    @SerializedName("name")
    private String nombreCity;




    @SerializedName("latitude")
    private String latitud;
    @SerializedName("longitude")
    private String longitud;


    public Producto() {
    }

    public Producto(String id, String title, Double price, String secureUrl, List<Producto> pictures, String thumbnail, String condition, String plainText, Producto sellerAdress, Producto city, String nombreCity, String latitud, String longitud) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.secureUrl = secureUrl;
        this.pictures = pictures;
        this.thumbnail = thumbnail;
        this.condition = condition;
        this.plainText = plainText;
        this.sellerAdress = sellerAdress;
        this.city = city;
        this.nombreCity = nombreCity;
        this.latitud = latitud;
        this.longitud = longitud;

    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
    public Producto getCity() {
        return city;
    }

    public void setCity(Producto city) {
        this.city = city;
    }

    public String getNombreCity() {
        return nombreCity;
    }

    public void setNombreCity(String nombreCity) {
        this.nombreCity = nombreCity;
    }





    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public Producto getSellerAdress() {
        return sellerAdress;
    }

    public void setSellerAdress(Producto sellerAdress) {
        this.sellerAdress = sellerAdress;
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
        return plainText;
    }

    public void setDescripcion(String descripcion) {
        this.plainText = plainText;
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
