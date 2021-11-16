package com.fooddelivery.Authentication;

public class login {
    private String userName="";
    private String password="";
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.userName = username;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return userName;
    }
}
