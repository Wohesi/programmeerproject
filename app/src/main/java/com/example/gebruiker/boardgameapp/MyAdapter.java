package com.example.gebruiker.boardgameapp;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {

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
                .inflate(R.layout.bg_listcard, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.title.setText(mFilteredList.get(position).getName());
        holder.year.setText(mFilteredList.get(position).getYear());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //FragmentManager fm;
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                LargeBgFragment largeBgFragment = new LargeBgFragment();
                activity.getFragmentManager().beginTransaction()
                        .replace(R.id.intent_activity, largeBgFragment )
                        .addToBackStack(null)
                        .commit();
            }
        });
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
                       if( searchTile_boardgame.getName().contains(charString)  || searchTile_boardgame.getYear().contains(charSequence)) {
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
                notifyDataSetChanged();

           }
       };
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
