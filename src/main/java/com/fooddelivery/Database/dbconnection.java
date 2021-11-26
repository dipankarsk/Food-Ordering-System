package com.fooddelivery.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fooddelivery.cart;
import com.fooddelivery.food;
import com.fooddelivery.resturant;
import com.fooddelivery.Authentication.login;
import com.fooddelivery.Authentication.registration;

public class dbconnection {
    public static List<food> fetchFoodItems(int resturant_id)
    {
        List<food> foodList = new ArrayList<food>();
        String sql = "SELECT * " + "FROM Food WHERE resturant_id = ?";
        Connection con =  Dbconnection("test.db");
        try{
        PreparedStatement pstmt  = con.prepareStatement(sql);
        pstmt.setInt(1, resturant_id);
        ResultSet rs  = pstmt.executeQuery();
        if(rs==null)
        {
            return foodList;
        }
        while (rs.next()) {
                food f= new food();
                f.setFood_id(rs.getInt(1));
                f.setFood_name(rs.getString(2));
                f.setFood_price(rs.getInt(3));
                foodList.add(f);
            }
        }catch(SQLException e)
        {
            System.out.println("Error due select query for fetching food " + e.getMessage());
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
        return foodList;
    }
    public static List<resturant> fetchResturantDetils(String city,int lat,int lon)
    {
        List<resturant> resturantList = new ArrayList<resturant>();
        String sql = "SELECT * " + "FROM Resturants WHERE resturant_city = ?";
        Connection con =  Dbconnection("test.db");
        try{
        PreparedStatement pstmt  = con.prepareStatement(sql);
        pstmt.setString(1, city);
        ResultSet rs  = pstmt.executeQuery();
        if(rs==null)
        {
            return resturantList;
        }
        while (rs.next()) {
                resturant r= new resturant();
                r.setResturant_id(rs.getInt(1));
                r.setLatitude(rs.getInt(2));
                r.setLongitude(rs.getInt(3));
                r.setResturant_name(rs.getString(4));
                r.setResturant_city(rs.getString(5));
                r.setResturant_address(rs.getString(6));
                Double distanceBetweenSrcRes=(double)((r.getLatitude()-lat) + (r.getLongitude()-lon));
                r.setResturant_distance(distanceBetweenSrcRes/40);
                r.setEstimated_time((distanceBetweenSrcRes/40)*10);
                resturantList.add(r);
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
        return resturantList;
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
        return false;
    }
    public static login fetchFlags(login log)
    {   
        login loginObject = new login();
        String sql1 = "SELECT * " + "FROM User WHERE Email = ?";
        Connection con =  Dbconnection("test.db");
        try{
        PreparedStatement pstmt1  = con.prepareStatement(sql1);
        pstmt1.setString(1, log.getEmailId());
        ResultSet rs1  = pstmt1.executeQuery();    
        if(rs1==null)
        {
            return null;
        }
        while (rs1.next()) {
          
                loginObject.setSave20(rs1.getInt(4));
                loginObject.setSave50(rs1.getInt(5));
                
            }
        }catch(SQLException e)
        {
            System.out.println("Error due select query for Login " + e.getMessage());
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
        return loginObject;
    }
    public static void insertUserData (registration reg)
    {
        
        
        String sql = "INSERT INTO User(UserName,Email,Password,save20,save50) VALUES(?,?,?,?,?)";

        try (Connection con1 =  Dbconnection("test.db");
             PreparedStatement usri = con1.prepareStatement(sql))
        {
            usri.setString(1, reg.getUserName());
            usri.setString(2, reg.getEmailId());
            usri.setString(3, reg.getPassword());
            usri.setInt(4, reg.getSave20());
            usri.setInt(5, reg.getSave50());
            usri.executeUpdate();
            
            System.out.println("Registration Successful.......");
        }
        catch (SQLException e) {
            System.out.println("Error due to insertion " + e.getMessage());
        }
        
    }
    public static void insertFlags (registration reg)
    {
        Connection con =  Dbconnection("test.db");
        
        //String sql = "INSERT INTO User(save20,save50) VALUES(?,?)";
        String sql = "UPDATE User SET save20 = ?, save50 = ? WHERE Email = ?";

        try {
            PreparedStatement usri = con.prepareStatement(sql);
            usri.setInt(1, reg.getSave20());
            usri.setInt(2, reg.getSave50());
            usri.setString(3, reg.getEmailId());
            usri.executeUpdate();
            
            //System.out.println("Registration Successful.......");
           
        }
        catch (SQLException e) {
            System.out.println("Error due to Updation " + e.getMessage());
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
    public static Connection Dbconnection(String name_file)
     {

        String u = "jdbc:sqlite:./" + name_file;
        Connection connect=null;
        try 
        {
             connect = DriverManager.getConnection(u);
            if (connect != null) {
                
                return connect;
            }

        } catch (SQLException e) {
            System.out.println("Error"+e.getMessage());
        }
       
        return null ;
    }
    public static void insertOrderDetails (cart cartObject)
    {
        
        
        String sql = "INSERT INTO Orders(Email, FoodId, FinalPrice) VALUES(?,?,?)";

        try (Connection con1 =  Dbconnection("test.db");
             PreparedStatement usri = con1.prepareStatement(sql))
        {
            usri.setString(1, cartObject.getEmail());
            usri.setString(2, cartObject.getFoodId());
            usri.setDouble(3, cartObject.getFinalPrice());
            usri.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error due to insertion " + e.getMessage());
        }
        
    }
}
