package com.example.gebruiker.boardgameapp;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static com.example.gebruiker.boardgameapp.CalendarDialog.DATEPICKER_FRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewEventFragment extends DialogFragment {


    private Button confirm, cancel;

    // dialog values
    public static final int DATEPICKER_FRAGMENT = 1; // class variable
    public static final int TIMEPICKER_FRAGMENT=2; // class variable

    // view values
    private ImageButton setDate, setTime;
    private EditText set_event_title;
    private String eventTitle, eventDate, eventTime;
    private String resultTime, resultDate;

    // firebase connections - database
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseDatabase;

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
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // set title
        set_event_title = view.findViewById(R.id.set_event_title);
        eventTitle = set_event_title.getText().toString();

        // set time and date
        setDate = view.findViewById(R.id.setDate);
        pickDate();
        setTime = view.findViewById(R.id.setTime);
        pickTime();

       // confirm event
        confirm = view.findViewById(R.id.confirmEvent);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEvent();
                System.out.println(eventTitle);
            }
        });

        // dismiss event
        cancel = view.findViewById(R.id.cancelEvent);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    public void pickDate() {
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDialog dialog = new CalendarDialog();

                Bundle date = new Bundle();
                date.putString("Date", eventDate);
                dialog.setArguments(date);

                dialog.setTargetFragment(NewEventFragment.this, DATEPICKER_FRAGMENT);
                dialog.show(getFragmentManager(), "CalendarDialog");
            }

        });
    }


    public void pickTime() {
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeDialog dialog = new TimeDialog();

                Bundle time = new Bundle();
                time.putString("time", eventTime);
                dialog.setArguments(time);

                dialog.setTargetFragment(NewEventFragment.this, TIMEPICKER_FRAGMENT);
                dialog.show(getFragmentManager(), "TimeDialog");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case DATEPICKER_FRAGMENT:
                if(resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    resultDate = bundle.getString("date");
                    System.out.println(resultDate + "FROM NEW EVENT DIALOG");
                }
                break;
            case TIMEPICKER_FRAGMENT:
                if(resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    resultTime = bundle.getString("time");
                    System.out.println(resultTime + "FROM TIME DIALOG");
                }
        }
    }


    public void createEvent() {

        String eventData = eventTitle +" "+ resultTime+" "+ resultDate;
        System.out.println(eventData + "FROM CREATE EVENT");

        mDatabase.child("users")
                .child(firebaseUser.getUid())
                .child("event")
                .setValue(eventData);
    }
}
