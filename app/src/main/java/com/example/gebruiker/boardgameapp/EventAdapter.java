package com.example.gebruiker.boardgameapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class EventAdapter  extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    // setting up variables
    private ArrayList<Event> eventList;
    private Context context;

    public EventAdapter(ArrayList<Event> events, Context context) {
        eventList = events;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_event, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.titleEvent.setText(eventList.get(position).getTitle());
        holder.dateEvent.setText(eventList.get(position).getDate());
        holder.timeEvent.setText(eventList.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView titleEvent;
        public TextView dateEvent;
        public TextView timeEvent;

        ViewHolder(View itemView) {
            super(itemView);

            titleEvent = itemView.findViewById(R.id.titleEvent);
            dateEvent = itemView.findViewById(R.id.dateEvent);
            timeEvent = itemView.findViewById(R.id.timeEvent);

        }
    }
}
