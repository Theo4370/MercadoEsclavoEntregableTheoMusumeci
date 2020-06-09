package com.example.mercadoesclavoentregable.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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

    private ImageView imageViewProducto;
    private TextView textViewProducto;
    private TextView textViewDescripcion;
    private Producto producto;
    private ProductoController productoController;


    public FragmentDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        producto = getProductoClickeado();
        findViewById(view);
        getAndSetViews();


        return view;
    }

    /**
     * Hace los pedidos a internet de la descripcion y foto y las setea a las views.
     * */
    private void getAndSetViews() {
        getAndSetDescripcionProducto(producto, textViewDescripcion);
        getAndSetFotoProducto(producto, imageViewProducto);
        textViewProducto.setText(producto.getTitle());
    }

    /**
     * Hace el pedido a internet del producto por ID, pido la foto del producto y la seteo.
     * */
    private void getAndSetFotoProducto(Producto producto, final ImageView imageViewProducto) {
        productoController = new ProductoController();
        productoController.getProductoById(producto.getId(), new ResultListener<Producto>() {
            @Override
            public void onFinish(Producto result) {
                Producto producto = result;
                Glide.with(getContext())
                        .load(producto.getPictures().get(0).getSecureUrl())
                        .into(imageViewProducto);
            }
        });
    }

    /**
     * Se hace el pedido a internet de la descripcion del producto y se lo setea al textViewDescripcion
     */
    private void getAndSetDescripcionProducto(Producto producto, final TextView textViewDescripcion) {
        productoController = new ProductoController();
        productoController.getDescripcionProducto(producto.getId(), new ResultListener<ArrayList<Producto>>() {
            @Override
            public void onFinish(ArrayList<Producto> result) {
                ArrayList<Producto> descripcion = result;
                textViewDescripcion.setText(descripcion.get(0).getDescripcion());
            }
        });
    }

    /**
     * Traigo el objeto producto que me provee el bundle que me da la main
     */
    private Producto getProductoClickeado() {
        Bundle bundle = getArguments();
        return (Producto) bundle.getSerializable("Producto");
    }

    /**
     * Se hacen los find by id de los componentes del fragment
     */
    private void findViewById(View view) {
        imageViewProducto = view.findViewById(R.id.detailsProductoImagen);
        textViewProducto = view.findViewById(R.id.detailsProductoTextView);
        textViewDescripcion = view.findViewById(R.id.detailsProductoDescripcion);
    }
}


