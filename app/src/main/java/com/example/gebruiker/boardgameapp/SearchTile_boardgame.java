package com.example.gebruiker.boardgameapp;

/**
 * Created by Wout on 15-1-2018.
 */

public class SearchTile_boardgame {

    public String name, id ;
    public int yearpublished;

    public SearchTile_boardgame() {
    }

    public void print_info() {
        System.out.println(id + " " + name + " " + yearpublished);
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

    public String getID(String value) {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getName() {
        return String.valueOf(Integer.parseInt(name));
    }

    public int getYearpublished() {
        return yearpublished;
    }
}
