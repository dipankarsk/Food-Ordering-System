package com.fooddelivery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import com.fooddelivery.Authentication.registrationDao;
import com.fooddelivery.Authentication.loginDao;
import com.fooddelivery.Database.DbHandler;
public final class foodappp {
    static double totalPrice = 0;
    static double discount = 0;
    static double finalPrice = 0;
    static double distance = 0;
    static double deliveryCharge = 0;
    static int flag20, flag50;
    static int listOfRestaurants;
    static double estimatedTime=0;
    static double actualTime=0;
    static double time=0;
    static String tStamp;
    static int lat=0;
    static int lon=0;
    static long timeElp=0;
    static String resturant_id="";
    static boolean toCancel;
    static int appRating=0;
    static int foodRating[];
    static int deliveryTime=0;
    static long estimatedTimeInMilli=0;
    static List<String> food_items_id_extractor = new ArrayList<String>();// arraylist to store the food ids stored at any instance
    static List<Integer> quantity = new ArrayList<Integer>(); // arraylist to store quantities of each food items inside the cart
    static DbHandler dbconnection=new DbHandler();
    static sessionHandler cacheObject=new sessionHandler();
    public void authenticationDisplay()
    {
        System.out.println("\n                               Welcome to XYZ food delivery system                              \n");
        System.out.println("                  #################### Authentication ########################                    \n");
        System.out.println("1. For LogIn ");
        System.out.println("2. For SignUp  ");
        System.out.println("3. To Close the Apllication\n");
        System.out.println("                                      ####################                     ");
    }

    public static void register (registrationDao reg)
    {
        dbconnection.insertUserData(reg);

    }
    public static void getFlags(loginDao log)
    {
            loginDao l = dbconnection.fetchFlags(log);
            flag20 = l.getSave20();
            flag50 = l.getSave50();
    }
    public static void login (loginDao log)
    {
        boolean credentialStatus=dbconnection.logincheck(log);
        if(credentialStatus)
        {
           System.out.println("Login Successful....\n");
           System.out.println("Welcome "+log.getEmailId()+"\n");
           /*flag20 = log.getSave20();
           flag50 = log.getSave50();
           System.out.println(flag20+" "+flag50);*/
           /*int flags[] = dbconnection.fetchFlags(log);
           flag20 = flags[0];
           flag50 = flags[1];*/
           cacheObject.addToCache("Y", log.getEmailId(), "", "", "","","");
        }else{
            System.out.println("Wrong Credentials\n");
        }
    }
    public static void paymentPage(BufferedReader reader, String email) throws NumberFormatException, IOException
    {   
        cartDao cartObject = new cartDao();
        cartObject.setEmail(email);
        cartObject.setFinalPrice(finalPrice);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();
       // tStamp=  System.currentTimeMillis();
        tStamp= dtf.format(now);
        cartObject.setTimeStamp(tStamp);

        String orderIdsInString = "";
        for(int i = 0; i<food_items_id_extractor.size();i++)
        {
            orderIdsInString = orderIdsInString + food_items_id_extractor.get(i);
            if(i!=food_items_id_extractor.size()-1)
            {
                orderIdsInString = orderIdsInString + ",";
            }
        }
        cartObject.setFoodId(orderIdsInString);
        
        int paymentModeOptions=Integer.parseInt(reader.readLine());
        double price = 0;
        if(finalPrice == 0)
        {
            price = totalPrice;
        }
        else{
            price = finalPrice;
        }
        
        switch(paymentModeOptions)
        {  
            case 1: System.out.println("Payment Done.\nPaid: "+price+"\nPayment mode: UPI");
                    toCancel= trackingPage(reader, email);
                    break;
            case 2: System.out.println("Payment Done.\nPaid: "+price+"\nPayment mode: Debit Card");
                    toCancel= trackingPage(reader, email);
                    break;
            case 3: System.out.println("Payment Done.\nPaid: "+price+"\nPayment mode: Credit Card");
                    toCancel= trackingPage(reader, email);
                    break;
            case 4: System.out.println("Payment Done.\nPaid: "+price+"\nPayment mode: Net Banking");
                    toCancel= trackingPage(reader, email);
                    break;
            case 5: System.exit(0);
                    break;
            default: System.out.println("Invalid Selection");
        }

        if(toCancel== true){
            System.out.println("Current time has exceeded the estimated time by 10%, so do you wanna cancel the order? \n1. Yes\n2. No ");
            int option= Integer.parseInt(reader.readLine());

            switch(option){
                case 1: System.out.println("Your order has been cancelled!!!!!");
                        System.exit(0);
                case 2: toCancel= trackingPage(reader, email);
                        break;
            }
        
        }
        dbconnection.insertOrderDetails(cartObject);
    }

