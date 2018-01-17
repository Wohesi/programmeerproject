package com.example.gebruiker.boardgameapp;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<SearchTile_boardgame> SearchTile_boardgames;
    private Context context;

    public MyAdapter(ArrayList<SearchTile_boardgame> searchTiles, Context context) {
        SearchTile_boardgames = searchTiles;
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

        final SearchTile_boardgame SearchTile_boardgame = SearchTile_boardgames.get(position);

        holder.title.setText(SearchTile_boardgame.getName());
        holder.year.setText(SearchTile_boardgame.getYearpublished());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        Typeface benton_bold = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/BentonSans Bold.otf");

        public TextView year;
        Typeface benton_reg = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/BentonSans Regular.otf");

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            title.setTypeface(benton_bold);

            year = (TextView) itemView.findViewById(R.id.year);
            year.setTypeface(benton_reg);
        }
    }
}
