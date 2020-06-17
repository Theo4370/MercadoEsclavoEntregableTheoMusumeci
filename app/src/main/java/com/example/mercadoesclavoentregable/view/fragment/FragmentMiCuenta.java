package com.example.mercadoesclavoentregable.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mercadoesclavoentregable.MainActivity;
import com.example.mercadoesclavoentregable.R;
import com.example.mercadoesclavoentregable.databinding.FragmentMiCuentaBinding;
import com.example.mercadoesclavoentregable.model.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.BigInteger;


public class FragmentMiCuenta extends Fragment {

    private FragmentMiCuentaBinding binding;


    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private FragmentMiCuentaResultListener fragmentMiCuentaResultListener;


    public FragmentMiCuenta() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.fragmentMiCuentaResultListener = (FragmentMiCuentaResultListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMiCuentaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


        Bundle bundle = getArguments();
        UserInfo userInfo = (UserInfo) bundle.getSerializable(MainActivity.USERINFO);


        binding.textViewNombreCompleto.setText(userInfo.getNombreCompleto());
        binding.textViewApodo.setText(userInfo.getApodo());
        binding.textViewEdad.setText(userInfo.getEdad());
        binding.textViewCiudad.setText(userInfo.getCiudad());


       binding.botonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentMiCuentaResultListener.onClickBotonLogOutFirebase();

            }
        });


        return view;
    }



    public interface FragmentMiCuentaResultListener {
        public void onClickBotonLogOutFirebase();


    }
}