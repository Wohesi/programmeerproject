package com.example.gebruiker.boardgameapp;

import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Created by Wout on 18-1-2018.
 */

public class CalendarDialog extends DialogFragment {

    private Button confirmButton, cancelButton;
    private DatePicker datePicker;
    private TextView dateSelected;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_calendar, container, false);

        // finding the views
        confirmButton = view.findViewById(R.id.confirmButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        datePicker = view.findViewById(R.id.datePicker);

        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                            datePicker.getMonth(),
                            datePicker.getDayOfMonth());

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add the date to the calendar.
            }
        });

        return view;
    }
}
