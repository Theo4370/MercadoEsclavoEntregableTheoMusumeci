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
import com.example.mercadoesclavoentregable.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;



public class FragmentRegister extends Fragment {

    private FragmentRegisterBinding binding;

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
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();



        binding.botonRegisterFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentRegisterListener.onClickBotonFinalizarRegister(binding.editTextMailRegister.getText().toString(), binding.editTextContrasenaRegister.getText().toString());


            }
        });

        return view;
    }



    public interface FragmentRegisterListener {
        public void onClickBotonFinalizarRegister(String mail, String password);


    }
}