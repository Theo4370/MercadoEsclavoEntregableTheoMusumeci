package com.example.mercadoesclavoentregable.controller;

import com.example.mercadoesclavoentregable.dao.ProductoDao;
import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.ProductoContainer;
import com.example.mercadoesclavoentregable.util.ResultListener;

import java.util.ArrayList;

public class ProductoController {

    private ProductoDao productoDao;
    private Integer offset = 0;
    private Boolean hayMasProductos = true;
    public static final Integer PAGE_SIZE = 10;

    public ProductoController() {
        this.productoDao = new ProductoDao() {
        };
    }


    public void getProductoPorSearchPaginado(String producto, final ResultListener<ProductoContainer> resultListenerFromView){
        productoDao.getProductoPorSearchPaginado(producto, offset, PAGE_SIZE, new ResultListener<ProductoContainer>() {
            @Override
            public void onFinish(ProductoContainer result) {

                if (result.getProductoList().size()<PAGE_SIZE){
                  hayMasProductos = false;
                } else {
                    offset = offset + PAGE_SIZE;
                    resultListenerFromView.onFinish(result);
                }
            }
        });


    }

    public Boolean getHayMasProductos() {
        return hayMasProductos;
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