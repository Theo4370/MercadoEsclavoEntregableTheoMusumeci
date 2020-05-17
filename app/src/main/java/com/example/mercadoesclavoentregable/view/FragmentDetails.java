package com.example.mercadoesclavoentregable.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mercadoesclavoentregable.R;
import com.example.mercadoesclavoentregable.model.Producto;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetails extends Fragment {

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
        TextView textViewDescripcion = view.findViewById(R.id.detailsProductoDescripción);

        imageViewProducto.setImageResource(producto.getFotoProducto());
        textViewProducto.setText(producto.getNombreProducto());
        textViewDescripcion.setText(producto.getDescripcionProducto());


        return view;
    }

}
