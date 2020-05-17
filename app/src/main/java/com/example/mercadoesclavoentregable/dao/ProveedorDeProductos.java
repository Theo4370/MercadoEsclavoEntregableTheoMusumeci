package com.example.mercadoesclavoentregable.dao;

import com.example.mercadoesclavoentregable.R;
import com.example.mercadoesclavoentregable.model.Producto;

import java.util.ArrayList;
import java.util.List;

public abstract class ProveedorDeProductos {

    public static List<Producto> getProducto() {
        List<Producto> listaDeProductos = new ArrayList<>();
        listaDeProductos.add(new Producto("Laptop Dell", 25000.0, R.drawable.laptopdell, "Segunda mano, muy bien cuidada."));
        listaDeProductos.add(new Producto("Taladro BD", 3000.0, R.drawable.taladro,"Nunca se uso, viene con caja."));
        listaDeProductos.add(new Producto("Renault 12 usado", 34000.0, R.drawable.renault12, "Era de mi abuela."));
        listaDeProductos.add(new Producto("Mesa", 2300.0, R.drawable.mesa, "Siempre le pasé blem."));
        listaDeProductos.add(new Producto("Placa de video RX460", 6000.0, R.drawable.rx460, "Permuto mas dinero por una mejor."));
        listaDeProductos.add(new Producto("Vagón de carga", 42000.0, R.drawable.vagon, "Traido directamente de la British Rail Company."));
        listaDeProductos.add(new Producto("Anteojos Rayban", 7000.0, R.drawable.rayban, "Los encontré en la calle."));
        listaDeProductos.add(new Producto("Auriculares Noga", 1800.0, R.drawable.aurisnoga, "Escucho ofertas, producto a reparar."));

        return listaDeProductos;
    }
}
