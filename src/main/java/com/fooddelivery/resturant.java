package com.fooddelivery;

public class resturant {
    private int resturant_id=0;
    private String resturant_name="";
    private int latitude=0;
    private int longitude=0;
    private String food_items="";
    private String resturant_address="";
    private Double estimated_time=0.0;
    private Double resturant_distance=0.0;
    private String resturant_city="";
    public void setResturant_city(String resturant_city) {
        this.resturant_city = resturant_city;
    }
    public String getResturant_city() {
        return resturant_city;
    }
    public void setFood_items(String food_items) {
        this.food_items = food_items;
    }
    public void setResturant_name(String resturant_name) {
        this.resturant_name = resturant_name;
    }
    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }
    public void setResturant_id(int resturant_id) {
        this.resturant_id = resturant_id;
    }
    public void setResturant_address(String resturant_address) {
        this.resturant_address = resturant_address;
    }
    public String getFood_items() {
        return food_items;
    }
    public int getLatitude() {
        return latitude;
    }
    public int getLongitude() {
        return longitude;
    }
    public String getResturant_address() {
        return resturant_address;
    }
    public String getResturant_name() {
        return resturant_name;
    }
    public int getResturant_id() {
        return resturant_id;
    }
    public void setEstimated_time(Double estimated_time) {
        this.estimated_time = estimated_time;
    }
    public Double getEstimated_time() {
        return estimated_time;
    }
    public void setResturant_distance(Double resturant_distance) {
        this.resturant_distance = resturant_distance;
    }
    public Double getResturant_distance() {
        return resturant_distance;
    }
}
