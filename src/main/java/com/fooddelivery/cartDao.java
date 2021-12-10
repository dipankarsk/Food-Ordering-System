package com.fooddelivery;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class implements the concept of encapsulation and inherits foodDao for cart 
 */

public class cartDao extends foodDao {
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
    @Override
    void display()
    {
        System.out.println("                   #############  Your order cart #################"+"\n");       
        System.out.print("Sl"+"\t\t"+"Food Name"+"\t\t"+"Food Price"+"\t\t"+"Quantity"+"\n\n");
        System.out.println();
    }
    /**
     * To display the user cart
     * @param food_items_id_extractor
     * @param foodList
     * @param food_items_quantity_extractor
     */
    public void cartDisplay(List <Integer> food_items_id_extractor,List<foodDao> foodList,List<Integer> food_items_quantity_extractor)
    {
        cartDao cd=new cartDao();
        cd.display();
        for(int i=0;i<food_items_id_extractor.size();i++)
        {
           foodDao f=(foodDao) foodList.get(food_items_id_extractor.get(i));
           System.out.print(food_items_id_extractor.get(i)+"\t\t"+f.getFood_name()+"\t\t"+f.getFood_price()+"  in Rs"+"\t\t"+food_items_quantity_extractor.get(i));
           System.out.println("\n");
        }
    }
    public void cartOptions()
    {
        System.out.println("####################");
        System.out.println("1. Close the application");
        System.out.println("2. Logout");
        System.out.println("3. Remove an item from cart");
        System.out.println("4. Add an item to cart");
        System.out.println("5. Remove all items from cart");
        System.out.println("6. change the resturant");
        System.out.println("7. Checkout");
        System.out.println("####################");
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
    /**
     * Remove all items from cart
     * @param c
     */
    public void removeAllItems(cartDao c)
    {
        sessionHandler cacheObject=new sessionHandler();
        cacheObject.addToCache("Y", c.getEmail(), c.getResturant_city(),"", c.getResturant_id()+"","","");
        
    }
    /**
     * To change the resturant and discard the cart
     * @param c
     */
    public void changeResturant(cartDao c)
    {
        sessionHandler cacheObject=new sessionHandler();
        cacheObject.addToCache("Y", c.getEmail(), c.getResturant_city(),"", "","","");
        
    }
    /**
     * to remove a single item from cart
     * @param br
     * @param c
     * @param food_items_id_extractor
     * @param food_items_quantity_extractor
     * @return
     * @throws IOException
     */
    public List < List <Integer> > removeItems(BufferedReader br,cartDao c, List <Integer> food_items_id_extractor,List<Integer> food_items_quantity_extractor) throws IOException
    {
        sessionHandler cacheObject=new sessionHandler();
        System.out.println("Enter the SL no of the items to be deleted");
        String food_items_to_delete=br.readLine();
        String food_items_to_delete_split[]=food_items_to_delete.split(",");
        String food_items="";
        String food_order_quantity="";
       
       for(int j=0;j<food_items_to_delete_split.length;j++)
       {
           if(food_items_id_extractor.contains(Integer.parseInt(food_items_to_delete_split[j])))
           {
               int indexToDelete=food_items_id_extractor.indexOf(Integer.parseInt(food_items_to_delete_split[j]));
               food_items_id_extractor.remove(indexToDelete);
               food_items_quantity_extractor.remove(indexToDelete);
           }
       }
       for(int j=0;j<food_items_id_extractor.size();j++)
       {
               food_items+=food_items_id_extractor.get(j)+",";
               food_order_quantity+=food_items_quantity_extractor.get(j)+",";
       }
       cacheObject.addToCache("Y", c.getEmail(), c.getResturant_city(), food_items, c.getResturant_id()+"","",food_order_quantity);
       List<List<Integer>> l1=new ArrayList<>();
       l1.add(food_items_id_extractor);
       l1.add(food_items_quantity_extractor);
       return l1;
    }
    /**
     * To add an item to the cart
     * @param br
     * @param c
     * @param food_items_id_extractor
     * @param food_items_quantity_extractor
     * @param foodList
     * @return
     * @throws IOException
     */
    public List < List <Integer> > addItems(BufferedReader br,cartDao c, List <Integer> food_items_id_extractor,List<Integer> food_items_quantity_extractor,List <foodDao> foodList) throws IOException
    {
        sessionHandler cacheObject=new sessionHandler();
        
        foodMenuDisplay(foodList); // using inherited method from foodDao class
        System.out.println("Enter the SL no of the item to be inserted");
        String food_item_to_add=br.readLine();
        
        String food_items="";
        String food_order_quantity="";
       
      
        if(food_items_id_extractor.contains(Integer.parseInt(food_item_to_add)))
        {
               int indexToAdd=food_items_id_extractor.indexOf(Integer.parseInt(food_item_to_add));
               food_items_quantity_extractor.set(indexToAdd, food_items_quantity_extractor.get(indexToAdd)+1);
        }else{
            food_items_id_extractor.add(Integer.parseInt(food_item_to_add));
            food_items_quantity_extractor.add(1);
        }
       
       for(int j=0;j<food_items_id_extractor.size();j++)
       {
               food_items+=food_items_id_extractor.get(j)+",";
               food_order_quantity+=food_items_quantity_extractor.get(j)+",";
       }
       cacheObject.addToCache("Y", c.getEmail(), c.getResturant_city(), food_items, c.getResturant_id()+"","",food_order_quantity);
       List<List<Integer>> l1=new ArrayList<>();
       l1.add(food_items_id_extractor);
       l1.add(food_items_quantity_extractor);
       return l1;
    }
}
