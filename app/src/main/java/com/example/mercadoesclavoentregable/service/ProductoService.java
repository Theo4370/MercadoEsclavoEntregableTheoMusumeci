package com.example.mercadoesclavoentregable.service;

import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.ProductoContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductoService {


    @GET("search")
    Call<ProductoContainer> getProductoPorSearch(@Query("q") String producto);

}
