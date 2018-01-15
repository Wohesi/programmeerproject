package com.example.gebruiker.boardgameapp;

/**
 * Created by Wout on 15-1-2018.
 */

public class bg_searchtile {

    public String name;
    public int yearpublished, id;

    public bg_searchtile() {
    }

    public void print_info() {
        System.out.println(id + " " + name + " " + yearpublished);
    }

    public String getName(String value) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearpublished(Integer value) {
        return yearpublished;
    }

    public void setYearpublished(int yearpublished) {
        this.yearpublished = yearpublished;
    }

    public int getID(Integer value) {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

}
