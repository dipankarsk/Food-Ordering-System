package com.fooddelivery;

public class cacheDao {

    private String cacheStatus;
    private String cacheEmail;
    private String cacheLocation;
    private String cachefoodItems;
    private String cacheResturantId;
    private String cacheTotalPrice;
    private String cacheQuantity;

    public cacheDao()
    {
      this.cacheEmail="";
      this.cacheLocation="";
      this.cacheQuantity="";
      this.cacheStatus="N";
      this.cacheResturantId="";
      this.cacheTotalPrice="";
      this.cachefoodItems="";
    }

    public void setCacheEmail(String cacheEmail) {
        this.cacheEmail = cacheEmail;
    }
    public void setCacheLocation(String cacheLocation) {
        this.cacheLocation = cacheLocation;
    }
    public void setCacheQuantity(String cacheQuantity) {
        this.cacheQuantity = cacheQuantity;
    }
    public void setCacheResturantId(String cacheResturantId) {
        this.cacheResturantId = cacheResturantId;
    }
    public void setCacheStatus(String cacheStatus) {
        this.cacheStatus = cacheStatus;
    }
    public void setCacheTotalPrice(String cacheTotalPrice) {
        this.cacheTotalPrice = cacheTotalPrice;
    }
    public void setCachefoodItems(String cachefoodItems) {
        this.cachefoodItems = cachefoodItems;
    }
    public String getCacheEmail() {
        return cacheEmail;
    }
    public String getCacheLocation() {
        return cacheLocation;
    }
    public String getCacheQuantity() {
        return cacheQuantity;
    }
    public String getCacheResturantId() {
        return cacheResturantId;
    }
    public String getCacheStatus() {
        return cacheStatus;
    }
    public String getCacheTotalPrice() {
        return cacheTotalPrice;
    }
    public String getCachefoodItems() {
        return cachefoodItems;
    }
}
