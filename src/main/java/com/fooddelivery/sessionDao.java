package com.fooddelivery;
/**
 * Encapsulation for Session Data Read and write
 */
public class sessionDao {  
    private String cacheStatus;
    private String cacheEmail;
    private String cacheLocation;
    private String cachefoodItems;
    private String cacheResturantId;
    private String cacheWhishList;
    private String cacheQuantity;

    public sessionDao()
    {
      this.cacheEmail="";
      this.cacheLocation="";
      this.cacheQuantity="";
      this.cacheStatus="N";
      this.cacheResturantId="";
      this.cacheWhishList="";
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
    public void setCacheWhishList(String cacheWhishList) {
        this.cacheWhishList = cacheWhishList;
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
    public String getcacheWhishList() {
        return cacheWhishList;
    }
    public String getCachefoodItems() {
        return cachefoodItems;
    }
}
