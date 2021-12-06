package com.fooddelivery.Authentication;

public class registrationDao 
{
    private String userName="";
    private String password="";
    private String emailId="";
    private int flag20 = 1;
    private int flag50 = 1;
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }
    public void setEmailId(String emailId) 
    {
        this.emailId = emailId;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }
    public String getEmailId() 
    {
        return emailId;
    }
    public String getPassword()
    {
        return password;
    }
    public String getUserName() 
    {
        return userName;
    }
    public void setSave20(int flag20) 
    {
        this.flag20 = flag20;
    }
    public int getSave20() 
    {
        return flag20;
    }
    public void setSave50(int flag50) 
    {
        this.flag50 = flag50;
    }
    public int getSave50() 
    {
        return flag50;
    }
  

}
