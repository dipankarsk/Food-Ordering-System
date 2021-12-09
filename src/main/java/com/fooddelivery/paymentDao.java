package com.fooddelivery;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.fooddelivery.Database.DbHandler;

public class paymentDao extends cartDao{
    private Double toatlPrice=0.0;
    private Double originalEstimateTime=0.0;
    private Double calculatedEstimatedTime=0.0;
    private String foodRatingValues="";
    public String getFoodRatingValues() {
        return foodRatingValues;
    }
    public void setFoodRatingValues(String foodRatingValues) {
        this.foodRatingValues = foodRatingValues;
    }
    public void setOriginalEstimateTime(Double originalEstimateTime) {
        this.originalEstimateTime = originalEstimateTime;
    }
    public Double getOriginalEstimateTime() {
        return originalEstimateTime;
    }
    
    public void setToatlPrice(Double toatlPrice) {
        this.toatlPrice = toatlPrice;
    }
    public Double getToatlPrice() {
        return toatlPrice;
    }

    @Override
    void display()
    {
        System.out.println("Choose a payment mode:\n1. UPI\n2. Debit Card\n3. Credit Card\n4. Net Banking\n5. Exit");
    }
    public void paymentOptions()
    {
        paymentDao pd=new paymentDao();
        pd.display();
    }
    public void tracker(Double EstimatedTime)
    {
         System.out.println(" ######### Tracking ###########");
         System.out.println(EstimatedTime);
         
         long minutes=0l; 
         long triggeredTimeStamp = System.currentTimeMillis();
         while(minutes<EstimatedTime)
         {
           long endTime = System.currentTimeMillis();
           minutes=(endTime-triggeredTimeStamp)/1000;
           if(minutes>0)
           System.out.println("Order reaches in  :"+(EstimatedTime-minutes)+" Minutes");
           try 
           {
            Thread.sleep(5000);
           } 
           catch (InterruptedException e) 
           {
            
            System.out.println("Error due to thread during payment trackng");
           }
         }
        
    }
    public boolean trackingPage(BufferedReader reader,Double originalEstimatedTime) throws NumberFormatException
    {

        boolean check = false;    
        Random randomEstimate= new Random();
        int x= Math.abs(randomEstimate.nextInt(10-0)+ 0);
        calculatedEstimatedTime= x + originalEstimatedTime;
        if(calculatedEstimatedTime<((0.1*originalEstimatedTime)+originalEstimatedTime))
        {
           return true;
        }  
        return check;
    }

    public List<Integer> paymentPage(BufferedReader reader,List <Integer> food_items_id_extractor,List <foodDao> foodList,paymentDao p) throws NumberFormatException, IOException
    {   DbHandler dbconnection=new DbHandler();
        long tStamp=0l;
        sessionHandler cacheObject=new sessionHandler();
        tStamp=  System.currentTimeMillis();
        p.setTimeStamp(tStamp);
        String orderIdsInString = "";
        for(int i = 0; i<food_items_id_extractor.size();i++)
        {
            foodDao f=(foodDao) foodList.get(food_items_id_extractor.get(i));
            orderIdsInString = orderIdsInString + f.getFood_id();
            if(i!=food_items_id_extractor.size()-1)
            {
                orderIdsInString = orderIdsInString + ",";
            }
        }
        p.setFoodId(orderIdsInString);
        
        int paymentModeOptions=Integer.parseInt(reader.readLine());
        double price = 0;
        if(p.getFinalPrice() == 0)
        {
            price = p.getToatlPrice();
        }
        else{
            price = p.getFinalPrice();
        }
        boolean toCancel=false;
        switch(paymentModeOptions)
        {  
            case 1: System.out.println("Enter your upi id");
                    String upiID=reader.readLine();
                    System.out.println("Payment Done.\nPaid: "+price+"\nPayment mode: UPI");
                    toCancel= trackingPage(reader,p.getOriginalEstimateTime());
                    break;
            case 2: 
                    System.out.println("Enter your Debit card Number");
                    String debitCardNumber=reader.readLine();
                    System.out.println("Enter your Debit card CVV");
                    String debitCardCVV=reader.readLine();
                    System.out.println("Payment Done.\nPaid: "+price+"\nPayment mode: Debit Card");
                    toCancel= trackingPage(reader,p.getOriginalEstimateTime());
                    break;
            case 3: 
                    System.out.println("Enter your Credit card Number");
                    String creditCardNumber=reader.readLine();
                    System.out.println("Enter your Credit card CVV");
                    String creditCardCVV=reader.readLine();
                    System.out.println("Payment Done.\nPaid: "+price+"\nPayment mode: Credit Card");
                    toCancel= trackingPage(reader,p.getOriginalEstimateTime());
                    break;
            case 4: 
                    System.out.println("Enter your netbanking id");
                    String netbankingID=reader.readLine();
                    System.out.println("Payment Done.\nPaid: "+price+"\nPayment mode: Net Banking");
                    toCancel= trackingPage(reader,p.getOriginalEstimateTime());
                    break;
            case 5: System.exit(0);
                    break;
            default: System.out.println("Invalid Selection");
                    break;
        }
        int flagOrderPlaced=0;
        if(toCancel== false)
        {
            System.out.println("Current time has exceeded the estimated time by 10%, so do you wanna cancel the order? \n1. Yes\n2. No ");
            int option= Integer.parseInt(reader.readLine());

            switch(option)
            {
                case 1: System.out.println("Your order has been cancelled!!!!!");
                        break;
                case 2: 
                      tracker(calculatedEstimatedTime);
                      flagOrderPlaced=1;
                      break;
            }
        }
        else
        {
            tracker(calculatedEstimatedTime);
            flagOrderPlaced=1;
        }
        if(flagOrderPlaced==1)
        {
            dbconnection.insertOrderDetails(p);
            System.out.println("Order Delivered");
            System.out.println("Thank You for Ordering");
            System.out.println("###############################");
            System.out.println("Please rate the Application between 1 to 5");
            int rating= Integer.parseInt(reader.readLine());
            dbconnection.insertRating(rating, p.getEmail());
            cacheObject.addToCache("Y", p.getEmail(), "", "", "", "", "");
            
            String foodRating="";
            for(int i = 0; i<food_items_id_extractor.size();i++)
            {
                foodDao f=(foodDao) foodList.get(food_items_id_extractor.get(i));
                f.getFood_id();
                System.out.println("Please rate "+f.getFood_name());
                int ratingfood= Integer.parseInt(reader.readLine());
                foodRating+=ratingfood+",";
            }  
            System.out.println("Thanks for rating the food");
            p.setFoodRatingValues(foodRating);
            dbconnection.insertFoodRating(p);
            food_items_id_extractor=null;
        }
        return food_items_id_extractor;   
    }
}
