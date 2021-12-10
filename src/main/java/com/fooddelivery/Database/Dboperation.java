package com.fooddelivery.Database;
import java.sql.Connection;
import java.util.List;

import com.fooddelivery.foodDao;
import com.fooddelivery.Authentication.loginDao;
import com.fooddelivery.Authentication.registrationDao;

public abstract class Dboperation {

    /* abstract class for Database Operation */
    final String dbPath = "jdbc:sqlite:./" + "test.db";
    abstract public void insertUserData (registrationDao reg);
    abstract public void updateUserData (loginDao loginObject);
    abstract public List<foodDao> fetchUserData(int resturant_id);
    abstract public Connection Dbconnection();
    
}
