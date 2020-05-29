package com.example.mercadoesclavoentregable.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mercadoesclavoentregable.R;
import com.example.mercadoesclavoentregable.model.Producto;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {


    private List<Producto> listaDeProductos;
    private ProductoAdapterListener productoAdapterListener;


    public ProductoAdapter(List<Producto> listaDeProductos, ProductoAdapterListener listener) {
        this.listaDeProductos = listaDeProductos;
        this.productoAdapterListener = listener;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater infladorLayout = LayoutInflater.from(parent.getContext());
        View celdaView = infladorLayout.inflate(R.layout.celda_producto, parent, false);

        return new ProductoViewHolder(celdaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto productoAMostrar = listaDeProductos.get(position);
        holder.onBind(productoAMostrar);

    }

    @Override
    public int getItemCount() {
        return this.listaDeProductos.size();
    }

    public void setListaDeProductos(List<Producto> listaDeProductos) {
        this.listaDeProductos = listaDeProductos;
        notifyDataSetChanged();
    }

    protected class ProductoViewHolder extends RecyclerView.ViewHolder {

        private TextView nombreProducto;
        private TextView precioProducto;
        private ImageView fotoProducto;


        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreProducto = itemView.findViewById(R.id.celdaArticuloTextViewArticulo);
            precioProducto = itemView.findViewById(R.id.celdaArticuloTextViewPrecio);
            fotoProducto = itemView.findViewById(R.id.celdaArticuloImageViewFoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Producto producto = listaDeProductos.get(getAdapterPosition());
                    productoAdapterListener.onClickProductoAdapterListener(producto);
                }
            });

        }

        public void onBind(Producto unProducto) {
            Glide.with(nombreProducto.getContext())
                    .load(unProducto.getFotoProducto())
                    .into(fotoProducto);

            precioProducto.setText(unProducto.getPrice().toString());
            nombreProducto.setText(unProducto.getTitle());
        }


    }

    public interface ProductoAdapterListener {
        public void onClickProductoAdapterListener(Producto producto);
    }
}
