package com.example.project;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

public class User {
    private static final String TAG = "USER"; //for logging
    private int id;
    private String F_name;
    private String L_name;
    private String Language;
    private int Age;
    private String Region;
    private String Password;
    private String Email;
    private Privacy p;

    public User(int id, String f_name, String l_name, String language, int age, String region, String password, String email) {
        this.id = id;
        F_name = f_name;
        L_name = l_name;
        Language = language;
        Age = age;
        Region = region;
        Password = password;
        Email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getF_name() {
        return F_name;
    }

    public void setF_name(String f_name) {
        F_name = f_name;
    }

    public String getL_name() {
        return L_name;
    }

    public void setL_name(String l_name) {
        L_name = l_name;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    // parses an INSERT query formatted with its own data members as values, then invokes a DBHelper`s execute with it, to save itself to the DB/.
    public void saveToDB(Context context) {
        Log.d(TAG, "saveToDB");
        String userDBString =
                "(" + addTicksToStringForDB(this.Email) + ","
                        + addTicksToStringForDB(this.Password) + ","
                        + addTicksToStringForDB(this.F_name) + ","
                        + addTicksToStringForDB(this.L_name) + ","
                        + addTicksToStringForDB(this.Region) + ","
                        + addTicksToStringForDB(this.Language) + ","
                        + addTicksToStringForDB(String.valueOf(this.id)) + ","
                        + addTicksToStringForDB(String.valueOf(this.Age)) + ")";
        String saveUserQuery = "INSERT INTO users (email, password, firstName, lastName, region, language, id, age) VALUES " + userDBString;
        new DBHelper(context).getWritableDatabase().execSQL(saveUserQuery);
    };

    private String addTicksToStringForDB(String inputString) {
        return "'"+inputString+"'";
    }

    public String toString() {
        String userString = "USER: " +
                        this.Email + ","
                        + this.Password + ","
                        + this.F_name + ","
                        + this.L_name + ","
                        + this.Region + ","
                        + this.Language + ","
                        + String.valueOf(this.id) + ","
                        + String.valueOf(this.Age);
        return userString;
    }
}
