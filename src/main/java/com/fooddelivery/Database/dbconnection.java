package com.fooddelivery.Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fooddelivery.Authentication.login;
import com.fooddelivery.Authentication.registration;

public class dbconnection {
    public static boolean logincheck(login log)
    {
        String sql = "SELECT Password " + "FROM User WHERE Email = ?";
        Connection con =  Dbconnection("test.db");
        try{
        PreparedStatement pstmt  = con.prepareStatement(sql);
        pstmt.setString(1, log.getEmailId());
        ResultSet rs  = pstmt.executeQuery();    
        
        if(rs==null)
        {
            return false;
        }
        while (rs.next()) {
                if(log.getPassword().equals(rs.getString(1)))
                {
                    return true;
                }
            }
        }catch(SQLException e)
        {
            System.out.println("Error due select query for Login " + e.getMessage());
        }
        return false;
    }

    public static void insertUserData (registration reg)
    {
        Connection con =  Dbconnection("test.db");
        
        String sql = "INSERT INTO User(UserName,Email,Password) VALUES(?,?,?)";

        try {
            PreparedStatement usri = con.prepareStatement(sql);
            usri.setString(1, reg.getUserName());
            usri.setString(2, reg.getEmailId());
            usri.setString(3, reg.getPassword());
            usri.executeUpdate();
            System.out.println("Registration Successful.......");
        }
        catch (SQLException e) {
            System.out.println("Error due to insertion " + e.getMessage());
        }
        finally
        {
            if(con!=null)
            {
                try {
                    con.close();
                } catch (SQLException e) {
                    
                    e.printStackTrace();
                }
            }
        }
    }

    public static Connection Dbconnection(String name_file) {

        String u = "jdbc:sqlite:./" + name_file;

        try 
        {
            Connection connect = DriverManager.getConnection(u);
            if (connect != null) {
                
                return connect;
            }

        } catch (SQLException e) {
            System.out.println("Error"+e.getMessage());
        }
        return null ;
    }

    
    
}
