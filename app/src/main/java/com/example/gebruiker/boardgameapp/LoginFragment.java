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
public class LoginFragment extends Fragment implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private EditText userEmail, userPassword;

    public View view;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if( user != null) {
            updateUI(user);
        } else {
            view.findViewById(R.id.signoutButton).setVisibility(View.GONE);
        }

        // buttons
        view.findViewById(R.id.registerButton).setOnClickListener( LoginFragment.this);
        view.findViewById(R.id.loginButton).setOnClickListener( LoginFragment.this);
        view.findViewById(R.id.signoutButton).setOnClickListener( LoginFragment.this);

        userEmail = view.findViewById(R.id.email);
        userPassword = view.findViewById(R.id.password);


        return view;
    }

    public void signIn() {

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

                            Toast.makeText(getContext(), "Logged in as: "+ user,
                                    Toast.LENGTH_SHORT).show();

                            updateUI(user);
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

    private void updateUI(FirebaseUser currentUser ) {

        // remove visibility from views if user is logged in.
        if (currentUser != null) {
            // static text views
            view.findViewById(R.id.passwordText).setVisibility(View.GONE);
            view.findViewById(R.id.emailText).setVisibility(View.GONE);

            // edit text views
            view.findViewById(R.id.email).setVisibility(View.GONE);
            view.findViewById(R.id.password).setVisibility(View.GONE);

            // buttons
            view.findViewById(R.id.registerButton).setVisibility(View.GONE);
            view.findViewById(R.id.loginButton).setVisibility(View.GONE);
            view.findViewById(R.id.signoutButton).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.signoutButton).setVisibility(View.GONE);
        }

    }

    // set onclick methods for correct buttons
    @Override
    public void onClick(View v){
        int i = v.getId();
        if (i == R.id.loginButton) {
            signIn();
        } else if ( i == R.id.registerButton) {
            newRegisterFragment();
        } else if(  i == R.id.signoutButton) {
            mAuth.signOut();
        }
    }

}
