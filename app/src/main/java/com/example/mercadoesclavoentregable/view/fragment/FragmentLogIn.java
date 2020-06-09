package com.example.mercadoesclavoentregable.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mercadoesclavoentregable.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogIn extends Fragment {

    private SignInButton botonRegisterGoogle;
    private Button botonRegister;
    private Button botonLogOut;
    private GoogleSignInClient client;
    private static final int RC_SIGN_IN_GOOGLE = 1;

    public FragmentLogIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        botonRegisterGoogle = view.findViewById(R.id.botonRegisterGoogle);
        botonRegister = view.findViewById(R.id.botonRegister);
        botonLogOut = view.findViewById(R.id.botonLogOut);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        client = GoogleSignIn.getClient(getContext(), gso);


        botonRegisterGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = client.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
            }
        });

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


        return view;
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
            updateUI(account);
        } catch (ApiException e) {
            Log.w("GOOGLE", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getContext(), "Error inesperado", Toast.LENGTH_SHORT).show();
            updateUI(null);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        updateUI(account);
    }

    public void updateUI(GoogleSignInAccount account) {
        if (account != null) {
//Pasar a fragment con datos de cuenta

        }

    }

}
