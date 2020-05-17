package com.example.mercadoesclavoentregable.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mercadoesclavoentregable.R;
import com.example.mercadoesclavoentregable.model.Producto;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements FragmentListadoProductos.FragmentListadoProductosListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

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

        FragmentListadoProductos fragmentListadoProductos = new FragmentListadoProductos();
        pegarFragment(fragmentListadoProductos);


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
}
