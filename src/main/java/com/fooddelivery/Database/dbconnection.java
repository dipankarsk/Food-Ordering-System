package com.fooddelivery.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {

    public static void dbconnect ()
    {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:/test.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        dbconnect();
    }
    
}
