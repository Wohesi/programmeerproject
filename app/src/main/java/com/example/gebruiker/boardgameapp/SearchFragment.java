package com.example.gebruiker.boardgameapp;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */


public class SearchFragment extends Fragment {


    private RequestQueue requestQueue;
    private bg_searchtile searchtile;
    private ArrayList<bg_searchtile> searchTiles = new ArrayList<>();

    private XmlPullParser xpp;

    private String url;
    private static final String ns = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View searchFragment =  inflater.inflate(R.layout.fragment_search, container, false);

        requestQueue = Volley.newRequestQueue(getContext());

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
        } catch (XmlPullParserException e) {
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
                        url = "https://www.boardgamegeek.com/xmlapi/search?search="+s+"&exact=1";


                        final StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    xpp.setInput(new StringReader(response));
                                    int event = xpp.getEventType();

                                    String tag = "", value = "";

                                    while(event != XmlPullParser.END_DOCUMENT) {
                                        tag = xpp.getName();
                                        switch(event) {
                                            case XmlPullParser.START_TAG:
                                                if(tag.equals("boardgame")) {
                                                    searchtile = new bg_searchtile();

                                                    // not working??
                                                    searchTiles.add(searchtile);
                                                }
                                                break;
                                            case XmlPullParser.TEXT:
                                                value = xpp.getText();
                                                break;
                                            case XmlPullParser.END_TAG:

                                                switch (tag) {
                                                    case "name":
                                                        searchtile.setName(value);
                                                        break;
                                                    case "yearpublished":
                                                        searchtile.setYearpublished(Integer.parseInt(value));
                                                }
                                                break;
                                        }
                                        event = xpp.next();
                                    }


                                } catch (XmlPullParserException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                for(int i = 0; i < searchTiles.size(); i++ ) {
                                    searchTiles.get(i).print_info();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println(error);
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

