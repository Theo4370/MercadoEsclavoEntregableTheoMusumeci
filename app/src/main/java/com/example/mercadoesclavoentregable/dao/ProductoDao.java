package com.example.mercadoesclavoentregable.dao;

import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.ProductoContainer;
import com.example.mercadoesclavoentregable.service.ProductoService;
import com.example.mercadoesclavoentregable.util.ResultListener;

import java.nio.file.Path;
import java.util.ArrayList;

import okhttp3.internal.http2.PushObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class ProductoDao extends RetrofitDao {

    private ProductoService productoService;

    public ProductoDao() {
        productoService = super.retrofit.create(ProductoService.class);
    }

    public void getProductoPorSearch(String id, final ResultListener<ProductoContainer> resultListenerPorController) {
        Call<ProductoContainer> call = this.productoService.getProductoPorSearch(id);

        call.enqueue(new Callback<ProductoContainer>() {
            @Override
            public void onResponse(Call<ProductoContainer> call, Response<ProductoContainer> response) {
                if (response.isSuccessful()) {
                    ProductoContainer productoContainer = response.body();
                    resultListenerPorController.onFinish(productoContainer);
                } else {
                    response.errorBody();
                }

            }

            @Override
            public void onFailure(Call<ProductoContainer> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void getProductoById(String id, final ResultListener<Producto> resultListenerPorController) {
        Call<Producto> call = this.productoService.getProductoById(id);

        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful()) {
                    Producto producto = response.body();
                    resultListenerPorController.onFinish(producto);
                } else {
                    response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public void getDescripcionProducto(String id, final ResultListener<ArrayList<Producto>> resultListenerFromController) {
        Call<ArrayList<Producto>> call = this.productoService.getDescripcionProducto(id);

        call.enqueue(new Callback<ArrayList<Producto>>() {
            @Override
            public void onResponse(Call<ArrayList<Producto>> call, Response<ArrayList<Producto>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Producto> producto = response.body();
                    resultListenerFromController.onFinish(producto);
                } else {
                    response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Producto>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
