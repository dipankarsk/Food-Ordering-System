package com.fooddelivery.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fooddelivery.cartDao;
import com.fooddelivery.foodDao;
import com.fooddelivery.resturantDao;
import com.fooddelivery.Authentication.loginDao;
import com.fooddelivery.Authentication.registrationDao;

public class DbHandler extends Dboperation{
    public List<foodDao> fetchFoodItems(int resturant_id)
    {
        List<foodDao> foodList = new ArrayList<foodDao>();
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
                foodDao f= new foodDao();
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
    public List<resturantDao> fetchResturantDetils(String city,int lat,int lon)
    {
        List<resturantDao> resturantList = new ArrayList<resturantDao>();
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
                resturantDao r= new resturantDao();
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
    @Override
    public boolean logincheck(loginDao log)
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
    public loginDao fetchFlags(loginDao log)
    {   
        loginDao loginObject = new loginDao();
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
    @Override
    public void insertUserData (registrationDao reg)
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
    public void insertFlags (loginDao loginObject)
    {
        Connection con =  Dbconnection("test.db");
        
        //String sql = "INSERT INTO User(save20,save50) VALUES(?,?)";
        String sql = "UPDATE User SET save20 = ?, save50 = ? WHERE Email = ?";

        try {
            PreparedStatement usri = con.prepareStatement(sql);
            usri.setInt(1, loginObject.getSave20());
            usri.setInt(2, loginObject.getSave50());
            usri.setString(3, loginObject.getEmailId());
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
    @Override
    public Connection Dbconnection(String name_file)
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
    public void insertOrderDetails (cartDao cartObject)
    {
        
        
        String sql = "INSERT INTO Orders(Email, FoodId, FinalPrice, TimeStamp) VALUES(?,?,?,?)";

        try (Connection con1 =  Dbconnection("test.db");
             PreparedStatement usri = con1.prepareStatement(sql))
        {
            usri.setString(1, cartObject.getEmail());
            usri.setString(2, cartObject.getFoodId());
            usri.setDouble(3, cartObject.getFinalPrice());
            usri.setLong(4, cartObject.getTimeStamp());
            usri.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error due to insertion " + e.getMessage());
        }
        
    }
}
