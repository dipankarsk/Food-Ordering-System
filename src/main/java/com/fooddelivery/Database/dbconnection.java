package com.fooddelivery.Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fooddelivery.resturant;
import com.fooddelivery.Authentication.login;
import com.fooddelivery.Authentication.registration;
public class dbconnection {
    public static List<resturant> fetchResturantDetils(String city,int lat,int lon)
    {
        List<resturant> arrayList = new ArrayList<resturant>();
        String sql = "SELECT * " + "FROM Resturants WHERE resturant_city = ?";
        Connection con =  Dbconnection("test.db");
        try{
        PreparedStatement pstmt  = con.prepareStatement(sql);
        pstmt.setString(1, city);
        ResultSet rs  = pstmt.executeQuery();
        if(rs==null)
        {
            return arrayList;
        }
        while (rs.next()) {
                resturant r= new resturant();
                r.setResturant_id(rs.getInt(1));
                r.setLatitude(rs.getInt(2));
                r.setLongitude(rs.getInt(3));
                r.setResturant_name(rs.getString(4));
                r.setFood_items(rs.getString(5));
                r.setResturant_city(rs.getString(6));
                r.setResturant_address(rs.getString(7));
                Double distanceBetweenSrcRes=Math.sqrt(Math.pow(r.getLatitude()-lat,2)+Math.pow(r.getLongitude()-lon, 2));
                r.setResturant_distance(distanceBetweenSrcRes);
                r.setEstimated_time(distanceBetweenSrcRes+1);
                arrayList.add(r);
            }
        }catch(SQLException e)
        {
            System.out.println("Error due select query for fetching cities " + e.getMessage());
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
        return arrayList;
    }
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
