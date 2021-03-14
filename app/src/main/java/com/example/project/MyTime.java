package com.example.project;

import java.util.ArrayList;

public class MyTime {
    private int startHour;
    private int startMins;
    private int finishHour;
    private int finishMins;

    public MyTime(int startHour, int startMins, int finishHour, int finishMins) {
        this.startHour = startHour;
        this.startMins = startMins;
        this.finishHour = finishHour;
        this.finishMins = finishMins;
    }
    public MyTime(String string){

        int s_Hour, s_Mins, f_Hour,f_Mins, i;
        String st = "";
        ArrayList<String> lsDate= new ArrayList<String>();
        for (i=0; i<string.length();i++){
            if (string.charAt(i)!=':'){
                st+=string.charAt(i);
            }
            else{
                lsDate.add(st);
                st="";
            }
        }
        lsDate.add(st);
        s_Hour = Integer.parseInt(lsDate.get(0));
        s_Mins=Integer.parseInt(lsDate.get(1));
        f_Hour=Integer.parseInt(lsDate.get(2));
        f_Mins=Integer.parseInt(lsDate.get(3));
        this.startHour = s_Hour;
        this.startMins = s_Mins;
        this.finishMins = f_Hour;
        this.finishMins = f_Mins;


    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMins() {
        return startMins;
    }

    public void setStartMins(int startMins) {
        this.startMins = startMins;
    }

    public int getFinishHour() {
        return finishHour;
    }

    public void setFinishHour(int finishHour) {
        this.finishHour = finishHour;
    }

    public int getFinishMins() {
        return finishMins;
    }

    public void setFinishMins(int finishMins) {
        this.finishMins = finishMins;
    }

    @Override
    public String toString() {
        return startHour+":"+startMins+finishHour+":"+finishMins;
    }
}
