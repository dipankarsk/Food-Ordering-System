package com.fooddelivery.Database;
import java.sql.Connection;
import java.util.List;

import com.fooddelivery.foodDao;
import com.fooddelivery.resturantDao;
import com.fooddelivery.Authentication.loginDao;
import com.fooddelivery.Authentication.registrationDao;

public abstract class Dboperation {

    /* abstract class for Database Operation */
    final String dbPath = "jdbc:sqlite:./" + "test.db";
    abstract public void insertUserData (registrationDao reg);
    abstract public boolean logincheck(loginDao log);
    abstract public List<foodDao> fetchFoodItems(int resturant_id);
    abstract public Connection Dbconnection();
    abstract public List<resturantDao> fetchResturantDetils(String city,int lat,int lon);
}
