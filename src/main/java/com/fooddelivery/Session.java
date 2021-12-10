package com.fooddelivery;
/**
 * Abstract class for Session Management 
 */
public abstract class Session {

    
    
    final String cachePath="./resources/session.json";
    /**
     * For updating the session for the user to save progress
     * @param cacheStatus
     * @param cacheEmail
     * @param cacheLocation
     * @param cachefoodItems
     * @param cacheResturantId
     * @param cacheTotalPrice
     * @param cacheQuantity
     */
    abstract void addToCache(String cacheStatus, String cacheEmail,String cacheLocation, String cachefoodItems, String cacheResturantId,String cacheTotalPrice,String cacheQuantity);
    /**
     * For reading data from session json file
     */
    abstract sessionDao readFromCache();
}
