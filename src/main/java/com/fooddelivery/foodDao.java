package com.fooddelivery;

public class foodDao {
    private int food_id=0;
    private String food_name="";
    private int food_price=0;
    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }
    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }
    public void setFood_price(int food_price) {
        this.food_price = food_price;
    }
    public int getFood_id() {
        return food_id;
    }
    public int getFood_price() {
        return food_price;
    }
    public String getFood_name() {
        return food_name;
    }
}
