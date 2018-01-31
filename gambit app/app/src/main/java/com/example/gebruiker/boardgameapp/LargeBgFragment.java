/**
 * Name: Wout Singerling
 * https://github.com/Wohesi/programmeerproject
 * Student number: 11136324
 */

package com.example.gebruiker.boardgameapp;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;


public class LargeBgFragment extends Fragment {

    // values for visuals
    private ImageButton openCalendar;
    private TextView title, year, age_setter, minPlaytime_setter, maxPlaytime_setter, maxPlayers_setter, minPlayers_setter, description;
    private ImageView backgroundImg;

    // values for API connection
    private String url;
    private RequestQueue requestQueue;
    private XmlPullParser xpp;
    private String bgId;
    private String tag;
    private String value;

    // firebase
    private FirebaseAuth auth;
    private String userID;


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
            bgId = arguments.getString("id", bgId);
        }

        // firebase
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(mAuthListener);
        FirebaseUser user = auth.getCurrentUser();

        // hide the make event button if a user is not logged in.
        // this so that only a signed in user can make an event.
        hideButton(user, LargeBgFragment);

        // get the correct ID with the corresponding API url
        url = "https://www.boardgamegeek.com/xmlapi/boardgame/" + bgId;

        // get views
        findId(LargeBgFragment);

        // get the calendar dialog
        openCalendar = LargeBgFragment.findViewById(R.id.openCalendar);
        makeEvent();

        // get API connection
        requestQueue = Volley.newRequestQueue(getContext());

        // get XML parser conection
        xmlConnector();

        // load data
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

                    // while the event is not the end of the document, continue the loop.
                    while (event != XmlPullParser.END_DOCUMENT) {

                        // get the name of the XML tag
                        tag = xpp.getName();

                        // set a switch on the type of event
                        switch (event) {
                            // check for the starttag
                            case XmlPullParser.START_TAG:
                                if (tag.equals("boardgame"))
                                    break;
                            // set value to the text between the tags
                            case XmlPullParser.TEXT:
                                value = xpp.getText();

                            // check the end tag
                            case XmlPullParser.END_TAG:

                                // set the title of the game where the title is the primary title in the xml file
                                if(Objects.equals(xpp.getName(), "name") && Objects.equals(xpp.getAttributeValue(null, "primary"), "true")) {
                                    if(xpp.next() == XmlPullParser.TEXT) {
                                        title.setText(xpp.getText());
                                    }
                                }

                                // set other values of the game.
                                if (Objects.equals(tag, "yearpublished"))   { year.setText(value);}
                                if (Objects.equals(tag, "minplaytime"))     {minPlaytime_setter.setText("Min: "+ value);}
                                if (Objects.equals(tag, "maxplaytime"))     {maxPlaytime_setter.setText("Max: "+value);}
                                if (Objects.equals(tag, "age"))             {age_setter.setText(value);}
                                if (Objects.equals(tag, "minplayers"))      {minPlayers_setter.setText("Min: "+value);}
                                if (Objects.equals(tag, "maxplayers"))      {maxPlayers_setter.setText("Max: "+value);}
                                if (Objects.equals(tag, "description"))     {description();}
                                if (Objects.equals(tag, "image"))           {Picasso.with(getContext()).load(value).fit().into(backgroundImg);}

                                break;
                        }
                        // get the next event, which is the next "boardgame" tag
                        event = xpp.next();
                    }
                } catch (XmlPullParserException | IOException e) {
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

    // set the description without the HTML mark up.
    public void description() {
        description.setText(value);
        String text = (String) description.getText();
        // remove and replace HTML text
        text = Html.fromHtml(text).toString();
        description.setText(text);
    }

    // make a new event in a dialog screen
    public void makeEvent() {
        openCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewEventFragment dialog = new NewEventFragment();
                dialog.show(getFragmentManager(), "newEventDragment");
            }

        });
    }

    // make a new XMLpul parser
    public void xmlConnector() {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
        } catch (XmlPullParserException e ) {
            e.printStackTrace();
        }
    }

    // set the views of the fragment.
    public void findId(View LargeBgFragment) {
        // set the views
        title = LargeBgFragment.findViewById(R.id.title);
        year = LargeBgFragment.findViewById(R.id.year);

        // maximum and minimum players
        maxPlayers_setter = LargeBgFragment.findViewById(R.id.num_players_setter_max);
        minPlayers_setter = LargeBgFragment.findViewById(R.id.num_players_setter_min);

        // age
        age_setter = LargeBgFragment.findViewById(R.id.age_setter);

        // minimum and maximum players
        minPlaytime_setter = LargeBgFragment.findViewById(R.id.minPlaytime_setter);
        maxPlaytime_setter = LargeBgFragment.findViewById(R.id.maxPlaytime_setter);

        backgroundImg = LargeBgFragment.findViewById(R.id.background_img);
        description = LargeBgFragment.findViewById(R.id.description_setter);
    }

    // update the UI with the correct events.
    public void hideButton( FirebaseUser currentUser, View view ) {
        if (currentUser == null) {
            view.findViewById(R.id.openCalendar).setVisibility(View.GONE);
        }
    }

    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if (firebaseAuth.getCurrentUser() != null) {
                userID = firebaseAuth.getCurrentUser().getUid();
            }
        }
    };

}
