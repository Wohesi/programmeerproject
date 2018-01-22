package com.example.gebruiker.boardgameapp;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewEventFragment extends DialogFragment {

    private ImageButton setDate, setTime;
    private Button confirm, cancel;

    public NewEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_event, container, false);

        // set time and date
        setDate = view.findViewById(R.id.setDate);
        pickDate();
        setTime = view.findViewById(R.id.setTime);
       pickTime();

       // confirm or cancel event
        confirm = view.findViewById(R.id.confirmEvent);
        cancel = view.findViewById(R.id.cancelEvent);
        // dismiss event
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
                dialog.show(getFragmentManager(), "CalendarDialog");
            }

        });
    }

    public void pickTime() {
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeDialog dialog = new TimeDialog();
                dialog.show(getFragmentManager(), "TimeDialog");
            }
        });
    }
}
