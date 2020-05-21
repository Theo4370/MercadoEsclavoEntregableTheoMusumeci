package com.example.mercadoesclavoentregable.controller;

import com.example.mercadoesclavoentregable.dao.ProductoDao;
import com.example.mercadoesclavoentregable.model.Producto;

import java.util.List;

public class ProductoController {

    public List<Producto> getProducto(){
        List<Producto> listaDeProductos = ProductoDao.getProducto();
    return listaDeProductos;
    }
}
