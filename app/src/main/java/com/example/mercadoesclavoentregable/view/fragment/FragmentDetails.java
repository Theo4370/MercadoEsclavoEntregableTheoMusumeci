package com.example.mercadoesclavoentregable.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mercadoesclavoentregable.R;
import com.example.mercadoesclavoentregable.controller.ProductoController;
import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.util.ResultListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetails extends Fragment {

    private ProductoController productoController;


    public FragmentDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        Bundle bundle = getArguments();
        Producto producto = (Producto) bundle.getSerializable("Producto");

        ImageView imageViewProducto = view.findViewById(R.id.detailsProductoImagen);
        TextView textViewProducto = view.findViewById(R.id.detailsProductoTextView);
        final TextView textViewDescripcion = view.findViewById(R.id.detailsProductoDescripcion);

        productoController = new ProductoController();
        productoController.getDescripcionProducto(producto.getId(), new ResultListener<ArrayList<Producto>>() {
            @Override
            public void onFinish(ArrayList<Producto> result) {
                ArrayList<Producto> descripcion = result;
                textViewDescripcion.setText(descripcion.get(0).getDescripcion());
            }
        });

        Glide.with(getContext())
                .load(producto.getPictures().get(0).getSecureUrl())
                .into(imageViewProducto);
        textViewProducto.setText(producto.getTitle());



        return view;
    }

}
