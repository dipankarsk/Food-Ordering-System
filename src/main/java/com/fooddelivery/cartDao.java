package com.fooddelivery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class cartDao extends foodDao {
    private int orderId;
    private String foodId;
    private String email;
    private double finalPrice;
    private String timeStamp;
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

    public void setTimeStamp(String tStamp){
        this.timeStamp= tStamp;
    }

    public String getTimeStamp(){
        return timeStamp;
    }
    @Override
    void display()
    {
        System.out.println("                   #############  Your order cart #################"+"\n");       
        System.out.print("Sl"+"\t\t"+"Food Name"+"\t\t"+"Food Price"+"\t\t"+"Quantity"+"\n\n");
        System.out.println();
    }
    public void cartDisplay(String food_items_split[],List foodList,String food_items_quantity_split[])
    {
        display();
        for(int i=0;i<food_items_split.length;i++)
        {
            if(food_items_split[i].equals(""))
           {
            continue;
           }
           foodDao f=(foodDao) foodList.get(Integer.parseInt(food_items_split[i]));
           System.out.print(food_items_split[i]+"\t\t"+f.getFood_name()+"\t\t"+f.getFood_price()+"  in Rs"+"\t\t"+food_items_quantity_split[i]);
           System.out.println("\n");
        }
    }
    public List<Integer> quantityCount(String array[])
    {   
        List<Integer> finalCount = new ArrayList<Integer>();
        boolean visited[] = new boolean[array.length];
         
        Arrays.fill(visited, false);
        for (int i = 0; i < array.length; i++) {
            if (visited[i] == true)
                continue;
     
            // Count frequency
            int count = 1;
            for (int j = i + 1; j < array.length; j++) {
                if (array[i].equals(array[j])) {
                    visited[j] = true;
                    count++;
                }
            }
            finalCount.add(count);
        }
    return finalCount;
    }
   
}
