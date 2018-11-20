package com.manum.android.moodtracker.Models;

/**
 * Created by Emmanuel G. on 29/10/2018.
 */
public class Mood {

    private String name;
    private String comment;

    public Mood(String name, String comment) {

        this.name = name;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public String getComment() {

        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
