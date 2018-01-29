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
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CalendarDialog extends DialogFragment implements View.OnClickListener {

    public String dateSelected;

    // set a pointer so the fragments can communicate
    public static final int DATEPICKER_FRAGMENT=1; // adding this line

    private DatePicker datePicker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_calendar, container, false);

        // set onclick listeners
        view.findViewById(R.id.confirmButton).setOnClickListener(CalendarDialog.this);
        view.findViewById(R.id.cancelButton).setOnClickListener(CalendarDialog.this);

        // find the calendar view
        datePicker = view.findViewById(R.id.datePicker);
        return view;
    }

    public void getDate() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear() - 1900;

        // set the format to which the date can be stored
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date(year, month, day);
        dateSelected = dateFormat.format(date);

        // send the date back to NewEventFragment
        Intent i = new Intent();
        i.putExtra("date", dateSelected);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
        getDialog().dismiss();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        // when cancel is clicked close the dialog
        if (i == R.id.cancelButton) {
            getDialog().dismiss();
        }
        // if 'ok' is clicked, confirm the time
        else if (i == R.id.confirmButton) {
            getDate();
            Toast.makeText(getContext(), "You set date to: " + dateSelected, Toast.LENGTH_SHORT).show();
        }

    }
}
