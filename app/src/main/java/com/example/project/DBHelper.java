package com.example.project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


/*
 * DBHelper
 * this is an intermediate class used to communicate with the MySQL database and commit queries
 * */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private final String TAG = "DBHelper"; //for logging
    String CREATE_TASKS_TABLE = "CREATE TABLE tasks(taskId TEXT PRIMARY KEY, name TEXT, description TEXT, lastName TEXT, createdAt TEXT, executionDate TEXT, completed TEXT)";
    String CREATE_USERS_TABLE = "CREATE TABLE users(email TEXT PRIMARY KEY, password TEXT, firstName TEXT, lastName TEXT, region TEXT, language TEXT, id TEXT, age TEXT)";
    String INSERT_ADMIN_USER = "INSERT INTO users (email, password, firstName, lastName, region, language, id, age) VALUES ('admin', 'admin', 'admin', 'admin', 'israel', 'english', '0', '69420')";

    // constructor - db name is fixed.
    public DBHelper(Context context) {
        super(context, "buchnitzDB", null, DATABASE_VERSION);
        Log.d(TAG, "constructor");
    }
    // creating the data tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        db.execSQL(CREATE_TASKS_TABLE); // creating the users table
        db.execSQL(CREATE_USERS_TABLE); // creating the users table
        db.execSQL(INSERT_ADMIN_USER); // immediately inserting one admin user so that we can erase the db whenever we want and still have an account to log in with - for development
    }

    // creating the tables from scratch on update
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }

    // a few get-all methods
    public static ArrayList<User> getAllUsersFromDB(Context context){
        // parses a "select all" query, then invokes a DBHelper`s execute with it
        ArrayList<User> allUsers = new ArrayList<>(); //result set
        String query = "SELECT * FROM users";
        Cursor c = new DBHelper(context).getReadableDatabase().rawQuery(query, null); // getting a cursor to the first entry
        c.moveToFirst(); // moving to first just to make sure, the default is we ge the first entry anyway..
        // for each table entry - we create an instance of User and push it into the result ArrayList
        while(c.isAfterLast() == false){
            allUsers.add(new User( // extracting values from current entry - constructing a User Object and pushing it into the result ArrayList
                    Integer.valueOf(c.getString(c.getColumnIndex("id"))) ,
                    c.getString(c.getColumnIndex("firstName")),
                    c.getString(c.getColumnIndex("lastName")),
                    c.getString(c.getColumnIndex("language")),
                    Integer.valueOf(c.getString(c.getColumnIndex("age"))),
                    c.getString(c.getColumnIndex("region")),
                    c.getString(c.getColumnIndex("password")),
                    c.getString(c.getColumnIndex("email"))));
            c.moveToNext();
        }
        return allUsers;
    }
}
