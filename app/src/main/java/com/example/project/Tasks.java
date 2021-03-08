package com.example.project;

import android.content.Context;
import android.util.Log;

import java.util.Date;

public class Tasks {
    private static final String TAG = "TASKS"; //for logging
    private String part;
    private int length;
    private int id;
    private String description;
    private MyDate date;
    private MyTime time;


    public Tasks(String part, int length, int id, String description, MyDate date, MyTime time) {
        this.part = part;
        this.length = length;
        this.id = id;
        this.description = description;
        this.date = date;
        this.time = time;
    }


    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MyDate getDate() {
        return date;
    }

    public void setDate(MyDate date) {
        this.date = date;
    }

    public MyTime getTime() {
        return time;
    }

    public void setTime(MyTime time) {
        this.time = time;
    }


    public void saveToDB(Context context) {
        Log.d(TAG, "saveToDB");
        String userDBString =
                "(" + addTicksToStringForDB(String.valueOf(this.id)) + ","
                        + addTicksToStringForDB(this.description) + ","
                        + addTicksToStringForDB((this.part)) + ","
                        + addTicksToStringForDB(this.time.toString()) + ","
                        + addTicksToStringForDB(String.valueOf( this.length)) + ","
                        + addTicksToStringForDB(this.date.toString()) +  ")";
        String saveUserQuery = "INSERT INTO tasks (taskId, description, part, time, length, date ) VALUES " + userDBString;
        new DBHelper(context).getWritableDatabase().execSQL(saveUserQuery);
    };

    private String addTicksToStringForDB(String inputString) {
        return "'"+inputString+"'";
    }


    @Override
    public String toString() {
        return "Tasks{" +
                "part=" + part +
                ", length=" + length +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
