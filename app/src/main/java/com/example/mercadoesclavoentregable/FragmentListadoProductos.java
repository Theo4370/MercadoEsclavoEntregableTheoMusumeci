package com.example.mercadoesclavoentregable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FragmentListadoProductos extends Fragment {


    public FragmentListadoProductos() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentInflado = inflater.inflate(R.layout.fragment_listado_productos, container, false);


        RecyclerView recyclerViewProductos = fragmentInflado.findViewById(R.id.fragmentListadoRecyclerView);

        List<Producto> listaDeProductos = ProveedorDeProductos.getProducto();
        ProductoAdapter productoAdapter = new ProductoAdapter(listaDeProductos);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragmentInflado.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewProductos.setLayoutManager(linearLayoutManager);


        recyclerViewProductos.setAdapter(productoAdapter);
        return fragmentInflado;
    }


}
