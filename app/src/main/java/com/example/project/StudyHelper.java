package com.example.project;

import android.content.Context;
import android.util.Log;

public class StudyHelper {

    private static final String TAG = "StudyHelper"; //for logging
    private boolean a_d_d;
    private boolean a_d_h_d;
    private boolean ritalin;
    private boolean konserta;
    private int mealsPerDay;
    private int userId;



    public StudyHelper (int userId, boolean a_d_d, boolean a_d_h_d , boolean ritalin , boolean konserta , int mealsPerDay){
        this.userId=userId;
        this.a_d_d=a_d_d;
        this.a_d_h_d=a_d_h_d;
        this.ritalin=ritalin;
        this.konserta=konserta;
        this.mealsPerDay=mealsPerDay;
    }

    public boolean isAdd() {
        return a_d_d;
    }

    public void setAdd(boolean add) {
        this.a_d_d = add;
    }

    public boolean isAdhd() {
        return a_d_h_d;
    }

    public void setAdhd(boolean adhd) {
        this.a_d_h_d = adhd;
    }

    public boolean isRitalin() {
        return ritalin;
    }

    public void setRitalin(boolean ritalin) {
        this.ritalin = ritalin;
    }

    public boolean isKonserta() {
        return konserta;
    }

    public void setKonserta(boolean konserta) {
        this.konserta = konserta;
    }

    public int getMealsPerDay() {
        return mealsPerDay;
    }

    public void setMealsPerDay(int mealsPerDay) {
        this.mealsPerDay = mealsPerDay;
    }


    public void saveToDB(Context context) {
        Log.d(TAG, "saveToDB");
        String userDBString =
                "(" + addTicksToStringForDB(String.valueOf(this.userId)) + ","
                        + addTicksToStringForDB(String.valueOf(this.a_d_d)) + ","
                        + addTicksToStringForDB(String.valueOf(this.a_d_h_d) )+ ","
                        + addTicksToStringForDB(String.valueOf(this.ritalin)) + ","
                        + addTicksToStringForDB(String.valueOf(this.konserta) )+ ","
                        + addTicksToStringForDB(String.valueOf(this.mealsPerDay))+ ")";
        String saveUserQuery = "INSERT INTO studyHelper ( userid, a_d_d , a_d_h_d,ritalin,konserta,mealsPerDay) VALUES " + userDBString;
        new DBHelper(context).getWritableDatabase().execSQL(saveUserQuery);
    };

    public void deleteFromDB(Context context) {
        Log.d(TAG, "saveToDB");
        String deleteStudyHelperRecordQuery = "DELETE FROM studyHelper WHERE studyHelper.userid="+String.valueOf(this.userId);
        new DBHelper(context).getWritableDatabase().execSQL(deleteStudyHelperRecordQuery);
    };
    private String addTicksToStringForDB(String inputString) {
        return "'"+inputString+"'";
    }
    @Override
    public String toString() {
        return "StudyHelper{" +
                "a_d_d=" + a_d_d +
                ", a_d_h_d=" + a_d_h_d +
                ", ritalin=" + ritalin +
                ", konserta=" + konserta +
                ", mealsPerDay=" + mealsPerDay +
                ", userId=" + userId +
                '}';
    }

}
