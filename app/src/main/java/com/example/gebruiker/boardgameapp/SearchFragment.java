package com.example.gebruiker.boardgameapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;



public class SearchFragment extends Fragment {

    // get views for adapter
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    // get API connection views / variables
    private RequestQueue requestQueue;
    private SearchTile tileBoardgame;
    private ArrayList<SearchTile> searchTiles = new ArrayList<>();
    private XmlPullParser xpp;
    private String url;
    public String tag_id = null;

    // firebase variables
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        // get the firebase data
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        // setting the adaptor
        recyclerView = view.findViewById(R.id.searchRv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // getting XML data from the API
        requestQueue = Volley.newRequestQueue(getContext());

        // set the adapter
        adapter = new SearchAdapter(searchTiles, getContext());
        recyclerView.setAdapter(adapter);

        // initializing the pullparser for XMLparser
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        // searchview initializing
        SearchView searchView = view.findViewById(R.id.search_bar);

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        // replacing spaces for appropriate characters
                        // here, s = name of the game
                        s  = s.replace(" ", "%20");

                        // getting the correct url of searched game.
                        url = "https://www.boardgamegeek.com/xmlapi/search?search="+s+"&exact=1";
                        //+"&exact=1"
                        System.out.println(url);

                        searchTiles.clear();

                        loadData(url);

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
        return view;
    }

    // load the data with the url constructed by the searchview.
    public void loadData(final String url) {
        final StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    xpp.setInput(new StringReader(response));
                    int event = xpp.getEventType();

                    // empty values for name and tag to be set to the correct ones.
                    String tag = "", value = "";

                    while(event != XmlPullParser.END_DOCUMENT) {

                        // get the name of the tag.
                        tag = xpp.getName();
                        switch(event) {

                            case XmlPullParser.START_TAG:

                                if(tag.equals("boardgame")) {

                                    // make new searchtile
                                    tileBoardgame = new SearchTile();
                                    searchTiles.add(tileBoardgame);

                                    // get the ID as attribute value
                                    tag_id = xpp.getAttributeValue(null, "objectid");

                                    // set the ID for the searchtile
                                    tileBoardgame.setID(tag_id);
                                }
                                break;

                            // get the text between the opening and close tags.
                            case XmlPullParser.TEXT:
                                // conver the text between the tags to a string
                                value = xpp.getText();
                                break;

                            case XmlPullParser.END_TAG:
                                switch (tag) {
                                    case "name":
                                        tileBoardgame.setName(value);
                                        break;
                                    case "yearpublished":
                                        tileBoardgame.setYear(value);
                                }
                                break;
                        }

                        // get the next XML object
                        event = xpp.next();
                    }

                    // add searched items to the adapter
                    adapter.notifyDataSetChanged();

                // error catches
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

}

