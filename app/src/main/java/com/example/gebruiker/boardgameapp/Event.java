package com.example.gebruiker.boardgameapp;

/**
 * Created by Wout on 24-1-2018.
 */

public class Event {

    public String titleEvent, date, time;

    public Event() {
    }

    // test print
    public void print_event() {
        System.out.println(titleEvent + " " + date + " " + time);
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
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // setter & getter of time
    public String getTime() {
        return time;
    }

    public void setTime() {
        this.time = time;
    }
}
