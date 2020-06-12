package com.example.mercadoesclavoentregable.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mercadoesclavoentregable.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.Executor;


public class FragmentRegister extends Fragment implements Executor {


    private EditText mail;
    private EditText password;
    private EditText apodo;
    private Button botonRegistrarFirebase;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    public FragmentRegister() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();


        db = FirebaseFirestore.getInstance();

        findViewById(view);


        botonRegistrarFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                crearUsuarioFirebase(mail.getText().toString(), password.getText().toString());


            }
        });

        return view;
    }

    private void findViewById(View view) {
        mail = view.findViewById(R.id.editTextMailRegister);
        password = view.findViewById(R.id.editTextContraseñaRegister);
        botonRegistrarFirebase = view.findViewById(R.id.botonRegisterFirebase);
        apodo = view.findViewById(R.id.editTextApodo);
    }

    public void crearUsuarioFirebase(String mail, String password) {

        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Usuario creado con exito",
                                    Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            // updateUIFirebase(user);

                        } else {

                            Toast.makeText(getContext(), "Algo fallo con la autenticacion",
                                    Toast.LENGTH_SHORT).show();
                            updateUIFirebase(null);
                        }

                        // ...
                    }
                });


    }

    private void updateUIFirebase(FirebaseUser currentUser) {
        if (currentUser != null) {
            //Pasar a fragment con datos de cuenta
            Toast.makeText(getContext(), "Se logueo usuario de Firebase", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void execute(Runnable command) {

    }
}