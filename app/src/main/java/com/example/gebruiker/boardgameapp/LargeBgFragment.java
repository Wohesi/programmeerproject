package com.example.gebruiker.boardgameapp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LargeBgFragment extends Fragment {

    private Button openCalendar;

    public LargeBgFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View LargeBgFragment = inflater.inflate(R.layout.bg_large, container, false);

        openCalendar = LargeBgFragment.findViewById(R.id.openCalendar);

        openCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CalendarDialog dialog = new CalendarDialog();
                dialog.show(getFragmentManager(), "CalendarDialog");

            }
        });



        return LargeBgFragment;
    }

}