    public static boolean trackingPage(BufferedReader reader, String email) throws NumberFormatException, IOException{
        boolean check= false;
        Random randomNo= new Random();
        int x= Math.abs(randomNo.nextInt(40-5)+ 5);
        estimatedTime= Math.abs(estimatedTime);
        //int high= (int)(estimatedTime+estimatedTime*0.15);
        //int low= (int)(estimatedTime*0.5);
        //deliveryTime= Math.abs(randomNo.nextInt(high-low)+ low);
        deliveryTime= 60;
        if(deliveryTime*60000<= timeElp){
            System.out.println("Your order has been delivered.\nRate our app");

            cartDao cart2= new cartDao();
            cart2.setEmail(email);

            cartDao c = dbconnection.fetchOrderDetails(cart2);

            String foodIDs= c.getFoodId();
            String foodArray[]= foodIDs.split(",");
            appRating= Integer.parseInt(reader.readLine());

            System.out.println("Rate the food");

            for(int i=0; i<foodArray.length; i++){
                foodRating[i]= Integer.parseInt(reader.readLine());
        }             
             
        }
        while(check== false){
        check = false;
        
        System.out.println("1. Track your order" + "\n2. Exit");
        
        int option= Integer.parseInt(reader.readLine());

        switch(option){
            case 1: estimatedTime= x + time;
                    System.out.println("Estimated time for your order is: "+ (estimatedTime-(timeElp/60000)) + " minutes.");
                    
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                    LocalDateTime now = LocalDateTime.now();
                    estimatedTimeInMilli= (long)estimatedTime*60000;
                    /*timeElp= System.currentTimeMillis()- tStamp;
                    System.out.println(timeElp);*/
                    String currDate= dtf.format(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    try{
                        Date date1= (Date) sdf.parse(tStamp);
                        Date date2= (Date) sdf.parse(currDate);

                        timeElp= ((date2.getTime()- date1.getTime())/1000)%60;
                        timeElp= timeElp*1000; 
                    }catch (ParseException e) {
                        e.printStackTrace();
                    }
                    
                    //if(estimatedTimeInMilli*0.1< Math.abs(estimatedTimeInMilli-timeElp)){
                    if(estimatedTimeInMilli*0.1 + estimatedTimeInMilli<= timeElp){
                        check= true;
                        //return true;
                    }
                   // return false;
                    break;
            case 2: System.exit(0);
                    break;
        }
    }
        return check;
        
    }


    public static void main(String[] args) throws IOException
    {
        foodappp foodapppObj=new foodappp();
        resturantDao resturantDaoObj=new resturantDao();
        cartDao cartDaoObj=new cartDao();
        foodDao foodDaoObj=new foodDao();
        InputStreamReader ir =  new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(ir);
        while(true)
        {
            /* The below variabes are used as flags for visuals */
            boolean flag_view=true;
            boolean flag_authentication=true;
            boolean flag_menu=true;

            /* The below variables are session variables for storing progress */
            String sessionEmail="";
            String sessionLocation="";
            String food_items_id="";
            String food_items="";
            String food_order_quantity="";
            
            List<resturantDao> resturantList = new ArrayList<resturantDao>();// to store array of objects for the resturants table
            List<foodDao> foodList = new ArrayList<foodDao>();// to store array of objects for food database table
           
            sessionHandler cacheHandlerOBJ=new sessionHandler();
            sessionDao  cacheDaoObj=cacheHandlerOBJ.readFromCache();
            
                
            if (cacheDaoObj.getCacheStatus().equals("Y"))
            {
                    sessionEmail=cacheDaoObj.getCacheEmail();
                    flag_authentication = false;
            }
            if(!cacheDaoObj.getCacheLocation().equals(""))
            {
                    sessionLocation=cacheDaoObj.getCacheLocation().toString();
                    flag_view=false;
            }
            if(!cacheDaoObj.getCachefoodItems().equals(""))
            {
                    food_items_id=cacheDaoObj.getCachefoodItems().toString();
                    food_order_quantity=cacheDaoObj.getCacheQuantity().toString();
                    flag_menu=false;

            }
            if(!cacheDaoObj.getCacheResturantId().equals(""))
            {
                    resturant_id=cacheDaoObj.getCacheResturantId().toString();
            }
            
           
           
           if(flag_authentication)
           { //authentication flag
                  foodapppObj.authenticationDisplay();
                  int choice = Integer.parseInt(br.readLine());
                  switch (choice)
                  {
                  case 1 :
                         System.out.println("Email : ");
                         String email = br.readLine();
                         System.out.println("Password : ");
                         String password = br.readLine();
                         loginDao log=new loginDao();
                         log.setPassword(password);
                         log.setEmailId(email);
                         login(log);
                         break;

                  case 2 :
                         registrationDao reg = new registrationDao();
                         System.out.println("Username : ");
                         String f_user = br.readLine();
                         System.out.println("EmailId : ");
                         String f_emailId = br.readLine();
                         System.out.println("Password : ");
                         String f_password = br.readLine();
                         System.out.println("Confirm Password : ");
                         String f_cPassword = br.readLine();
                         reg.setUserName(f_user);
                         reg.setEmailId(f_emailId);
                         reg.setPassword(f_password);
                         reg.setSave20(1);
                         reg.setSave50(1);
                         register(reg);
                         break;

                   case 3:
                         System.exit(0);
                   default:
                         continue;
            
                   }
            }
            else if (flag_menu)
            {
                       //session stored locally on machine using Json object using file writting
                       System.out.println("                     #################### Profile ######################                \n");
                       System.out.println("1. To Close the Apllication ");
                       System.out.println("2. Logout ");
                       if(flag_view)
                       System.out.println("3. To view your choices of Resturants and food!!!\n");
                       else
                       {
                       System.out.println("3. Pick a resturant of your choice to place orders\n"); 
                       }
                       System.out.println("                                #########################                              \n");
                       int choice = Integer.parseInt(br.readLine());
            
                       switch (choice)
                       {
                       case 1:
                             System.exit(0);
                             break;
                       case 2:
                             cacheObject.addToCache("N", "", "", "", "","","");
                             break;
                       case 3:
                             if(flag_view)
                                {
                                    System.out.println("####################");
                                    System.out.println("Currently we are open in Kolkata , Bangalore");
                                    System.out.println("Please Enter your city");
                                    System.out.println("####################");
                                    String cityChoice=br.readLine().toLowerCase();
                                   
                                    if(cityChoice.equalsIgnoreCase("kolkata"))
                                    {
                                    lat=10;
                                    lon=50;
                                    }else if(cityChoice.equalsIgnoreCase("Bangalore"))
                                    {
                                    //
                                    }
                                    else
                                    {
                                        System.out.println("Sorry we will reach your destination shortly!!!!!!!");
                                        continue;
                                    }
                                    resturantList=dbconnection.fetchResturantDetils(cityChoice,lat,lon);
                                    cacheObject.addToCache("Y", sessionEmail, cityChoice, "", "","","");
                               }
                               else
                               { 
                                   //else starts  
                                   if(sessionLocation.equalsIgnoreCase("kolkata"))
                                    {
                                         lat=10;
                                         lon=50;
                                    }else if(sessionLocation.equalsIgnoreCase("Bangalore"))
                                    {
                                        //
                                    }
                                    else
                                    {
                                         System.out.println("Sorry, we are not there yet!!!!!!!");
                                         continue;
                                    }
                                 resturantList=dbconnection.fetchResturantDetils(sessionLocation,lat,lon);
                                 resturantDaoObj.resturantDisplay(resturantList);
                                 System.out.println("####################");
                                 System.out.println("Choose a resturant of your choice by entering the resturant id in the left");
                                 String resturant_id=br.readLine(); 
                                 System.out.println("####################");
                                 foodList=dbconnection.fetchFoodItems(Integer.parseInt(resturant_id));
                                 resturantDao r = (resturantDao) resturantList.get(Integer.parseInt(resturant_id)-1);
                                 distance = r.getResturant_distance();
                                 time = r.getEstimated_time();
                                 foodDaoObj.foodMenuDisplay(foodList);
                                 System.out.println("####################");
                                 System.out.println("Choose the food items from the menu to cart by entering Sl seperated by comma");
                                 food_items=br.readLine();
                                 String food_items_id_temporary[]=food_items.split(",");
            
                                 quantity = cartDaoObj.quantityCount(food_items_id_temporary);
                                 for(int i=0; i<food_items_id_temporary.length;i++)
                                 {   
                                    if(!food_items_id_extractor.contains(food_items_id_temporary[i]))
                                    {   
                                        food_items_id_extractor.add(food_items_id_temporary[i]);
                                    }
                                 }
                                food_items="";
                                String quantity_items="";
                                for(int i=0;i<food_items_id_extractor.size();i++)
                                {
                                    food_items+=food_items_id_extractor.get(i)+",";
                                }
                                for(int i=0;i<quantity.size();i++)
                                {
                                   quantity_items+=quantity.get(i)+",";
                                }
                                System.out.println("####################\n");
                                cacheObject.addToCache("Y", sessionEmail,sessionLocation,food_items, resturant_id,"",quantity_items);
                               }
                               break;
                        default:
                               continue;
                        }
          }
          else
          {   //Code for part 3
           
                                 String food_items_split[]=food_items_id.split(","); 
                                 String food_items_quantity_split[]=food_order_quantity.split(","); 
                                 foodList=dbconnection.fetchFoodItems(Integer.parseInt(resturant_id));
                                 for(int i=0; i<food_items_split.length;i++)
                                {   
                                  if(!food_items_id_extractor.contains(food_items_split[i]))
                                     {   
                                         food_items_id_extractor.add(food_items_split[i]);
                                     }
                                }
                                while(food_items_split!=null)
                                { 
                                    //food_items_split contains the id of the food 
                                    //foodList contains the objects of the food table
                                 cartDaoObj.cartDisplay(food_items_split, foodList, food_items_quantity_split);
                                 totalPrice=0.0;
                                 for(int i=0; i<food_items_split.length;i++)
                                {
                                if(food_items_split[i].equals(""))
                                    {
                                    continue;
                                    }
                                foodDao f2=foodList.get(Integer.parseInt(food_items_split[i]));
                                totalPrice += f2.getFood_price() * Integer.parseInt(food_items_quantity_split[i]);
                                }
                                System.out.println("####################");
                                System.out.println("1. Close the application");
                                System.out.println("2. Logout");
                                System.out.println("3. Remove an item from cart");
                                System.out.println("4. Remove all items from cart");
                                System.out.println("5. change the resturant");
                                System.out.println("6. Checkout");
                                System.out.println("####################");
                                int cart_choice=Integer.parseInt(br.readLine());
                                switch(cart_choice)
                                {
                                case 1:
                                      System.exit(0);
                                      break;
                                case 2:
                                      cacheObject.addToCache("N", "", "", "", "","","");
                                      food_items_split=null;
                                      break;
                                case 3:
                                      System.out.println("Enter the SL no of the items to be deleted");
                                      String food_items_to_delete=br.readLine();
                                      String food_items_to_delete_split[]=food_items_to_delete.split(",");
                                      food_items="";
                                      food_order_quantity="";
                                      for(int i=0;i<food_items_to_delete_split.length;i++)
                                      {
                                      for(int j=0;j<food_items_split.length;j++)
                                        {
                                         if(food_items_split[j].equals(food_items_to_delete_split[i]))
                                           {
                                          food_items_split[j]="";
                                          food_items_quantity_split[j]="";
                                           }
                                        }
                                      }
                                     for(int j=0;j<food_items_split.length;j++)
                                     {
                                        if(food_items_split[j].equals(""))
                                          {
                                           continue;
                                          }
                                       food_items+=food_items_split[j]+",";
                                       food_order_quantity+=food_items_quantity_split[j]+",";
                                     }
                          
                                     cacheObject.addToCache("Y", sessionEmail, sessionLocation, food_items, resturant_id,"",food_order_quantity);
                                     break;
                                case 4:
                                     cacheObject.addToCache("Y", sessionEmail, sessionLocation, "", resturant_id,"","");
                                     food_items_split=null;
                                     break;
                                case 5:
                                     cacheObject.addToCache("Y", sessionEmail, sessionLocation, "", resturant_id,"","");
                                     food_items_split=null;
                                     break;
                                case 6:
                                      if(totalPrice>=100)
                                       {   
                                       //code for part4 
                                       deliveryCharge = 5 * distance;
                                       finalPrice = totalPrice + deliveryCharge;
                                       System.out.println("Your total cart value is: "+ totalPrice+"\nDelivery charges: "+deliveryCharge);
                                       System.out.println("1. Continue to the Payment page \n2. Apply a coupon\n3. Exit");
                                       int checkoutOptions=Integer.parseInt(br.readLine());
                                       switch(checkoutOptions)
                                         {
                                            case 1: 
                                                  System.out.println("Choose a payment mode:\n1. UPI\n2. Debit Card\n3. Credit Card\n4. Net Banking\n5. Exit");
                                                  paymentPage(br, sessionEmail);
                                                  System.exit(0);
                                                  break;

                                            case 2: 
                                                  loginDao log1 = new loginDao();
                                                  log1.setEmailId(sessionEmail);
                                                  getFlags(log1);
                                                  String temp1 = flag20==1?"Applicable":"Not Applicable";
                                                  String temp2 = flag50==1?"Applicable":"Not Applicable";
                                                  System.out.println("Select a coupon\n1. SAVE20: "+ temp1+"\n2. SAVE50: "+temp2);
                                                  int couponSelector=Integer.parseInt(br.readLine());
                                                  switch(couponSelector)
                                                  {
                                                    case 1: 
                                                        if(flag20==1)
                                                        {
                                                        discount = totalPrice * 0.2;
                                                        finalPrice = totalPrice - discount + deliveryCharge;
                                                        flag20=0;
                                                        loginDao lg = new loginDao();
                                                        lg.setSave20(flag20);
                                                        lg.setSave50(flag50);
                                                        lg.setEmailId(sessionEmail);
                                                        dbconnection.insertFlags(lg);
                                                        }
                                                       System.out.println("Your total cart value is: "+ totalPrice+"\nCoupon Discount(SAVE20): "+discount+"\nDelivery Charges: "+deliveryCharge+"\nAmount to be paid: "+finalPrice);
                                                       System.out.println("Choose a payment mode:\n1. UPI\n2. Debit Card\n3. Credit Card\n4. Net Banking\n5. Exit");
                                                       paymentPage(br, sessionEmail);
                                                       break;
                                                    case 2: 
                                                       if(flag50==1)
                                                       {
                                                        discount = totalPrice * 0.5;
                                                        finalPrice = totalPrice - discount+deliveryCharge;
                                                        flag50=0;
                                                        loginDao lg1 = new loginDao();
                                                        lg1.setSave50(flag50);
                                                        lg1.setSave20(flag20);
                                                        lg1.setEmailId(sessionEmail);
                                                        dbconnection.insertFlags(lg1);
                                                       }
                                                       System.out.println("Your total cart value is: "+ totalPrice+"\nCoupon Discount(SAVE50): "+discount+"\nDelivery Charges: "+deliveryCharge+"\nAmount to be paid: "+finalPrice);
                                                       System.out.println("Choose a payment mode:\n1. UPI\n2. Debit Card\n3. Credit Card\n4. Net Banking\n5. Exit");
                                                       paymentPage(br, sessionEmail);
                                                       break;
                                                  }
                                                  System.exit(0);
                                                  break;
                                            case 3: 
                                                  System.exit(0);
                                                  break;
                                         }
                                     }
                                    else
                                    {
                                        System.out.println("Minimum order value should be 100. Your current cart value: "+ totalPrice);
                                    }
                                    break;
                                } //outer switch ends                 
                             }//while loops ends
          }//part 3 ends
        }
    }
}

