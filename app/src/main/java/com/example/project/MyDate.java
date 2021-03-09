package com.example.project;

import java.util.ArrayList;

public class  MyDate {
    private int day;
    private int mounth;
    private int year;

    public MyDate(int day, int mounth, int year) {
        this.day = day;
        this.mounth = mounth;
        this.year = year;
    }

    public MyDate(String string){

        int day=0, month=0, year=0, i;
        String st = "";
        ArrayList<String> lsDate= new ArrayList<String>();
        for (i=0; i<string.length();i++){
            if (string.charAt(i)!='/'){
                st+=string.charAt(i);
            }
            else{
                lsDate.add(st);
                st="";
            }
        }
        day = Integer.parseInt(lsDate.get(0));
        month=Integer.parseInt(lsDate.get(1));
        year=Integer.parseInt(lsDate.get(2));
        this.day = day;
        this.mounth = month;
        this.year = year;

    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMounth() {
        return mounth;
    }

    public void setMounth(int mounth) {
        this.mounth = mounth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return day + "/" + mounth +  "/" + year ;

    }
}
