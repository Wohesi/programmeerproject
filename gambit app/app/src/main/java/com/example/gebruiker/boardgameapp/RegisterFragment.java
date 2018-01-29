/**
 * Name: Wout Singerling
 * https://github.com/Wohesi/programmeerproject
 * Student number: 11136324
 */

package com.example.gebruiker.boardgameapp;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




public class RegisterFragment extends Fragment implements View.OnClickListener {

    // Firebase connection
    private FirebaseAuth mAuth;

    // Set parameters
    private EditText newUserEmail;
    private EditText newUserPass;

    private RegisterFragment context;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // firebase authentication
        mAuth = FirebaseAuth.getInstance();

        // get the email and username
        newUserEmail = view.findViewById(R.id.registerEmail);
        newUserPass = view.findViewById(R.id.registerPassword);

        // set onclick listener
        view.findViewById(R.id.registerButton).setOnClickListener(RegisterFragment.this);

        return view;

    }


    // set the email and password for the new user.
    public void createAccount() {
        // Checking if correct info is filled in
        String email = newUserEmail.getText().toString();
        String password = newUserPass.getText().toString();

        // check if the email field is empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Please enter an email", Toast.LENGTH_LONG).show();
            return;
        }

        // checks if the password field is empty
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Please enter a password", Toast.LENGTH_LONG).show();
            return;
        }

        // Firebase passwords need to have a minimum of 6 characters
        // https://stackoverflow.com/questions/36318198/set-minimum-password-length-firebase-email-password-authentication
        if (password.length() < 6) {
            Toast.makeText(getContext(), "password need minumum 6 chars", Toast.LENGTH_LONG).show();
            return;
        }

        // set the validation for the user and password.
        validateCreateAccount(email, password);
    }

    // register the new user.
    public void validateCreateAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("User created", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "You registered as: "+ user, Toast.LENGTH_LONG).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("failed to create user", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    // onclick listener method
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.registerButton) {
            createAccount();
        }
    }
}
