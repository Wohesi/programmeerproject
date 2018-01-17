package com.example.gebruiker.boardgameapp;

/**
 * Created by Wout on 15-1-2018.
 */

public class SearchTile_boardgame {

    public String name, id, year ;
    public int iD;

    public SearchTile_boardgame() {
    }

    public void print_info() {
        System.out.println(id + " " + name + " " + year);
    }


    // setter & getter of name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // setter & getter of year
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    // setter & getter of id
    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    // test ID
    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }


}
