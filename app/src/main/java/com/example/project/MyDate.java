package com.example.project;

import android.util.Log;

import java.util.ArrayList;

public class  MyDate {
    private int day;
    private int month;
    private int year;

    public MyDate(int day, int month, int year) {
        Log.d("myDateClass",day+"/"+month+"/"+year);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public MyDate(String dateString){
        int day=0, month=0, year=0;
        String[] splitedDateString = dateString.split("/");
        day = Integer.parseInt(splitedDateString[0]);
        month=Integer.parseInt(splitedDateString[1]);
        year=Integer.parseInt(splitedDateString[2]);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMounth() {
        return month;
    }

    public void setMounth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return day + "/" + month +  "/" + year ;

    }
}
