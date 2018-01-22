package com.example.gebruiker.boardgameapp;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;

    private Button login, register;
    private EditText userEmail, userPassword;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        // buttons
        register = view.findViewById(R.id.registerButton);
        login = view.findViewById(R.id.loginButton);

        userEmail = view.findViewById(R.id.email);
        userPassword = view.findViewById(R.id.password);

        // login
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        // register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newRegisterFragment();
            }
        });

        return view;
    }

    public void signIn() {
        mAuth = FirebaseAuth.getInstance();
        // Checking if users filled in
        String email = userEmail.getText().toString();
        String password  = userPassword.getText().toString();

        // https://stackoverflow.com/questions/36388581/android-textutils-isempty-vs-string-isempty
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validating users
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Signed in", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("Failed to sign in", "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    public void newRegisterFragment() {

        // making new fragment
        RegisterFragment registerFragment = new RegisterFragment();

        // commiting fragment
        final FragmentTransaction transaction = ((Activity) getContext()).getFragmentManager().beginTransaction();
        transaction.replace(R.id.loginFragment, registerFragment);
        transaction.setTransition(transaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
