package com.example.gebruiker.boardgameapp;


import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class LargeBgFragment extends Fragment {

    // values for visuals
    private ImageButton openCalendar;
    private TextView title, year, age_setter, minPlaytime_setter, maxPlaytime_setter, maxPlayers_setter, minPlayers_setter, description;
    private ImageView backgroundImg;


    // values for API connection
    private String url;
    private RequestQueue requestQueue;
    private XmlPullParser xpp;
    private String bg_id;
    private String tag = "";
    private String value;


    public LargeBgFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View LargeBgFragment = inflater.inflate(R.layout.fragment_large_game, container, false);

        // get the corresponding ID from the bundle
        Bundle arguments = this.getArguments();
        if(arguments != null) {
            bg_id = arguments.getString("id", bg_id);
        }

        // get the correct ID with the corresponding API url
        url = "https://www.boardgamegeek.com/xmlapi/boardgame/" + bg_id;

        // set the views
        title = LargeBgFragment.findViewById(R.id.title);
        year = LargeBgFragment.findViewById(R.id.year);

        maxPlayers_setter = LargeBgFragment.findViewById(R.id.num_players_setter_max);
        minPlayers_setter = LargeBgFragment.findViewById(R.id.num_players_setter_min);

        age_setter = LargeBgFragment.findViewById(R.id.age_setter);

        backgroundImg = LargeBgFragment.findViewById(R.id.background_img);
        description = LargeBgFragment.findViewById(R.id.description_setter);

        minPlaytime_setter = LargeBgFragment.findViewById(R.id.minPlaytime_setter);
        maxPlaytime_setter = LargeBgFragment.findViewById(R.id.maxPlaytime_setter);

        // get the calendar dialog
        openCalendar = LargeBgFragment.findViewById(R.id.openCalendar);
        makeEvent();

        // get API connection
        requestQueue = Volley.newRequestQueue(getContext());

        // get XML parser conection
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

        System.out.println(url);

        final StringRequest stringRequest = new StringRequest(
                url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    xpp.setInput(new StringReader(response));
                    int event = xpp.getEventType();
                    ArrayList<String> names = new ArrayList<>();

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

                                if (Objects.equals(tag, "name")) {

                                    names.add(value);

                                    //System.out.println(names.e);
                                    title.setText(value);
                                }
                                //System.out.println(names);
                                for(int i = 0; i < names.size(); i ++) {
                                    // System.out.println(names.get(i) + "POSITION: " + i);
                                    //System.out.println(names.get(1));
                                }
                                //System.out.println(names.size());
                                //System.out.println(names.get(1));


                                if (Objects.equals(tag, "minplaytime")) {minPlaytime_setter.setText("Min: "+ value);}
                                if (Objects.equals(tag, "maxplaytime")) {maxPlaytime_setter.setText("Max: "+value);}
                                if (Objects.equals(tag, "age")) {age_setter.setText(value);}

                                if (Objects.equals(tag, "minplayers")) {
                                    minPlayers_setter.setText("Min: "+value);
                                }

                                if (Objects.equals(tag, "maxplayers")) {
                                    maxPlayers_setter.setText("Max: "+value);
                                }

                                if (Objects.equals(tag, "description")) {

                                    description.setText(value);
                                    String text = (String) description.getText();
                                    // remove and replace HTML text
                                    text = Html.fromHtml(text).toString();
                                    description.setText(text);
                                }

                                if (Objects.equals(tag, "image")) {
                                    if(Objects.equals(tag, null)) {
                                        backgroundImg.setVisibility(View.GONE);
                                    } else {
                                        Picasso.with(getContext()).load(value).fit().into(backgroundImg);
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
