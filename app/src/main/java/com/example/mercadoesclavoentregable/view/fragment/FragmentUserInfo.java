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
import com.example.mercadoesclavoentregable.databinding.FragmentUserInfoBinding;
import com.example.mercadoesclavoentregable.model.Producto;
import com.example.mercadoesclavoentregable.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class FragmentUserInfo extends Fragment {


    private FragmentUserInfoBinding binding;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    public static final String USERINFO = "userInfo";


    private FragmentUserInfoListener fragmentUserInfoListener;

    public FragmentUserInfo() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.fragmentUserInfoListener = (FragmentUserInfoListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();


        binding.botonFirebaseFirestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editTextNombreCompleto.getText() != null
                        && binding.editTextApodo.getText() != null
                        && binding.editTextEdad.getText() != null
                        && binding.editTextCiudad.getText() != null) {

                    UserInfo userInfo = new UserInfo(binding.editTextNombreCompleto.getText().toString(),
                            binding.editTextApodo.getText().toString(),
                            binding.editTextEdad.getText().toString(),
                            binding.editTextCiudad.getText().toString(), null);
                    agregarUserInfoAFirestone(userInfo);

                    fragmentUserInfoListener.onClickFinalizarUserInfo();
                } else {}
                Toast.makeText(getActivity(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
            }
        });


        return view;


    }

    public void agregarUserInfoAFirestone(UserInfo userInfo) {

        db.collection(USERINFO)
                .document(firebaseUser.getUid())
                .set(userInfo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Datos actualizados", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error en la base de datos", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }


    public interface FragmentUserInfoListener {
        public void onClickFinalizarUserInfo();

    }

}