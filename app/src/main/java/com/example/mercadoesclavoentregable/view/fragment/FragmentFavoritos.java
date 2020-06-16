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

import com.example.mercadoesclavoentregable.MainActivity;
import com.example.mercadoesclavoentregable.R;
import com.example.mercadoesclavoentregable.controller.ProductoController;
import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.UserInfo;
import com.example.mercadoesclavoentregable.util.ResultListener;
import com.example.mercadoesclavoentregable.view.adapter.ProductoAdapter;

import java.util.ArrayList;
import java.util.List;


public class FragmentFavoritos extends Fragment implements ProductoAdapter.ProductoAdapterListener {


    private ProductoAdapter productoAdapter;
    private List<Producto> favoritosList;
    private RecyclerView recyclerView;
    private FragmentFavoritosListener fragmentFavoritosListener;
    private ProductoController productoController;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.fragmentFavoritosListener = (FragmentFavoritosListener) context;
    }

    public FragmentFavoritos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);


        recyclerView = view.findViewById(R.id.fragmentFavoritosRecyclerView);

        Bundle bundle = getArguments();

        UserInfo userInfo = (UserInfo) bundle.getSerializable(MainActivity.USERINFO);

        List<String> favoritosIds = userInfo.getFavoritosIds();

        productoController = new ProductoController();
        favoritosList = new ArrayList<>();

        for (final String favoritosId : favoritosIds) {

            productoController.getProductoById(favoritosId, new ResultListener<Producto>() {
                @Override
                public void onFinish(Producto result) {
                    favoritosList.add(result);
                    productoAdapter.notifyDataSetChanged();
                }
            });

        }

        productoAdapter = new ProductoAdapter(favoritosList, this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(productoAdapter);


        return view;
    }


    @Override
    public void onClickProductoAdapterListener(Producto producto) {
        fragmentFavoritosListener.onClick(producto);
    }

    public interface FragmentFavoritosListener {
        public void onClick(Producto producto);

    }
}