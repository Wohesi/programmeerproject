package com.example.gebruiker.boardgameapp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * A simple {@link Fragment} subclass.
 */
public class LargeBgFragment extends Fragment {

    // values for visuals
    private Button openCalendar;
    private TextView title, year, length_setter, weight_setter, avgRating_setter, numPlayers_setter;
    private ImageView background_img;


    // values for API connection
    private String url;
    private RequestQueue requestQueue;
    private XmlPullParser xpp;
    private String id;


    public LargeBgFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View LargeBgFragment = inflater.inflate(R.layout.bg_large, container, false);

        title = LargeBgFragment.findViewById(R.id.title);
        year = LargeBgFragment.findViewById(R.id.year);
        length_setter = LargeBgFragment.findViewById(R.id.length_setter);
        weight_setter = LargeBgFragment.findViewById(R.id.weight_setter);
        avgRating_setter = LargeBgFragment.findViewById(R.id.avg_rating_setter);
        numPlayers_setter = LargeBgFragment.findViewById(R.id.num_players_setter);
        background_img = LargeBgFragment.findViewById(R.id.background_img);


        // get the calendar dialog
        openCalendar = LargeBgFragment.findViewById(R.id.openCalendar);
        makeEvent();

        requestQueue = Volley.newRequestQueue(getContext());

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
        } catch (XmlPullParserException e ) {
            e.printStackTrace();
        }

        //loadData();
        return LargeBgFragment;
    }
    /*
    public void loadData() {
        final StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    xpp.setInput(new StringReader(response));
                    int event = xpp.getEventType();

                    String tag = "", value = "";

                    while(event != XmlPullParser.END_DOCUMENT) {
                        tag = xpp.getName();

                        switch (event) {
                            case XmlPullParser.START_DOCUMENT:
                                if(tag.equals(""))
                                break;
                            case XmlPullParser.TEXT:
                                value = xpp.getText();

                            case XmlPullParser.END_TAG:

                                switch (tag) {
                                    case "":
                                        break;
                                    case "":
                                        break;
                                    // more depending on api respnse
                                }
                        }

                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            }
        });

        requestQueue.add(stringRequest);
    }

    */

    public void makeEvent() {
        openCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CalendarDialog dialog = new CalendarDialog();
                dialog.show(getFragmentManager(), "CalendarDialog");

            }

        });
    }

}
