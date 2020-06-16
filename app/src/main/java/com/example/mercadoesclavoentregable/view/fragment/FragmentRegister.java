package com.example.mercadoesclavoentregable.view.fragment;

import android.content.Context;
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


public class FragmentRegister extends Fragment {


    private EditText mail;
    private EditText password;

    private Button botonRegistrarFirebase;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private FragmentRegisterListener fragmentRegisterListener;


    public FragmentRegister() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.fragmentRegisterListener = (FragmentRegisterListener) context;
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

                fragmentRegisterListener.onClickBotonFinalizarRegister(mail.getText().toString(), password.getText().toString());



            }
        });

        return view;
    }

    private void findViewById(View view) {
        mail = view.findViewById(R.id.editTextMailRegister);
        password = view.findViewById(R.id.editTextContraseñaRegister);
        botonRegistrarFirebase = view.findViewById(R.id.botonRegisterFirebase);

    }


    public interface FragmentRegisterListener {
        public void onClickBotonFinalizarRegister(String mail, String password);


    }
}