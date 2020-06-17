package com.example.mercadoesclavoentregable.service;

import android.content.Intent;

import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.ProductoContainer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductoService {


    @GET("sites/MLA/search/")
    Call<ProductoContainer> getProductoPorSearchPaginado(@Query("q") String producto,
                                                         @Query("offset") Integer offset,
                                                         @Query("limit") Integer limit);

    ///sites/MLA/search?q=auto&offset=10&limit=1


    @GET("items/{id}")
    Call<Producto> getProductoById(@Path("id") String id);

    //No se por qué, pero este pedido devuelve un ArrayList de 1 elemento con el "producto" adentro
    @GET("items/{id}/descriptions")
    Call<ArrayList<Producto>> getDescripcionProducto(@Path("id") String id);

}
