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


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentListadoProductos fragmentListadoProductos;
    private FragmentFavoritos fragmentFavoritos;
    private ProductoController productoController;
    private Toolbar toolbar;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private GoogleSignInClient client;
    public static final int RC_SIGN_IN_GOOGLE = 1;


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
        productoController.getProductoPorSearch("Planta", new ResultListener<ProductoContainer>() {
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

                            getAndSetFragmentMiCuentaConUserInfo();

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
    public void onClick(Producto producto) {

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
                                    Toast.makeText(MainActivity.this, "Algo salio mal al cargar tus datos", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Algo salio mal con el import", Toast.LENGTH_SHORT).show();
                    }
                });


                /*  productoController = new ProductoController();
                fragmentFavoritos = new FragmentFavoritos();
                productoController.getProductoPorSearch("Planta", new ResultListener<ProductoContainer>() {
                    @Override
                    public void onFinish(ProductoContainer result) {

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("productos", result);

                        fragmentFavoritos.setArguments(bundle);
                        pegarFragment(fragmentFavoritos);

                    }

                });
*/
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

            getAndSetFragmentMiCuentaConUserInfo();

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

//        getUserInfoDesdeFirestore(firebaseUser);
        updateUIFirebase(firebaseUser);
        //Pasar a fragment mi cuenta
        //updateUI


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

        nombreUsuarioNavigationView.setText("MercadoEsclavo");


    }


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


    //Configuracion google

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
}
