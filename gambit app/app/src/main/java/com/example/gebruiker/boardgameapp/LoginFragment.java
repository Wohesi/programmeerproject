/**
 * Name: Wout Singerling
 * https://github.com/Wohesi/programmeerproject
 * Student number: 11136324
 */

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment implements View.OnClickListener{

    private FirebaseAuth auth;

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

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        // check if the user is logged in and update the UI
        loginCheck(user);

        // buttons
        view.findViewById(R.id.registerButton).setOnClickListener( LoginFragment.this);
        view.findViewById(R.id.loginButton).setOnClickListener( LoginFragment.this);
        view.findViewById(R.id.signoutButton).setOnClickListener( LoginFragment.this);

        // email and password
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
        validateUser(email, password);

    }

    public void validateUser(String email, String password) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Signed in", "signInWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(getContext(), "Logged in as: "+ user.getEmail(), Toast.LENGTH_SHORT).show();
                            // update the login fragment with the right UI
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("Failed to sign in", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Failed to login.", Toast.LENGTH_LONG).show();
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

    // update the UI after a user is logged in
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

            TextView login = view.findViewById(R.id.loginText);
            login.setText("You are logged in!");
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
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getContext(), "Signed out", Toast.LENGTH_SHORT).show();
            getActivity().recreate();
        }
    }

    public void loginCheck(FirebaseUser user) {
        if( user != null) {
            updateUI(user);
        } else {
            view.findViewById(R.id.signoutButton).setVisibility(View.GONE);
        }
    }

}
