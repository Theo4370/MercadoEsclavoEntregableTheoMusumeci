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
import com.example.mercadoesclavoentregable.databinding.FragmentLogInBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogIn extends Fragment implements View.OnClickListener {


    private FragmentLogInBinding binding;

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
        binding = FragmentLogInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();




        binding.botonLogIn.setOnClickListener(this);
        binding.botonRegisterGoogle.setOnClickListener(this);
        binding.botonRegisterGoogle.setSize(SignInButton.SIZE_WIDE);
        binding.textViewRegistrarse.setOnClickListener(this);


        return view;
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.botonLogIn:
                if(binding.editTextMail.getText() != null && binding.editTextContrasena.getText() != null) {

                    fragmentLogInListener.onClickSingInFirebase(binding.editTextMail.getText().toString(), binding.editTextContrasena.getText().toString());

                } else {
                    Toast.makeText(getActivity(), "No deje campos vacios", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.botonRegisterGoogle:

                fragmentLogInListener.onClickSingInGoogle();

                break;

            case R.id.textViewRegistrarse:

                fragmentLogInListener.onClickBotonCrearCuenta();

                break;
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        //updateUIGoogle(account);

        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUIFirebase(currentUser);
    }




    public interface FragmentLogInListener {
        public void onClickBotonCrearCuenta();

        public void onClickSingInFirebase(String mail, String password);

        public void onClickSingInGoogle();

    }


}
