package com.example.mercadoesclavoentregable;

import java.util.ArrayList;
import java.util.List;

public abstract class ProveedorDeProductos {

    public static List<Producto> getProducto() {
        List<Producto> listaDeProductos = new ArrayList<>();
        listaDeProductos.add(new Producto("Laptop Dell", 25000.0, R.drawable.laptopdell));
        listaDeProductos.add(new Producto("Taladro BD", 3000.0, R.drawable.taladro));
        listaDeProductos.add(new Producto("Renault 12 usado", 34000.0, R.drawable.renault12));
        listaDeProductos.add(new Producto("Mesa", 2300.0, R.drawable.mesa));
        listaDeProductos.add(new Producto("Placa de video RX460", 6000.0, R.drawable.rx460));
        listaDeProductos.add(new Producto("Vagón de carga", 42000.0, R.drawable.vagon));
        listaDeProductos.add(new Producto("Anteojos Rayban", 7000.0, R.drawable.rayban));
        listaDeProductos.add(new Producto("Auriculares Noga", 1800.0, R.drawable.aurisnoga));

        return listaDeProductos;
    }
}
