package com.example.mercadoesclavoentregable.dao;

import com.example.mercadoesclavoentregable.model.ProductoContainer;
import com.example.mercadoesclavoentregable.service.ProductoService;
import com.example.mercadoesclavoentregable.util.ResultListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class ProductoDao extends RetrofitDao {

    private ProductoService productoService;

    public ProductoDao() {
        productoService = super.retrofit.create(ProductoService.class);
    }

    public void getProductoPorSearch(final ResultListener<ProductoContainer> resultListenerPorController) {
        Call<ProductoContainer> call = this.productoService.getProductoPorSearch("Celular");

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

}
