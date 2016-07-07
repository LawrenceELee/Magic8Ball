package com.example.lawrence.fortuneteller;

/**
 * Created by Lawrence on 4/6/2016.
 *
 * Fortune object to organize code better.
 */
public class Fortune {

    private String fortune;
    private int color;

    public Fortune(){
    }

    public Fortune(String s, int c){
        fortune = s;
        color = c;
    }

    public String getText() {
        return fortune;
    }

    public int getColor() {
        return color;
    }
}
