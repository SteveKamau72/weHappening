package com.snapttechtechnologies.stevekamau.wehappening.model;

/**
 * Created by steve on 9/20/15.
 */

import java.util.ArrayList;

public class Model {
    private String title, thumbnailUrl, where, time;
    private int date;
    private ArrayList<String> details;

    public Model() {
    }

    public Model(String title, String thumbnailUrl, String where, int date, String time,
                 ArrayList<String> details) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.date = date;
        this.where=where;
        this.time = time;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<String> details) {
        this.details = details;
    }

}