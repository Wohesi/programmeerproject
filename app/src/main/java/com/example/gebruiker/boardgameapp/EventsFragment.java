package com.example.gebruiker.boardgameapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment{

    // get firebase information
    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;

    // get views for adapter
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Event event;
    private String nameEvent;
    final ArrayList<Event> events = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        // set firebase refeences
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference();
        user = mAuth.getCurrentUser();

        // setting up adapter
        recyclerView = view.findViewById(R.id.eventRv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (user != null) {
            userID = user.getUid();
            //getUserEvents();
            mDatabaseRef.addValueEventListener(eventListener);
        } else {

        }

        return view;
    }


    private ValueEventListener eventListener = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
                // get the events of the user

            DataSnapshot userEvent = dataSnapshot.child("users").child(userID).child("event");

            event = new Event();
            String key = "";
            String value = "";

            for (DataSnapshot child : userEvent.getChildren()) {

                key = child.getKey();
                value = (String) child.getValue();

                if (Objects.equals(key, "title")) {
                    event.setTitle(value);
                } else if (Objects.equals(key, "date")) {
                    event.setDate(value);
                } else if (Objects.equals(key, "time")) {
                    System.out.println(value + " TEEEESTTTTT");
                    event.setTime(value);
                }
                events.add(event);

            }

            adapter = new EventAdapter(events, getContext());
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w("cancel", "loadPost:onCancelled", databaseError.toException());
        }
    };


}
