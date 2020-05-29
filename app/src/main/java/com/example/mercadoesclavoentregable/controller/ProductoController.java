package com.example.mercadoesclavoentregable.controller;

import com.example.mercadoesclavoentregable.dao.ProductoDao;
import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.ProductoContainer;
import com.example.mercadoesclavoentregable.util.ResultListener;

import java.util.List;

public class ProductoController {

    private ProductoDao productoDao;

    public ProductoController() {
        this.productoDao = new ProductoDao() {
        };
    }

    public void getProductoPorSearch(final ResultListener<ProductoContainer> resultListenerFromView) {
        productoDao.getProductoPorSearch(new ResultListener<ProductoContainer>() {
            @Override
            public void onFinish(ProductoContainer result) {
                resultListenerFromView.onFinish(result);
            }
        });

   /*
    public List<Producto> getProducto(){
        List<Producto> listaDeProductos = ProductoDao.getProducto();
    return listaDeProductos;
    }*/
        // resultListenerFromView.onFinish(result);
    }
}