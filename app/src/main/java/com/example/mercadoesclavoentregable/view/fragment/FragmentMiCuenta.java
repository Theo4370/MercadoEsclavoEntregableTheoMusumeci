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
import com.example.mercadoesclavoentregable.model.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class FragmentMiCuenta extends Fragment {

    private TextView nombreCompleto;
    private TextView apodo;
    private TextView edad;
    private TextView ciudad;
    private Button botonLogOut;


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
        View view = inflater.inflate(R.layout.fragment_mi_cuenta, container, false);


        findViewById(view);


        Bundle bundle = getArguments();
        UserInfo userInfo = (UserInfo) bundle.getSerializable(MainActivity.USERINFO);


        nombreCompleto.setText(userInfo.getNombreCompleto());
        apodo.setText(userInfo.getApodo());
        edad.setText(userInfo.getEdad());
        ciudad.setText(userInfo.getCiudad());


        botonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentMiCuentaResultListener.onClickBotonLogOutFirebase();

            }
        });


        return view;
    }

    private void findViewById(View view) {
        nombreCompleto = view.findViewById(R.id.textViewNombreCompleto);
        apodo = view.findViewById(R.id.textViewApodo);
        edad = view.findViewById(R.id.textViewEdad);
        ciudad = view.findViewById(R.id.textViewCiudad);
        botonLogOut = view.findViewById(R.id.botonLogOut);
    }


    public interface FragmentMiCuentaResultListener {
        public void onClickBotonLogOutFirebase();


    }
}