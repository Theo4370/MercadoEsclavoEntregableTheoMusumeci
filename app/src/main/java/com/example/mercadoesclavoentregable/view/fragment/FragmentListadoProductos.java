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
import com.example.mercadoesclavoentregable.controller.ProductoController;
import com.example.mercadoesclavoentregable.databinding.FragmentListadoProductosBinding;
import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.ProductoContainer;
import com.example.mercadoesclavoentregable.util.ResultListener;
import com.example.mercadoesclavoentregable.view.adapter.ProductoAdapter;

import java.util.List;


public class FragmentListadoProductos extends Fragment implements ProductoAdapter.ProductoAdapterListener {

    private FragmentListadoProductosListener fragmentListadoProductosListener;
    private ProductoAdapter productoAdapter;
    private ProductoController productoController;

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
        final ProductoContainer productoContainer = (ProductoContainer) bundle.getSerializable("productos");
        List<Producto> productoList = productoContainer.getProductoList();


        productoController = new ProductoController();

        productoAdapter = new ProductoAdapter(productoList, this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        binding.fragmentListadoRecyclerView.setLayoutManager(linearLayoutManager);
        binding.fragmentListadoRecyclerView.setAdapter(productoAdapter);

        binding.fragmentListadoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Integer posicionActual = linearLayoutManager.findLastVisibleItemPosition();
                Integer ultimaCelda = linearLayoutManager.getItemCount();

                if (posicionActual.equals(ultimaCelda - 3)) {

                    if (productoController.getHayMasProductos()) {

                        productoController.getProductoPorSearchPaginado(productoContainer.getQuery(), new ResultListener<ProductoContainer>() {
                            @Override
                            public void onFinish(ProductoContainer result) {
                                productoAdapter.agregarProductos(result.getProductoList());
                            }

                        });
                    }

                }

            }
        });


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
