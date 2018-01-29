package com.example.gebruiker.boardgameapp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlannerFragment extends Fragment {


    public PlannerFragment() {
        // Required empty public constructor
    }

    ImageButton plannerButton;
    int year_x, month_x, day_x;
    static final int DILOG_id = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bg_large, container, false);
    }



}
