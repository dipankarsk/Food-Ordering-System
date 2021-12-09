package com.fooddelivery;

import java.util.ArrayList;
import java.util.List;

import com.fooddelivery.Database.DbHandler;

public class wishlistDao extends foodDao{
  
    private String foodId;
    private String email;
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
    @Override
    void display()
    {
        System.out.println("                   #############  Your WhishList #################"+"\n");       
        System.out.print("Sl"+"\t\t"+"Food Name"+"\t\t"+"Food Price"+"\t\t"+"\n\n");
        System.out.println();
    }
    public void wishListDisplay(List <Integer> whishlist_food_items_id_extractor,List<foodDao> foodList)
    {
        wishlistDao wd=new wishlistDao();
        wd.display();
        for(int i=0;i<whishlist_food_items_id_extractor.size();i++)
        {
           foodDao f=(foodDao) foodList.get(whishlist_food_items_id_extractor.get(i));
           System.out.print(whishlist_food_items_id_extractor.get(i)+"\t\t"+f.getFood_name()+"\t\t"+f.getFood_price()+"  in Rs");
           System.out.println("\n");
        }
    }
    public String wishListConfigurator(wishlistDao w)
    {
        DbHandler dbHandler=new DbHandler();
        w=dbHandler.fetchWishList(w);
        if(w.getFoodId()==null)
        {
            System.out.println("No Item to Show");
        }
        else
        {
            String foodId=w.getFoodId();
            List <foodDao> f=null;
            f=dbHandler.fetchFoodItemsWishList();
            List <Integer> whishlist_food_items_id_extractor=new ArrayList<Integer>();
            for(int i=0;i<foodId.split(",").length;i++){
                whishlist_food_items_id_extractor.add(Integer.parseInt(foodId.split(",")[i]));
            }
            wishListDisplay(whishlist_food_items_id_extractor, f);
        }
        return w.getFoodId();
    }
    public void addToWishList(wishlistDao w)
    {
        DbHandler dbHandler=new DbHandler();
        dbHandler.insertToWishList(w);
    }
}
