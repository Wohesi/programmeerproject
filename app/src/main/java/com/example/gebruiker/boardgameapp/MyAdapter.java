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


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable, View.OnClickListener {


    private ArrayList<SearchTile_boardgame> SearchTile_boardgames;
    private ArrayList<SearchTile_boardgame> mFilteredList;
    private Context context;
    private String id;

    public MyAdapter(ArrayList<SearchTile_boardgame> searchTiles, Context context) {

        SearchTile_boardgames = searchTiles;
        mFilteredList = searchTiles;
        this.context = context;
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_search_game, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.title.setText(mFilteredList.get(position).getName());
        holder.year.setText(mFilteredList.get(position).getYear());
        id = mFilteredList.get(position).getID();

        // set listener on cardview
        holder.card.setOnClickListener(MyAdapter.this);
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

       return new Filter() {
           @Override
           protected FilterResults performFiltering(CharSequence charSequence) {
               String charString = charSequence.toString();

               if(charString.isEmpty()) {
                   mFilteredList = SearchTile_boardgames;
               } else {
                   ArrayList<SearchTile_boardgame> filteredList = new ArrayList<>();
                   for(SearchTile_boardgame searchTile_boardgame : SearchTile_boardgames) {

                       if( searchTile_boardgame.getName().contains(charString)  || searchTile_boardgame.getYear().contains(charSequence) || searchTile_boardgame.getID().contains(charSequence)) {
                           filteredList.add(searchTile_boardgame);
                       }

                   }
                   mFilteredList = filteredList;
               }

               FilterResults filterResults = new FilterResults();
               filterResults.values = mFilteredList;
               return filterResults;
           }

           @Override
           protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<SearchTile_boardgame>) filterResults.values;
                //notifyDataSetChanged();

           }
       };
    }

    public void newFramgent() {

        // making new fragment
        LargeBgFragment largeBgFragment = new LargeBgFragment();

        // setting bundle to add items
        Bundle arguments = new Bundle();
        arguments.putString("id", id);
        largeBgFragment.setArguments(arguments);

        // commiting fragment
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
        if ( i == R.id.card) {
            System.out.println(id);
            newFramgent();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView year;
        public CardView card;

        ViewHolder(View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            title =  itemView.findViewById(R.id.title);
            year =  itemView.findViewById(R.id.year);
        }
    }
}
