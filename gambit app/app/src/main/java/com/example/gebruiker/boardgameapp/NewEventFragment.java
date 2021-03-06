/**
 * Name: Wout Singerling
 * https://github.com/Wohesi/programmeerproject
 * Student number: 11136324
 */

package com.example.gebruiker.boardgameapp;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



public class NewEventFragment extends DialogFragment implements View.OnClickListener{

    // dialog values
    public static final int DATEPICKER_FRAGMENT = 1; // class variable
    public static final int TIMEPICKER_FRAGMENT=2; // class variable

    // view values
    private EditText setEventTitle;
    private String eventTitle, eventDate, eventTime;
    private String resultTime, resultDate;

    // firebase connections - database
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;

    // firebase connections - user
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    public NewEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_event, container, false);

        // get firebase connection
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        // set title
        setEventTitle = view.findViewById(R.id.set_event_title);


       // set button listeners
        view.findViewById(R.id.confirmEvent).setOnClickListener(NewEventFragment.this);
        view.findViewById(R.id.cancelEvent).setOnClickListener(NewEventFragment.this);

        // set time and date
        view.findViewById(R.id.setTime).setOnClickListener(NewEventFragment.this);
        view.findViewById(R.id.setDate).setOnClickListener(NewEventFragment.this);

        return view;
    }

    // Method to select date from dialogfragment
    public void pickDate() {
        CalendarDialog dialog = new CalendarDialog();

        Bundle date = new Bundle();
        date.putString("Date", eventDate);
        dialog.setArguments(date);

        dialog.setTargetFragment(NewEventFragment.this, DATEPICKER_FRAGMENT);
        dialog.show(getFragmentManager(), "CalendarDialog");
    }

    // method to select time from dialog fagment
    public void pickTime() {
        TimeDialog dialog = new TimeDialog();

        Bundle time = new Bundle();
        time.putString("time", eventTime);
        dialog.setArguments(time);

        dialog.setTargetFragment(NewEventFragment.this, TIMEPICKER_FRAGMENT);
        dialog.show(getFragmentManager(), "TimeDialog");
    }

    // get the corresponding results from the correct dialog fragment.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            // get the date from the calendar dialogfragment
            case DATEPICKER_FRAGMENT:
                if(resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    resultDate = bundle.getString("date");
                }
                break;
            // get the time from time dialogfragment
            case TIMEPICKER_FRAGMENT:
                if(resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    resultTime = bundle.getString("time");
                }
        }
    }

    // method where the correct data will be added to the database.
    public void createEvent(String ID) {

        // add the values to a map
        eventTitle = setEventTitle.getText().toString();
        Map<String, Object> setEvent = new HashMap<String, Object>();
        setEvent.put("title", eventTitle);
        setEvent.put("date", resultDate);
        setEvent.put("time", resultTime);

        // push the event to the database
        database.child("users")
                .child(firebaseUser.getUid())
                .child("event")
                .child(String.valueOf(ID))
                .updateChildren(setEvent);

        // dismiss the dialog and set a message to the user.
        getDialog().dismiss();
        Toast.makeText(getContext(), "Made event: " + eventTitle, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        // confirm the event
        if ( i == R.id.confirmEvent) {
            // create an event with auto increment by using an unique string for each event.
            // this to prevent events with the same time, date and name get overwritten.
            String uniqueID = UUID.randomUUID().toString();
            createEvent(uniqueID);

        }
        // when cancel is clicked close the dialog
        else if (i == R.id.cancelEvent) {
            getDialog().dismiss();
        }
        // open the timepicker
        else if (i == R.id.setTime) {
            pickTime();
        }
        // open the datepicker
        else if (i == R.id.setDate) {
            pickDate();
        }

    }

}
