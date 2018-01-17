package com.example.gebruiker.boardgameapp;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {

    private ArrayList<SearchTile_boardgame> SearchTile_boardgames;
    private ArrayList<SearchTile_boardgame> mFilteredList;
    private Context context;

    public MyAdapter(ArrayList<SearchTile_boardgame> searchTiles, Context context) {

        SearchTile_boardgames = searchTiles;
        mFilteredList = searchTiles;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bg_listcard, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //final SearchTile_boardgame SearchTile_boardgame = SearchTile_boardgames.get(position);

        //holder.title.setText(SearchTile_boardgame.getName());
        //holder.year.setText(SearchTile_boardgame.getYear());

        holder.title.setText(mFilteredList.get(position).getName());
        holder.title.setText(mFilteredList.get(position).getYear());

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
        //Typeface benton_bold = Typeface.createFromAsset(itemView.getContext().getAssets(), "res/font/bentonsans_bold.otf");


        public TextView year;
        //Typeface benton_reg = Typeface.createFromAsset(itemView.getContext().getAssets(), "res/font/bentonsans_reg.otf");

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            //title.setTypeface(benton_bold);

            year = (TextView) itemView.findViewById(R.id.year);
            //year.setTypeface(benton_reg);
        }
    }
}
