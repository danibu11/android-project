package com.example.project;

public class User {

    private int id;
    private static String F_name;
    private static String L_name;
    private String Language;
    private int Age;
    private String Region;
    private String Password;
    private Privacy p;

    public User(int id, String f_name, String l_name, String language, int age, String region, String password) {
        this.id = id;
        F_name = f_name;
        L_name = l_name;
        Language = language;
        Age = age;
        Region = region;
        Password = password;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getF_name() {
        return F_name;
    }

    public void setF_name(String f_name) {
        F_name = f_name;
    }

    public static String getL_name() {
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





}
