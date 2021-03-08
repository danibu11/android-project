package com.example.project;

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
        return "start:"+startHour+":"+startMins+"  finish:"+finishHour+":"+finishMins;
    }
}
