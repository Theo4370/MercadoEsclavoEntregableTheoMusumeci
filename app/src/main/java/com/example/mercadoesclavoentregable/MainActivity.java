package com.example.mercadoesclavoentregable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mercadoesclavoentregable.controller.ProductoController;
import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.ProductoContainer;
import com.example.mercadoesclavoentregable.util.ResultListener;
import com.example.mercadoesclavoentregable.view.fragment.AboutUsFragment;
import com.example.mercadoesclavoentregable.view.fragment.FragmentDetails;
import com.example.mercadoesclavoentregable.view.fragment.FragmentListadoProductos;
import com.example.mercadoesclavoentregable.view.fragment.FragmentLogIn;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentListadoProductos.FragmentListadoProductosListener {

    public static final String PRODUCTO = "Producto";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentListadoProductos fragmentListadoProductos;
    private ProductoController productoController;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        navigationView();
        toolBar();
        getAndSetProductosAlRecycler();


    }

    /**
     * Configuracion de toolBar
     */
    private void toolBar() {
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawers, R.string.close_drawers);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    /**
     * Metodo que recibe el pedido de internet y pega los productos al recycler
     */
    private void getAndSetProductosAlRecycler() {
        productoController = new ProductoController();
        fragmentListadoProductos = new FragmentListadoProductos();
        productoController.getProductoPorSearch("Celular", new ResultListener<ProductoContainer>() {
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
     * Configuracion de navigationView BOTONES
     */
    private void navigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuInicio:
                        getAndSetProductosAlRecycler();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menuPerfil:
                        FragmentLogIn fragmentLogIn = new FragmentLogIn();
                        pegarFragment(fragmentLogIn);
                        drawerLayout.closeDrawers();

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
        toolbar = findViewById(R.id.toolBar);
    }

    private void pegarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivityContenedorDeFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Configuracion onClickProducto mostrado en el recycler principal
     */
    @Override
    public void onClick(final Producto producto) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(PRODUCTO, producto);
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

    /**
     * Inflo el appBar (toolBar) y se configura el searchView
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.appBarsearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Buscar producto");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productoController = new ProductoController();
                fragmentListadoProductos = new FragmentListadoProductos();
                productoController.getProductoPorSearch(query, new ResultListener<ProductoContainer>() {
                    @Override
                    public void onFinish(ProductoContainer result) {

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("productos", result);
                        fragmentListadoProductos.setArguments(bundle);
                        pegarFragment(fragmentListadoProductos);

                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });


        return true;
    }

    /**
     * Configuro botones del appBar (toolBar)
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.appBarFavoritosButton:

                //Pasar al fragment favoritos
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
