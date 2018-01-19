package com.example.gebruiker.boardgameapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Xml;
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
import java.io.StringReader;
import java.util.ArrayList;



public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private RequestQueue requestQueue;
    private SearchTile_boardgame tileBoardgame;
    private ArrayList<SearchTile_boardgame> searchTiles = new ArrayList<>();
    private XmlPullParser xpp;
    private String url;
    public String tag_id = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View searchFragment =  inflater.inflate(R.layout.fragment_search, container, false);

        // setting the adaptor
        recyclerView = searchFragment.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // getting XML data from the API
        requestQueue = Volley.newRequestQueue(getContext());

        // initializing the pullparser for XMLparser
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        // searchview initializing
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

                        //MyAdapter.getFilter().filter(s);
                        return false;
                    }
                }
        );

        // Inflate the layout for this fragment
        return searchFragment;
    }

    // load the data with the url constructed by the searchview.
    public void loadData(final String url) {
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
                                //value = xpp.getAttributeName(event);
                                if(tag.equals("boardgame")) {

                                    tag_id = xpp.getAttributeValue(null, "objectid");

                                    tileBoardgame = new SearchTile_boardgame();
                                    searchTiles.add(tileBoardgame);
                                    tileBoardgame.setID(tag_id);
                                }
                                break;
                            case XmlPullParser.TEXT:
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
                        event = xpp.next();

                        // add searched items to the adapter

                        adapter = new MyAdapter(searchTiles, getContext());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);


                    }

                // error catches
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                for(int i = 0; i < searchTiles.size(); i++ ) {
                    // print info to console
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
    }

}

