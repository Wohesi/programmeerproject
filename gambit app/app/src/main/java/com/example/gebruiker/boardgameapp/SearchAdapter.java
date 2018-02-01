/**
 * Name: Wout Singerling
 * https://github.com/Wohesi/programmeerproject
 * Student number: 11136324
 */

package com.example.gebruiker.boardgameapp;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable, View.OnClickListener {

    // set variables
    private ArrayList<SearchTile> searchTileBoardgames;
    private ArrayList<SearchTile> filteredList;
    private Context context;
    private String id;

    public SearchAdapter(ArrayList<SearchTile> searchTiles, Context context) {

        // initialize the class variables
        searchTileBoardgames = searchTiles;
        filteredList = searchTiles;
        this.context = context;
    }


    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_search_game, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // set the correct views with the correct contents
        holder.title.setText(filteredList.get(position).getName());
        holder.year.setText(filteredList.get(position).getYear());
        id = filteredList.get(position).getID();

        // set listener on cardview
        // get the ID of the game clicked
        holder.card.setTag(id);
        holder.card.setOnClickListener(SearchAdapter.this);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    // filter function for the searchview
    @Override
    public Filter getFilter() {

       return new Filter() {
           @Override
           protected FilterResults performFiltering(CharSequence charSequence) {
               String charString = charSequence.toString();

               if(charString.isEmpty()) {
                   filteredList = searchTileBoardgames;
               } else {
                   ArrayList<SearchTile> filteredList = new ArrayList<>();

                   // checks if each element has been filled before adding it to the list of searchtiles.
                   for(SearchTile searchTile_boardgame : searchTileBoardgames) {
                       if( searchTile_boardgame.getName().contains(charString)  || searchTile_boardgame.getYear().contains(charSequence) || searchTile_boardgame.getID().contains(charSequence)) {
                           // add a filtered result to the filterlist
                           filteredList.add(searchTile_boardgame);
                       }

                   }
                   // set the adapter to the list where is been searched for
                   SearchAdapter.this.filteredList = filteredList;
               }

               // set the list of filter results
               FilterResults filterResults = new FilterResults();
               filterResults.values = filteredList;
               return filterResults;
           }

           // update the filteredlist
           @Override
           protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<SearchTile>) filterResults.values;
                //notifyDataSetChanged();

           }
       };
    }

    public void newLargeBgFragment(Object id) {

        // making new fragment
        LargeBgFragment largeBgFragment = new LargeBgFragment();

        // setting bundle to add items
        Bundle arguments = new Bundle();
        arguments.putString("id", id.toString());
        largeBgFragment.setArguments(arguments);

        // commiting fragment and replecing the rootframe with the
        final FragmentTransaction transaction = ((Activity) context).getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_frame, largeBgFragment);
        transaction.setTransition(transaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // onclick listeners
    @Override
    public void onClick(View v) {
        int i = v.getId();
        Object id = v.getTag();
        if ( i == R.id.card) {
            newLargeBgFragment(id);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        // initialize the views
        public TextView title;
        public TextView year;
        public CardView card;

        ViewHolder(View itemView) {
            super(itemView);

            // set the views
            card = itemView.findViewById(R.id.card);
            title =  itemView.findViewById(R.id.title);
            year =  itemView.findViewById(R.id.year);
        }
    }
}
