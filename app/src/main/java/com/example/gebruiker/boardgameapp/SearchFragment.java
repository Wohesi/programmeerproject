package com.example.gebruiker.boardgameapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */


public class SearchFragment extends Fragment {

    private String url;

    private RequestQueue requestQueue;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;

    private bg_searchtile bg_searchtile;
    private ArrayList<bg_searchtile> tiles = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View searchFragment =  inflater.inflate(R.layout.fragment_search, container, false);


        // getting xml data
        requestQueue = Volley.newRequestQueue(getContext());

        try {
            factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
        } catch (XmlPullParserException e ) {
            e.printStackTrace();
        }



        // Searching
        SearchView searchView = (SearchView) searchFragment.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        // replacing spaces for appropriate characters
                        // here, s = name of the game
                        s  = s.replace(" ", "%20");

                        // getting the correct url of searched game.
                        url = "http://www.boardgamegeek.com/xmlapi/search?search="+s+"&exact=1";
                        System.out.println(url);

                        final StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    xpp.setInput(new StringReader(response));
                                    int event = xpp.getEventType();
                                    String tag="", value="";

                                    while(event != XmlPullParser.END_DOCUMENT) {

                                        tag = xpp.getName();

                                        switch (event) {
                                            case XmlPullParser.START_TAG:
                                                if(tag.equals("boardgame")) {
                                                    bg_searchtile = new bg_searchtile();
                                                    tiles.add(bg_searchtile);
                                                }
                                                break;
                                            case XmlPullParser.TEXT:
                                                value = xpp.getText();
                                                break;
                                            case XmlPullParser.END_TAG:

                                                switch (tag) {
                                                    case "objectid":
                                                        bg_searchtile.getID(Integer.parseInt(value));
                                                        break;
                                                    case "name":
                                                        bg_searchtile.getName(value);
                                                        break;
                                                    case "yearpublished":
                                                        bg_searchtile.getYearpublished(Integer.parseInt(value));
                                                        break;
                                                }
                                                break;
                                        }
                                        event = xpp.next();
                                    }

                                } catch (XmlPullParserException | IOException e) {
                                    e.printStackTrace();
                                }

                                for (int i = 0; i < tiles.size(); i++) {
                                    System.out.println(tiles.get(i));
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                        requestQueue.add(stringRequest);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        // perform the search query
                        System.out.println("this has been changed:  "+ s);
                        return false;
                    }
                }
        );


        // Inflate the layout for this fragment
        return searchFragment;
    }



}

