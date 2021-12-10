package com.fooddelivery.Database;
import java.sql.Connection;
import java.util.List;

import com.fooddelivery.foodDao;
import com.fooddelivery.Authentication.loginDao;
import com.fooddelivery.Authentication.registrationDao;
/**  
*This class implements the concept of Abstraction  by creating abstract methods to get implemented for Database Operations 
*/
public abstract class Dboperation {

    //!path of database
    final String dbPath = "jdbc:sqlite:./" + "test.db";
    //!For inserting data to the database table
    abstract public void insertUserData (registrationDao reg);
    //!For updating attributes in the table of the database 
    abstract public void updateUserData (loginDao loginObject);
    //!For fetching data from the database tables
    abstract public List<foodDao> fetchUserData(int resturant_id);
    //!For connection establishment to the database
    abstract public Connection Dbconnection();
    
}
