package com.fooddelivery.Authentication;

public class login {
    private String emailId="";
    private String password="";
    public void setPassword(String password) {
        this.password = password;
    }
   
    public String getPassword() {
        return password;
    }
  public void setEmailId(String emailId) {
      this.emailId = emailId;
  }
  public String getEmailId() {
      return emailId;
  }
}
