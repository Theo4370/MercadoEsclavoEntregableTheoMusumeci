package com.example.mercadoesclavoentregable.controller;

import com.example.mercadoesclavoentregable.dao.ProductoDao;
import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.ProductoContainer;
import com.example.mercadoesclavoentregable.util.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class ProductoController {

    private ProductoDao productoDao;

    public ProductoController() {
        this.productoDao = new ProductoDao() {
        };
    }

    public void getProductoPorSearch(String id, final ResultListener<ProductoContainer> resultListenerFromView) {
        productoDao.getProductoPorSearch(id, new ResultListener<ProductoContainer>() {
            @Override
            public void onFinish(ProductoContainer result) {
                resultListenerFromView.onFinish(result);
            }
        });
    }

    public void getProductoById(String id, final ResultListener<Producto> resultListeneFromView){
        productoDao.getProductoById(id, new ResultListener<Producto>() {
            @Override
            public void onFinish(Producto result) {
                resultListeneFromView.onFinish(result);
            }
        });
    }

    public void getDescripcionProducto(String id, final ResultListener<ArrayList<Producto>> resultListenerFromView){
        productoDao.getDescripcionProducto(id, new ResultListener<ArrayList<Producto>>() {
            @Override
            public void onFinish(ArrayList<Producto> result) {
                resultListenerFromView.onFinish(result);
            }
        });
    }
}