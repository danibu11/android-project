package com.example.project;

import android.content.Context;
import android.util.Log;

public class Tasks {
    private static final String TAG = "TASKS"; //for logging
    private String part;
    private int length;
    private int id;
    private int userId;
    private String description;
    private MyDate date;
    private MyTime time;
    private boolean completed;


    public Tasks(String part, int length, int id, String description, MyDate date, MyTime time,int userId) {
        Log.d("Tasks - object constructor", part+" "+ length+" "+ id+" "+ description+" "+ date.toString()+" "+ time.toString());
        this.part = part;
        this.length = length;
        this.id = id;
        this.userId=userId;
        this.description = description;
        this.date = date;
        this.time = time;
        this.completed=false;
    }

    public Tasks(String part, int length, int id, String description, String date, String time, String completed,int userId) {
        Log.d("Tasks - string constructor", part+" "+ length+" "+ id+" "+ description+" "+ date+" "+ time+" "+completed);
        this.part = part;
        this.length = length;
        this.userId=userId;
        this.id = id;
        this.description = description;
        MyDate myDate = new MyDate(date);
        this.date = myDate;
        MyTime myTime = new MyTime(time);
        this.time = myTime;
        this.completed = Boolean.parseBoolean(completed);
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

    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId;}

    public void setUserId(int userId) { this.userId = userId; }

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

    public boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean isCompleted) {
        this.completed = isCompleted;
    }


    public void saveToDB(Context context) {
        Log.d(TAG, "saveToDB");
        String userDBString =
                "(" + addTicksToStringForDB(String.valueOf(this.id)) + ","
                        + addTicksToStringForDB(String.valueOf(this.userId)) + ","
                        + addTicksToStringForDB(this.description) + ","
                        + addTicksToStringForDB((this.part)) + ","
                        + addTicksToStringForDB(this.time.toString()) + ","
                        + addTicksToStringForDB(String.valueOf( this.length)) + ","
                        + addTicksToStringForDB(this.date.toString()) +  ","
                        + addTicksToStringForDB(String.valueOf(this.completed))+  ")";
        String saveTaskQuery = "INSERT INTO tasks (taskId, userid, description, part, time, length, date, completed) VALUES " + userDBString;
        new DBHelper(context).getWritableDatabase().execSQL(saveTaskQuery);
    };
    public void deleteFromDB(Context context) {
        Log.d(TAG, "Deleted");
        String deleteTaskRecordQuery = "DELETE FROM tasks WHERE tasks.taskId="+String.valueOf(this.id);
        new DBHelper(context).getWritableDatabase().execSQL(deleteTaskRecordQuery);
    }


    private String addTicksToStringForDB(String inputString) {
        return "'"+inputString+"'";
    }


    @Override
    public String toString() {
        return "taskID= "+this.id +", part=" + part + ", length=" + length + ", description=" + description + '\'' + ", date=" + date + ", time=" + time +"UserId"+userId;
    }
}
