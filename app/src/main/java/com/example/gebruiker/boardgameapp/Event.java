package com.example.gebruiker.boardgameapp;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Wout on 24-1-2018.
 */

public class Event {


    public String titleEvent, dateEvent, timeEvent;

    public Event() {
    }

    // setter & getter of title
    public String getTitle() {
        return titleEvent;
    }

    public  void setTitle(String title) {
        this.titleEvent = title;
    }

    // setter & getter of date
    public String getDate () {
        return dateEvent;
    }

    public void setDate(String date) {
        this.dateEvent = date;
    }

    // setter & getter of time
    public String getTime() {
        return timeEvent;
    }

    public void setTime(String time) {
        this.timeEvent = time;
    }
}
