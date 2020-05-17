package com.example.mercadoesclavoentregable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentListadoProductos fragmentListadoProductos = new FragmentListadoProductos();
        pegarFragment(fragmentListadoProductos);


    }

    private void pegarFragment(FragmentListadoProductos fragmentListadoProductos) {


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivityContenedorDeFragment, fragmentListadoProductos);
        fragmentTransaction.commit();
    }
}
