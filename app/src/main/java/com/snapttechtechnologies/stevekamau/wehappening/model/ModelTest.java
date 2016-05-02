package com.snapttechtechnologies.stevekamau.wehappening.model;

/**
 * Created by steve on 9/20/15.
 */

import java.util.ArrayList;

public class ModelTest {
    private String title, thumbnailUrl, where, time;
    private int date;
    private String details;

    public ModelTest() {
    }

    public ModelTest(String title, String thumbnailUrl, String where, int date, String time,
                     String details) {
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}