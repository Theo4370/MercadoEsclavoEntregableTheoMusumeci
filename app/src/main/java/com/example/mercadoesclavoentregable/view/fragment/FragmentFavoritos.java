package com.example.mercadoesclavoentregable.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mercadoesclavoentregable.MainActivity;
import com.example.mercadoesclavoentregable.R;
import com.example.mercadoesclavoentregable.controller.ProductoController;
import com.example.mercadoesclavoentregable.databinding.FragmentFavoritosBinding;
import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.UserInfo;
import com.example.mercadoesclavoentregable.util.ResultListener;
import com.example.mercadoesclavoentregable.view.adapter.ProductoAdapter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FragmentFavoritos extends Fragment implements ProductoAdapter.ProductoAdapterListener {


    private ProductoAdapter productoAdapter;
    private List<Producto> favoritosList;

    private FragmentFavoritosListener fragmentFavoritosListener;
    private ProductoController productoController;

    private FragmentFavoritosBinding binding;


    private Producto deletedProducto = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.fragmentFavoritosListener = (FragmentFavoritosListener) context;
    }

    public FragmentFavoritos() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        Bundle bundle = getArguments();

        UserInfo userInfo = (UserInfo) bundle.getSerializable(MainActivity.USERINFO);

        favoritosList = userInfo.getFavoritosList();



        productoAdapter = new ProductoAdapter(favoritosList, this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        binding.fragmentFavoritosRecyclerView.setLayoutManager(linearLayoutManager);
        binding.fragmentFavoritosRecyclerView.setAdapter(productoAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.fragmentFavoritosRecyclerView);


        return view;
    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN |
            ItemTouchHelper.START |
            ItemTouchHelper.END,
            ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            Integer fromPosition = viewHolder.getAdapterPosition();
            Integer toPosition = target.getAdapterPosition();
            Collections.swap(favoritosList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();

            deletedProducto = favoritosList.get(position);
            favoritosList.remove(deletedProducto);

            productoAdapter.notifyItemRemoved(position);
            Snackbar.make(binding.fragmentFavoritosRecyclerView, "Producto eliminado", BaseTransientBottomBar.LENGTH_LONG)
                    .setAction("Deshacer", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            favoritosList.add(position, deletedProducto);
                            productoAdapter.notifyItemInserted(position);
                        }
                    }).show();
        }
    };


    @Override
    public void onClickProductoAdapterListener(Producto producto) {
        fragmentFavoritosListener.onClick(producto);
    }

    public interface FragmentFavoritosListener {
        public void onClick(Producto producto);

    }
}