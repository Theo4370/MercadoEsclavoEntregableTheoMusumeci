package com.example.mercadoesclavoentregable.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mercadoesclavoentregable.R;
import com.example.mercadoesclavoentregable.controller.ProductoController;
import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.ProductoContainer;
import com.example.mercadoesclavoentregable.util.ResultListener;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements FragmentListadoProductos.FragmentListadoProductosListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentListadoProductos fragmentListadoProductos;


    private RecyclerView recyclerView;

    private ProductoController productoController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        navigationView();
        productoController = new ProductoController();
        fragmentListadoProductos = new FragmentListadoProductos();


        pegarProductosAlRecycler();


        //fragmentListadoProductos = new FragmentListadoProductos();


    }

    private void pegarProductosAlRecycler() {

        productoController.getProductoPorSearch(new ResultListener<ProductoContainer>() {
            @Override
            public void onFinish(ProductoContainer result) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("productos", result);

                fragmentListadoProductos.setArguments(bundle);
                pegarFragment(fragmentListadoProductos);

            }
        });
    }

    /**
     * Configuracion de navigationView
     */
    private void navigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuInicio:
                        FragmentListadoProductos fragmentListadoProductos = new FragmentListadoProductos();
                        pegarFragment(fragmentListadoProductos);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menuPerfil:
                        Toast.makeText(MainActivity.this, "Perfil, en construcción", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.menuAboutUs:
                        AboutUsFragment aboutUsFragment = new AboutUsFragment();
                        pegarFragment(aboutUsFragment);
                        drawerLayout.closeDrawers();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "En construccion", Toast.LENGTH_LONG).show();
                        break;
                }

                return true;
            }
        });
    }

    /**
     * Se hacen los find by id de los componentes
     */
    private void findViewById() {
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
    }

    private void pegarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivityContenedorDeFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(Producto producto) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Producto", producto);
        FragmentDetails fragmentDetails = new FragmentDetails();
        fragmentDetails.setArguments(bundle);
        pegarFragment(fragmentDetails);

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }

    }
}
