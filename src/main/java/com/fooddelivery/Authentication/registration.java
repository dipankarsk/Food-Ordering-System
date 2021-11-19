package com.fooddelivery.Authentication;

public class registration {
    private String userName="";
    private String password="";
    private String confirmPassword="";
    private String emailId="";
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmailId() {
        return emailId;
    }
    public String getPassword() {
        return password;
    }
    public String getUserName() {
        return userName;
    }
}
