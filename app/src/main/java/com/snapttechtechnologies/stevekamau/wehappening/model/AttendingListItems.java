package com.snapttechtechnologies.stevekamau.wehappening.model;

/**
 * Created by steve on 10/25/15.
 */

public class AttendingListItems {

    public String _name;
    public byte[] _image;
    public String _detail;
    public String _time;
    public String _where;
    public String _timestamp;
    // private variables
    int _id;

    // Empty constructor
    public AttendingListItems() {

    }

    // constructor
    public AttendingListItems(int keyId, String name, byte[] image, String detail, String time, String where, String timestamp) {
        this._id = keyId;
        this._name = name;
        this._image = image;
        this._detail = detail;
        this._time = time;
        this._where = where;
        this._timestamp = timestamp;

    }

    // constructor
    public AttendingListItems(String name, byte[] image, String detail, String time, String where, String timestamp) {
        this._name = name;
        this._image = image;
        this._detail = detail;
        this._time = time;
        this._where = where;
        this._timestamp = timestamp;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int keyId) {
        this._id = keyId;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    // getting phone number
    public byte[] getImage() {
        return this._image;
    }

    public void setImage(byte[] image) {
        this._image = image;
    }

    public String getDetail() {
        return this._detail;
    }

    public void setDetail(String detail) {
        this._detail = detail;
    }

    public String getTime() {
        return this._time;
    }

    public void setTime(String time) {
        this._time = time;
    }

    public String getWhere() {
        return this._where;
    }

    public void setWhere(String where) {
        this._where = where;
    }

    public String getTimeStamp() {
        return this._timestamp;
    }

    public void setTimeStamp(String timeStamp) {
        this._timestamp = timeStamp;
    }

}

