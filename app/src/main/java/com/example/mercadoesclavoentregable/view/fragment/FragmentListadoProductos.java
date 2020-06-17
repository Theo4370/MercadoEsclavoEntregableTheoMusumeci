package com.example.mercadoesclavoentregable.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mercadoesclavoentregable.R;
import com.example.mercadoesclavoentregable.databinding.FragmentListadoProductosBinding;
import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.ProductoContainer;
import com.example.mercadoesclavoentregable.view.adapter.ProductoAdapter;

import java.util.List;


public class FragmentListadoProductos extends Fragment implements ProductoAdapter.ProductoAdapterListener {

    private FragmentListadoProductosListener fragmentListadoProductosListener;
    private ProductoAdapter productoAdapter;

    private FragmentListadoProductosBinding binding;

    public FragmentListadoProductos() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.fragmentListadoProductosListener = (FragmentListadoProductosListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListadoProductosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Bundle bundle = getArguments();
        ProductoContainer productoContainer = (ProductoContainer) bundle.getSerializable("productos");
        List<Producto> productoList = productoContainer.getProductoList();



        productoAdapter = new ProductoAdapter(productoList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        binding.fragmentListadoRecyclerView.setLayoutManager(linearLayoutManager);
        binding.fragmentListadoRecyclerView.setAdapter(productoAdapter);


        return view;
    }

    @Override
    public void onClickProductoAdapterListener(Producto producto) {
        fragmentListadoProductosListener.onClick(producto);

    }

    public interface FragmentListadoProductosListener {
        public void onClick(Producto producto);


    }
}
