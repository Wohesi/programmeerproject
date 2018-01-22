package com.example.gebruiker.boardgameapp;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class LargeBgFragment extends Fragment {

    // values for visuals
    private Button openCalendar;
    private TextView title, year, length_setter, weight_setter, avgRating_setter, numPlayers_setter_max, numPlayers_setter_min, description;
    private ImageView background_img;


    // values for API connection
    private String url;
    private RequestQueue requestQueue;
    private XmlPullParser xpp;
    private String id;
    private String bg_id;
    private String tag = "";
    private String value;


    public LargeBgFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View LargeBgFragment = inflater.inflate(R.layout.activity_large_game, container, false);

        // get the corresponding ID from the bundle
        Bundle arguments = this.getArguments();
        if(arguments != null) {
            bg_id = arguments.getString("id", bg_id);
        }

        url = "https://www.boardgamegeek.com/xmlapi/boardgame/" + bg_id;

        title = LargeBgFragment.findViewById(R.id.title);
        year = LargeBgFragment.findViewById(R.id.year);
        length_setter = LargeBgFragment.findViewById(R.id.length_setter);
        weight_setter = LargeBgFragment.findViewById(R.id.weight_setter);
        avgRating_setter = LargeBgFragment.findViewById(R.id.avg_rating_setter);
        numPlayers_setter_max = LargeBgFragment.findViewById(R.id.num_players_setter_max);
        numPlayers_setter_min = LargeBgFragment.findViewById(R.id.num_players_setter_min);
        background_img = LargeBgFragment.findViewById(R.id.background_img);
        description = LargeBgFragment.findViewById(R.id.description_setter);

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

        loadData();
        return LargeBgFragment;
    }


    public void loadData() {
        final StringRequest stringRequest = new StringRequest(
                url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    xpp.setInput(new StringReader(response));
                    int event = xpp.getEventType();



                    while (event != XmlPullParser.END_DOCUMENT) {
                        tag = xpp.getName();

                        switch (event) {
                            case XmlPullParser.START_TAG:
                                if (tag.equals("boardgame"))
                                    break;
                            case XmlPullParser.TEXT:
                                value = xpp.getText();

                            case XmlPullParser.END_TAG:

                                if (Objects.equals(tag, "yearpublished")) { year.setText(value);}

                                if (Objects.equals(tag, "name")) {title.setText(value);}

                                if (Objects.equals(tag, "playingtime")) {length_setter.setText(value);}

                                if (Objects.equals(tag, "minplayers")) {numPlayers_setter_min.setText(value);}

                                if (Objects.equals(tag, "maxplayers")) {numPlayers_setter_max.setText(value);}

                                if (Objects.equals(tag, "description")) {

                                    description.setText(value);
                                    String text = (String) description.getText();
                                    text = Html.fromHtml(text).toString();
                                    description.setText(text);
                                }

                                if (Objects.equals(tag, "image")) {
                                    if(tag == "") {
                                        background_img.setBackgroundColor(Color.TRANSPARENT);
                                    } else {
                                        Picasso.with(getContext()).load(value).fit().into(background_img);
                                    }
                                }

                                break;
                        }
                        event = xpp.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        requestQueue.add(stringRequest);
    }


    public void makeEvent() {
        openCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewEventFragment dialog = new NewEventFragment();
                dialog.show(getFragmentManager(), "newEventDragment");
            }

        });
    }

}
