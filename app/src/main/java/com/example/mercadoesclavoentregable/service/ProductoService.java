package com.example.mercadoesclavoentregable.service;

import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.ProductoContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductoService {

    //https://api.mercadolibre.com/sites/MLA/
    //https://api.mercadolibre.com/sites/MLA/
    //https://api.mercadolibre.com/items/MLA635031069


   /* @GET("search")
    Call<ProductoContainer> getProductoPorSearch(@Query("q") String producto);*/


    @GET("sites/MLA/search")
    Call<ProductoContainer> getProductoPorSearch(@Query("q") String producto);


    @GET("items/{id}")
    Call<Producto> getProductoById(@Path("id") String id);

}
