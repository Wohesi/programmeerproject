/**
 * Name: Wout Singerling
 * https://github.com/Wohesi/programmeerproject
 * Student number: 11136324
 */

package com.example.gebruiker.boardgameapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class EventAdapter  extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    // setting up variables
    private ArrayList<Event> eventList;

    public EventAdapter(ArrayList<Event> events) {
        eventList = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_event, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // set the correct texts
        holder.titleEvent.setText(eventList.get(position).getTitle());
        holder.dateEvent.setText(eventList.get(position).getDate());
        holder.timeEvent.setText(eventList.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        // set the views
        public TextView titleEvent;
        public TextView dateEvent;
        public TextView timeEvent;

        ViewHolder(View itemView) {
            super(itemView);
            // get the views from the layout file
            titleEvent = itemView.findViewById(R.id.titleEvent);
            dateEvent = itemView.findViewById(R.id.dateEvent);
            timeEvent = itemView.findViewById(R.id.timeEvent);

        }
    }
}
