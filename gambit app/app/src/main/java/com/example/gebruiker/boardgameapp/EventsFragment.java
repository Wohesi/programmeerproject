/**
 * Name: Wout Singerling
 * https://github.com/Wohesi/programmeerproject
 * Student number: 11136324
 */

package com.example.gebruiker.boardgameapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EventsFragment extends Fragment {

    // get firebase information
    private DatabaseReference databaseRef;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth auth;
    private String userID;


    // get views for adapter
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    final ArrayList<Event> events = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        // set firebase refeences
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseRef = firebaseDatabase.getReference();
        auth.addAuthStateListener(mAuthListener);

        // setting up adapter
        recyclerView = view.findViewById(R.id.eventRv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new EventAdapter(events, getContext());

        // update if the user is logged in and get the events of the logged in user.
        updateUI();

        return view;
    }

    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            if (firebaseAuth.getCurrentUser() != null) {
                userID = firebaseAuth.getCurrentUser().getUid();
                databaseRef.addValueEventListener(eventListener);
                adapter.notifyDataSetChanged();
            } else {
                databaseRef.removeEventListener(eventListener);
            }
        }
    };

    // eventlistener for the database
    private ValueEventListener eventListener = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            // get the events of the user
            DataSnapshot userEvent = dataSnapshot.child("users").child(userID).child("event");

            // set the values for the events in the adapter for the recycler view
            String key;
            String value;
            Event event;

             // for loop for each event
            for (DataSnapshot child : userEvent.getChildren()) {

                event = new Event();

                // for loop to get the values of each event
                for (DataSnapshot c : child.getChildren() ){

                    // get the key and value
                    key = c.getKey();
                    value = (String) c.getValue();

                    // using cases for each key in the database
                    switch (key) {
                        case "time":
                            event.setTime(value);
                            break;

                        case "title":
                            event.setTitle(value);
                            break;

                        case "date":
                            event.setDate(value);
                            break;
                    }
                }

                // add the events to a list
                events.add(event);

            }
            // set the adapter
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w("cancel", "loadPost:onCancelled", databaseError.toException());
        }
    };

    public void updateUI() {
        if (auth.getCurrentUser() != null) {
            userID = auth.getCurrentUser().getUid();
            recyclerView.setAdapter(adapter);
        }
    }
}
