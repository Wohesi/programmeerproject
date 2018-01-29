package com.example.gebruiker.boardgameapp;


public class SearchTile {

    public String name, id, year ;
    public int iD;

    public SearchTile() {
    }

    // test print
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


}
