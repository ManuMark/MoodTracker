package com.manum.android.moodtracker.Models;

/**
 * Created by Emmanuel G. on 29/10/2018.
 */
public class Mood {

    private String name;
    private String comment;
    private String color;
    private int width;

    // Constructor
    public Mood(String name, String color, String comment, int width) {

        this.name = name;
        this.color = color;
        this.comment = comment;
        this.width = width;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getComment() { return comment; }

    public String getColor() { return color; }

    public int getWidth() { return width; }

}
