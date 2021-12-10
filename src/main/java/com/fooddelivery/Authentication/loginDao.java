package com.fooddelivery.Authentication;
/** 
 * This class implements the concept of Encapsulation by creating getter setter methods to access its private members 
  */
public class loginDao 
{
    //! the email attribute of User table
    private String emailId="";
    //! the password attribute of User table
    private String password=""; 
    //! the coupon save20 attribute of User table
    private int flag20;
    private int flag50;
    //! the wishlist attribute of User table
    private String wishlist;
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
    public String getWishlist() {
        return wishlist;
    }
    public void setWishlist(String wishlist) {
        this.wishlist = wishlist;
    }
}
