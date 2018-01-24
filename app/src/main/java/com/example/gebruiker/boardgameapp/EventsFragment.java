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


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment{

    // get firebase information
    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;

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

        // setting up adapter
        recyclerView = view.findViewById(R.id.eventRv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getUserEvents();

        return view;
    }

    public void getUserEvents() {

        // set the adapter variables



        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get the events of the user
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DataSnapshot userEvent = dataSnapshot.child("users").child(userID).child("event");

                    event = new Event();

                for (DataSnapshot child : userEvent.getChildren()) {

                    nameEvent = child.getValue(String.class);
                    System.out.println(nameEvent + "  name from datasnapshot");
                    System.out.println(child);
                    event.setTitle(nameEvent);
                    events.add(event);
                    System.out.println(events + "ARRAYYYYYYY lmao");
                }

                adapter = new EventAdapter(events, getContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("cancel", "loadPost:onCancelled", databaseError.toException());
            }
        };
        FirebaseDatabase.getInstance().getReference().addValueEventListener(eventListener);


    }
}
