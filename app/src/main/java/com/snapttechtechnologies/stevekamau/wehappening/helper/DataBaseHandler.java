package com.snapttechtechnologies.stevekamau.wehappening.helper;

/**
 * Created by steve on 10/25/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.snapttechtechnologies.stevekamau.wehappening.model.AttendingListItems;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

    public static final String COLUMN_DETAIL = "detail";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_WHERE = "location";
    public static final String COLUMN_TIME_STAMP = "timestamp";
    // All Static variables
// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "imagedb";
    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_IMAGE + " BLOB," + COLUMN_DETAIL + " TEXT," + COLUMN_TIME + " TEXT," + COLUMN_WHERE + " TEXT," + COLUMN_TIME_STAMP + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

// Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public// Adding new attendingListItems
    void addContact(AttendingListItems attendingListItems) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, attendingListItems._name);
        values.put(KEY_IMAGE, attendingListItems._image);
        values.put(COLUMN_DETAIL, attendingListItems._detail);
        values.put(COLUMN_TIME, attendingListItems._time);
        values.put(COLUMN_WHERE, attendingListItems._where);
        values.put(COLUMN_TIME_STAMP, attendingListItems._timestamp);

// Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Contacts
    public List<AttendingListItems> getAllContacts() {
        List<AttendingListItems> attendingListItemsList = new ArrayList<AttendingListItems>();
// Select All Query
        String selectQuery = "SELECT * FROM contacts ORDER BY datetime(timestamp) ASC";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AttendingListItems attendingListItems = new AttendingListItems();
                attendingListItems.setID(Integer.parseInt(cursor.getString(0)));
                attendingListItems.setName(cursor.getString(1));
                attendingListItems.setImage(cursor.getBlob(2));
                attendingListItems.setDetail(cursor.getString(3));
                attendingListItems.setTime(cursor.getString(4));
                attendingListItems.setWhere(cursor.getString(5));
                attendingListItems.setTimeStamp(cursor.getString(6));
// Adding attendingListItems to list
                attendingListItemsList.add(attendingListItems);
            } while (cursor.moveToNext());
        }
// close inserting data from database
        db.close();
// return contact list
        return attendingListItemsList;

    }

    public List<AttendingListItems> getSingleContacts() {
        List<AttendingListItems> attendingListItemsList = new ArrayList<AttendingListItems>();
// Select single Query
        String selectQuery = "SELECT * FROM contacts ORDER BY datetime(timestamp) ASC LIMIT 1";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AttendingListItems attendingListItems = new AttendingListItems();
                attendingListItems.setID(Integer.parseInt(cursor.getString(0)));
                attendingListItems.setName(cursor.getString(1));
                attendingListItems.setImage(cursor.getBlob(2));
                attendingListItems.setDetail(cursor.getString(3));
                attendingListItems.setTime(cursor.getString(4));
                attendingListItems.setWhere(cursor.getString(5));
                attendingListItems.setTimeStamp(cursor.getString(6));
// Adding attendingListItems to list
                attendingListItemsList.add(attendingListItems);
            } while (cursor.moveToNext());
        }
// close inserting data from database
        db.close();
// return contact list
        return attendingListItemsList;

    }

    // Deleting single attendingListItems
    public void deleteContact(AttendingListItems attendingListItems) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(attendingListItems.getID())});
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

// return count
        return cursor.getCount();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("contacts", null, null);
        db.close();
    }

    public boolean checkForTables() {
        boolean hasRows = false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_CONTACTS, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count > 0)
            hasRows = true;
        db.close();
        return hasRows;
    }
}

