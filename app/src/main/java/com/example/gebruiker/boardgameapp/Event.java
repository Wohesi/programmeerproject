/**
 * Name: Wout Singerling
 * https://github.com/Wohesi/programmeerproject
 * Student number: 11136324
 */

package com.example.gebruiker.boardgameapp;


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
