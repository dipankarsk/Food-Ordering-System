package com.fooddelivery.Authentication;
public class loginDao 
{

    /* Using Encapsulation for login data */
    
    private String emailId="";
    private String password="";
    private int flag20;
    private int flag50;
    public void setPassword(String password) 
    {
        this.password = password;
    }
    public String getPassword() 
    {
        return password;
    }
  public void setEmailId(String emailId) 
    {
        this.emailId = emailId;
    }
  public String getEmailId() 
    {
        return emailId;
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
