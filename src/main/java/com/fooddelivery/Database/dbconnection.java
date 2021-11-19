package com.fooddelivery.Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {

    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:./" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println("Error"+e.getMessage());
        }
    }

    public static void main(String[] args) {
        createNewDatabase("test.db");
    }
    
}
