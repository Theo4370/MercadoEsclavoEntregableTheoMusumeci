package com.example.mercadoesclavoentregable.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mercadoesclavoentregable.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogIn extends Fragment implements Executor, View.OnClickListener {

    private SignInButton botonRegisterGoogle;
    private Button botonLogOut;
    private Button botonLogInFirebase;
    private GoogleSignInClient client;
    private static final int RC_SIGN_IN_GOOGLE = 1;

    private EditText mail;
    private EditText password;
    private MaterialTextView textoRegistrarse;

    private FragmentLogInListener fragmentLogInListener;


    private FirebaseAuth mAuth;

    public FragmentLogIn() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.fragmentLogInListener = (FragmentLogInListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        mAuth = FirebaseAuth.getInstance();


        findViewById(view);


        botonLogInFirebase.setOnClickListener(this);
        botonRegisterGoogle.setOnClickListener(this);
        botonRegisterGoogle.setSize(SignInButton.SIZE_WIDE);
        textoRegistrarse.setOnClickListener(this);


        //Este boton va a ir en otro lado
        botonLogOut.setOnClickListener(new View.OnClickListener() {
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


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        client = GoogleSignIn.getClient(getContext(), gso);

        return view;
    }








    private void findViewById(View view) {

        botonRegisterGoogle = view.findViewById(R.id.botonRegisterGoogle);
        botonLogOut = view.findViewById(R.id.botonLogOut);
        botonLogInFirebase = view.findViewById(R.id.botonLogIn);
        mail = view.findViewById(R.id.editTextMail);
        password = view.findViewById(R.id.editTextContraseña);
        textoRegistrarse = view.findViewById(R.id.textViewRegistrarse);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RC_SIGN_IN_GOOGLE:

                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);

                break;
        }


    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(getContext(), "Login correcto", Toast.LENGTH_SHORT).show();
            //updateUIGoogle(account);
        } catch (ApiException e) {
            Log.w("GOOGLE", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getContext(), "Error inesperado", Toast.LENGTH_SHORT).show();
            updateUIGoogle(null);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        updateUIGoogle(account);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUIFirebase(currentUser);
    }

    public void updateUIGoogle(GoogleSignInAccount account) {
        if (account != null) {
            //Pasar a fragment con datos de cuenta
            Toast.makeText(getContext(), "Se logueo usuario de google", Toast.LENGTH_SHORT).show();

        }

    }

    private void updateUIFirebase(FirebaseUser currentUser) {
        if (currentUser != null) {
            //Pasar a fragment con datos de cuenta
            Toast.makeText(getContext(), "Se logueo usuario de Firebase", Toast.LENGTH_SHORT).show();
        }
    }

    public void crearUsuarioFirebase(String mail, String password) {

        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUIFirebase(user);

                        } else {

                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUIFirebase(null);
                        }

                        // ...
                    }
                });


    }

    private void singInFirebase(String mail, String password) {


        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "LogIn exitoso", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                           // updateUIFirebase(user);
                        } else {
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUIFirebase(null);

                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.botonLogIn:
                singInFirebase(mail.getText().toString(), password.getText().toString());

            case R.id.botonRegisterGoogle:
                Intent signInIntent = client.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);

            case R.id.textViewRegistrarse:
                fragmentLogInListener.onClickRegistrar();


        }
    }

    public interface FragmentLogInListener {
        public void onClickRegistrar();

    }


    //El fragment tiene que impletmentar Executor para que funcione el crearUsuarioFirebase
    @Override
    public void execute(Runnable command) {

    }
}
