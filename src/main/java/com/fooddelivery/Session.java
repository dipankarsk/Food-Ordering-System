package com.fooddelivery;

public abstract class Session {

    /* Abstract class for Session Management */
    
    final String cachePath="./resources/session.json";
    abstract void addToCache(String cacheStatus, String cacheEmail,String cacheLocation, String cachefoodItems, String cacheResturantId,String cacheTotalPrice,String cacheQuantity);
    abstract sessionDao readFromCache();
}
