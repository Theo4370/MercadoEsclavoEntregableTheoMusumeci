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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mercadoesclavoentregable.controller.ProductoController;
import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.ProductoContainer;
import com.example.mercadoesclavoentregable.model.UserInfo;
import com.example.mercadoesclavoentregable.util.ResultListener;
import com.example.mercadoesclavoentregable.view.fragment.AboutUsFragment;
import com.example.mercadoesclavoentregable.view.fragment.FragmentDetails;
import com.example.mercadoesclavoentregable.view.fragment.FragmentListadoProductos;
import com.example.mercadoesclavoentregable.view.fragment.FragmentLogIn;
import com.example.mercadoesclavoentregable.view.fragment.FragmentMiCuenta;
import com.example.mercadoesclavoentregable.view.fragment.FragmentRegister;
import com.example.mercadoesclavoentregable.view.fragment.FragmentUserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements FragmentListadoProductos.FragmentListadoProductosListener, FragmentLogIn.FragmentLogInListener, FragmentRegister.FragmentRegisterListener, FragmentUserInfo.FragmentUserInfoListener, FragmentMiCuenta.FragmentMiCuentaResultListener {

    public static final String PRODUCTO = "Producto";
    public static final String USERINFO = "userInfo";

    private TextView nombreUsuarioNavigationView;


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentListadoProductos fragmentListadoProductos;
    private ProductoController productoController;
    private Toolbar toolbar;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();


        findViewById();
        navigationView();
        toolBar();
        getAndSetProductosAlRecycler();

        //BOTON LOGOUT GOOGLE
        /* botonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(getContext(), "LogOutOnSuccess", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
*/

    }

    /**
     * Configuracion de toolBar
     */
    private void toolBar() {
        toolbar.setTitle("Inicio");
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
        productoController.getProductoPorSearch("Vino", new ResultListener<ProductoContainer>() {
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
                        if (mAuth.getCurrentUser() != null) {
                            /*FragmentMiCuenta fragmentMiCuenta = new FragmentMiCuenta();
                            pegarFragment(fragmentMiCuenta);
                            */
                            pegarFragmentMiCuentaConUserInfo();

                        } else {
                            FragmentLogIn fragmentLogIn = new FragmentLogIn();
                            pegarFragment(fragmentLogIn);

                        }

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


    private void singInFirebase(String mail, String password) {


        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            firebaseUser = mAuth.getCurrentUser();

                            updateUIFirebase(firebaseUser);
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUIFirebase(null);

                        }
                    }
                });

    }

    private void updateUIFirebase(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {

            //Pasar a fragment con datos de cuenta

            Toast.makeText(MainActivity.this, "Se logueo usuario de Firebase", Toast.LENGTH_SHORT).show();

            pegarFragmentMiCuentaConUserInfo();

        } else {

            Toast.makeText(MainActivity.this, "updateUIFirebase null", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onClickBotonCrearCuenta() {
        pegarFragment(new FragmentRegister());
    }

    @Override
    public void onClickSingInFirebase(String mail, String password) {
        singInFirebase(mail, password);

    }


    @Override
    public void onClickFinalizarUserInfo() {
        getAndSetProductosAlRecycler();
        drawerLayout.closeDrawers();

        firebaseUser = mAuth.getCurrentUser();

        getUserInfoDesdeFirestore(firebaseUser);

        //Pasar a fragment mi cuenta
        //updateUI


    }

    public void getUserInfoDesdeFirestore(FirebaseUser firebaseUser) {

        db.collection(USERINFO)
                .document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserInfo userInfo = documentSnapshot.toObject(UserInfo.class);

                        String apodo = userInfo.getApodo();
                        nombreUsuarioNavigationView = findViewById(R.id.navigationHeadNombreUsuario);
                        nombreUsuarioNavigationView.setText(apodo);

                        pegarFragmentMiCuentaConUserInfo();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Algo salio mal con el import", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClickBotonFinalizarRegister(String mail, String password) {
        crearUsuarioFirebase(mail, password);

    }

    public void crearUsuarioFirebase(String mail, String password) {

        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Usuario creado con exito",
                                    Toast.LENGTH_SHORT).show();

                            FirebaseUser currentUser = mAuth.getCurrentUser();

                            pegarFragment(new FragmentUserInfo());
                            //updateUIFirebase(currentUser);

                        } else {

                            Toast.makeText(MainActivity.this, "Algo fallo con la autenticacion",
                                    Toast.LENGTH_SHORT).show();
                            // updateUIFirebase(null);
                        }

                    }
                });

    }


    @Override
    public void onClickBotonLogOutFirebase() {

        mAuth.signOut();

        //updateUIFirebase(null);
        FragmentLogIn fragmentLogIn = new FragmentLogIn();
        pegarFragment(fragmentLogIn);
        drawerLayout.closeDrawers();


    }


    public void pegarFragmentMiCuentaConUserInfo() {


        db.collection(USERINFO)
                .document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        UserInfo userInfo = documentSnapshot.toObject(UserInfo.class);


                        Bundle bundle = new Bundle();
                        bundle.putSerializable(USERINFO, userInfo);

                        FragmentMiCuenta fragmentMiCuenta = new FragmentMiCuenta();
                        fragmentMiCuenta.setArguments(bundle);
                        pegarFragment(fragmentMiCuenta);


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Algo salio mal con el import", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
