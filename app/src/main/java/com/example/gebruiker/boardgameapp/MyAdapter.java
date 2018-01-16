package com.example.gebruiker.boardgameapp;

import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wout on 16-1-2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<bg_searchtile> bgSearcTile;
    private Context context;

    public MyAdapter(ArrayList<bg_searchtile> searchTiles, Context context) {
        bgSearcTile = searchTiles;
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

        final bg_searchtile bg_searchtile = bgSearcTile.get(position);

        holder.title.setText(bg_searchtile.getName());
        holder.year.setText(bg_searchtile.getYearpublished());

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
