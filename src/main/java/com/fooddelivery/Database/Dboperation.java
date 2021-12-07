package com.fooddelivery.Database;
import java.sql.Connection;
import com.fooddelivery.Authentication.loginDao;
import com.fooddelivery.Authentication.registrationDao;

public abstract class Dboperation {
    final String dbPath = "jdbc:sqlite:./" + "test.db";
    abstract public void insertUserData (registrationDao reg);
    abstract public boolean logincheck(loginDao log);
    abstract public Connection Dbconnection();
}
