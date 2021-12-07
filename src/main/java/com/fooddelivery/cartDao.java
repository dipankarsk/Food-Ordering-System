package com.fooddelivery;

public class cartDao {
    private int orderId;
    private String foodId;
    private String email;
    private double finalPrice;
    private long timeStamp;
    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }
    public int getOrderId()
    {
        return orderId;
    }
    public void setFoodId(String foodId)
    {
        this.foodId = foodId;
    }
    public String getFoodId()
    {
        return foodId;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getEmail()
    {
        return email;
    }
    public void setFinalPrice(Double finalPrice)
    {
        this.finalPrice = finalPrice;
    }
    public Double getFinalPrice()
    {
        return finalPrice;
    }

    public void setTimeStamp(long timeStamp){
        this.timeStamp= timeStamp;
    }

    public long getTimeStamp(){
        return timeStamp;
    }
    
}
