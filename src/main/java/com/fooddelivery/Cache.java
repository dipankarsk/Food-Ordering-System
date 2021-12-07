package com.fooddelivery;

public abstract class Cache {
    final String cachePath="./resources/session.json";
    abstract void addToCache(String cacheStatus, String cacheEmail,String cacheLocation, String cachefoodItems, String cacheResturantId,String cacheTotalPrice,String cacheQuantity);
    abstract cacheDao readFromCache();
}
