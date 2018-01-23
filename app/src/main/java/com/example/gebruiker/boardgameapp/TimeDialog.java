package com.example.gebruiker.boardgameapp;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimeDialog extends DialogFragment implements View.OnClickListener {

    public String time_selected;

    public static final int TIMEPICKER_FRAGMENT=2;

    private TimePicker timePicker;


    public TimeDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_time, container, false);

        // setting onclick listeners
        view.findViewById(R.id.confirmButton).setOnClickListener(TimeDialog.this);
        view.findViewById(R.id.cancelButton).setOnClickListener(TimeDialog.this);

        // get the timepicker view
        timePicker = view.findViewById(R.id.timePicker);

        // return the view to the inflater
        return view;
    }

    // method to send the correct time back to the NewEventFragment
    public void getTime() {
        int hour = timePicker.getHour();
        int min = timePicker.getMinute();

        time_selected = Integer.toString(hour) + " : " + Integer.toString(min);

        // make new intent with the selected time
        Intent i  = new Intent();
        i.putExtra("time", time_selected);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
        getDialog().dismiss();
    }

    // onclick method
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancelButton ) {
            getDialog().dismiss();
        } else if ( i == R.id.confirmButton) {
            getTime();
        }
    }
}
