package com.example.project;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class SQLConnector { //a little class to help up with sql queries

    final String url = "jdbc:mysql://localhost";
    final String user = "root";
    final String password = "root";
    static Connection con;

    public SQLConnector() throws SQLException {

        try {
           Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            Log.d("TAG", "SQLConnector: " + e);
        }



        // establish the connection
        con = DriverManager.getConnection(url, user, password);

    }

    public boolean hakolTov()
    {
        return (con != null);

    }
}