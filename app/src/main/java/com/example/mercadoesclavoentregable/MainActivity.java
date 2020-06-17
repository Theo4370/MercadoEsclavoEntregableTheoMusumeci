package com.example.mercadoesclavoentregable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mercadoesclavoentregable.controller.ProductoController;
import com.example.mercadoesclavoentregable.databinding.ActivityMainBinding;
import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.ProductoContainer;
import com.example.mercadoesclavoentregable.model.UserInfo;
import com.example.mercadoesclavoentregable.util.ResultListener;
import com.example.mercadoesclavoentregable.view.fragment.AboutUsFragment;
import com.example.mercadoesclavoentregable.view.fragment.FragmentDetails;
import com.example.mercadoesclavoentregable.view.fragment.FragmentFavoritos;
import com.example.mercadoesclavoentregable.view.fragment.FragmentListadoProductos;
import com.example.mercadoesclavoentregable.view.fragment.FragmentLogIn;
import com.example.mercadoesclavoentregable.view.fragment.FragmentMiCuenta;
import com.example.mercadoesclavoentregable.view.fragment.FragmentRegister;
import com.example.mercadoesclavoentregable.view.fragment.FragmentUserInfo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity implements FragmentListadoProductos.FragmentListadoProductosListener, FragmentLogIn.FragmentLogInListener, FragmentRegister.FragmentRegisterListener, FragmentUserInfo.FragmentUserInfoListener, FragmentMiCuenta.FragmentMiCuentaResultListener, FragmentDetails.FragmentDetailsListener, FragmentFavoritos.FragmentFavoritosListener {

    public static final String PRODUCTO = "Producto";
    public static final String USERINFO = "userInfo";
    public static final String FAVORITOS_IDS = "favoritosIds";

    private TextView nombreUsuarioNavigationView;


    private FragmentListadoProductos fragmentListadoProductos;
    private FragmentFavoritos fragmentFavoritos;
    private ProductoController productoController;


    private ActivityMainBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private GoogleSignInClient client;
    public static final int RC_SIGN_IN_GOOGLE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();


        navigationView();
        toolBar();
        getAndSetProductosAlRecycler();


    }

    /**
     * Configuracion de toolBar
     */
    private void toolBar() {
        binding.toolBar.setTitle("Inicio");
        setSupportActionBar(binding.toolBar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolBar, R.string.open_drawers, R.string.close_drawers);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    /**
     * Metodo que recibe el pedido de internet y pega los productos al recycler
     */
    private void getAndSetProductosAlRecycler() {
        productoController = new ProductoController();
        fragmentListadoProductos = new FragmentListadoProductos();
        productoController.getProductoPorSearchPaginado("Planta", new ResultListener<ProductoContainer>() {
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
        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuInicio:
                        getAndSetProductosAlRecycler();
                        binding.drawerLayout.closeDrawers();
                        break;
                    case R.id.menuPerfil:
                        if (mAuth.getCurrentUser() != null) {

                            getAndSetFragmentMiCuentaConUserInfo();

                        } else {
                            FragmentLogIn fragmentLogIn = new FragmentLogIn();
                            pegarFragment(fragmentLogIn);

                        }

                        binding.drawerLayout.closeDrawers();

                        break;
                    case R.id.menuAboutUs:
                        AboutUsFragment aboutUsFragment = new AboutUsFragment();
                        pegarFragment(aboutUsFragment);
                        binding.drawerLayout.closeDrawers();


                        break;
                    default:
                        Toast.makeText(MainActivity.this, "En construccion", Toast.LENGTH_LONG).show();
                        break;
                }

                return true;
            }
        });
    }


    private void pegarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivityContenedorDeFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
                productoController.getProductoPorSearchPaginado(query, new ResultListener<ProductoContainer>() {
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

                fragmentFavoritos = new FragmentFavoritos();
                db.collection(USERINFO)
                        .document(mAuth.getCurrentUser().getUid())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                UserInfo userInfo = documentSnapshot.toObject(UserInfo.class);

                                if (userInfo != null) {

                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(USERINFO, userInfo);

                                    fragmentFavoritos.setArguments(bundle);
                                    pegarFragment(fragmentFavoritos);


                                } else {
                                    Toast.makeText(MainActivity.this, "Algo salio mal al cargar tus favoritos", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Algo salio mal con el import de favoritos", Toast.LENGTH_SHORT).show();
                    }
                });


                break;

        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Interfaces
     * */

    /**
     * Configuracion onClickProducto de ProductoAdapter (se usa en FragmentListadoProducto y en FragmentFavoritos)
     */
    @Override
    public void onClick(Producto producto) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(PRODUCTO, producto);
        FragmentDetails fragmentDetails = new FragmentDetails();
        fragmentDetails.setArguments(bundle);
        pegarFragment(fragmentDetails);

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

        binding.drawerLayout.closeDrawers();

        firebaseUser = mAuth.getCurrentUser();

        updateUIFirebase(firebaseUser);

    }

    @Override
    public void onClickBotonFinalizarRegister(String mail, String password) {
        crearUsuarioFirebase(mail, password);

    }

    @Override
    public void onClickBotonLogOutFirebase() {

        mAuth.signOut();

        //updateUIFirebase(null);
        FragmentLogIn fragmentLogIn = new FragmentLogIn();
        pegarFragment(fragmentLogIn);
        binding.drawerLayout.closeDrawers();

        nombreUsuarioNavigationView.setText("MercadoEsclavo");


    }

    @Override
    public void onClickAgregarFavoritos(String productoId) {
        db.collection(MainActivity.USERINFO)
                .document(firebaseUser.getUid())
                .update(FAVORITOS_IDS, FieldValue.arrayUnion(productoId))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Agregado a favoritos", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Salio mal agregar favorito", Toast.LENGTH_SHORT).show();
            }
        });


    }


    /**
     * Metodos de firebase
     * EL METODO PARA AGREGAR INFO ESTA EN FRAGMENTUSERINFO
     */
    public void getAndSetFragmentMiCuentaConUserInfo() {

        db.collection(USERINFO)
                .document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        UserInfo userInfo = documentSnapshot.toObject(UserInfo.class);

                        if (userInfo != null) {
                            String apodo = userInfo.getApodo();
                            nombreUsuarioNavigationView = findViewById(R.id.navigationHeadNombreUsuario);
                            nombreUsuarioNavigationView.setText(apodo);


                            Bundle bundle = new Bundle();
                            bundle.putSerializable(USERINFO, userInfo);

                            FragmentMiCuenta fragmentMiCuenta = new FragmentMiCuenta();
                            fragmentMiCuenta.setArguments(bundle);
                            pegarFragment(fragmentMiCuenta);
                        } else {
                            Toast.makeText(MainActivity.this, "Algo salio mal al cargar tus datos", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Algo salio mal con el import", Toast.LENGTH_SHORT).show();
            }
        });


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

            Toast.makeText(MainActivity.this, "Se logueo usuario de Firebase", Toast.LENGTH_SHORT).show();

            getAndSetFragmentMiCuentaConUserInfo();

        } else {

            Toast.makeText(MainActivity.this, "updateUIFirebase null", Toast.LENGTH_SHORT).show();

        }
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


    /**
     * Configuracion google
     */

    @Override
    public void onClickSingInGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        client = GoogleSignIn.getClient(MainActivity.this, gso);


        Intent signInIntent = client.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_SIGN_IN_GOOGLE:

                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                try {

                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d("GOOGLEFB", "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());

                    //updateUIGoogle(account);
                } catch (ApiException e) {
                    Log.w("GOOGLE", "signInResult:failed code=" + e.getStatusCode());
                    Toast.makeText(MainActivity.this, "Error inesperado", Toast.LENGTH_SHORT).show();
                    updateUIGoogle(null);
                }


                break;
        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            firebaseUser = mAuth.getCurrentUser();

                            GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(MainActivity.this);


                            UserInfo userInfo = new UserInfo(googleAccount.getDisplayName(), googleAccount.getFamilyName(), googleAccount.getGivenName(), googleAccount.getEmail(), null);

                            db.collection(USERINFO)
                                    .document(firebaseUser.getUid())
                                    .set(userInfo)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(MainActivity.this, "Todo joya", Toast.LENGTH_SHORT).show();
                                            getAndSetFragmentMiCuentaConUserInfo();

                                        }
                                    });

                            updateUIGoogle(firebaseUser);
                        } else {
                            Toast.makeText(MainActivity.this, "Error inesperado de google", Toast.LENGTH_SHORT).show();
                            // If sign in fails, display a message to the user.
                            //Log.w("Google", "signInWithCredential:failure", task.getException());
                            // Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void updateUIGoogle(FirebaseUser account) {
        if (account != null) {
            //Pasar a fragment con datos de cuenta
            Toast.makeText(MainActivity.this, "Se logueo usuario de google", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onClickAbrirMaps(Producto producto) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.PRODUCTO, producto);


        intent.putExtras(bundle);
        startActivity(intent);
    }


}
