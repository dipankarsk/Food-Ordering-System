package com.fooddelivery.Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fooddelivery.Authentication.registration;

public class dbconnection {
    public static void insertUserData ()
    {
        Connection con =  Dbconnection("test.db");
        registration reg = new registration();
        String sql = "INSERT INTO User(UserName,EmailId , Password) VALUES(?,?,?)";

        try {
            PreparedStatement usri = con.prepareStatement(sql);
                usri.setString(1, reg.getUserName());
                usri.setString(2, reg.getEmailId());
                usri.setString(2, reg.getPassword());
                usri.executeUpdate();
        }
            
        catch (SQLException e) {
            System.out.println("Error due to insertion " + e.getMessage());
        }
    }

    public static Connection Dbconnection(String name_file) {

        String u = "jdbc:sqlite:./" + name_file;

        try (Connection connect = DriverManager.getConnection(u)) {
            if (connect != null) {
                
                return connect;
            }

        } catch (SQLException e) {
            System.out.println("Error"+e.getMessage());
        }
        return null ;
    }

    
    
}
