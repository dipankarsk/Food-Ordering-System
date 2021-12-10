package com.fooddelivery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.fooddelivery.Authentication.registrationDao;
import com.fooddelivery.Authentication.loginDao;
import com.fooddelivery.Database.DbHandler;

/**  
 * The main class 
*/

public final class foodappp {
    static double totalPrice = 0;
    static double discount = 0;
    static double finalPrice = 0;
    static double distance = 0;
    static double deliveryCharge = 0;
    static int flag20, flag50;
    static int listOfRestaurants;
    static double actualTime=0;
    static double time=0;
    static double originalEstimatedTime=0.0;
    static int lat=0;
    static int lon=0;
    static String resturant_id="";
    static String food_items_split[];
    static String food_items_quantity_split[];
    static List<Integer> food_items_id_extractor =null;//!< arraylist to store the food ids stored at any instance
    static List<Integer> food_items_quantity_extractor = null; //!< arraylist to store quantities of each food items inside the cart
    static DbHandler dbconnection=new DbHandler();
    static List<resturantDao> resturantList;
    static List<foodDao> foodList;

    public static void filler()//!Filler for output pattern
    {
        System.out.println("###############################");
    }
    public void authenticationDisplay()//!Display's the Authentication page
    {
        System.out.println("\n                               Welcome to Combida food Ordering system                              \n");
        System.out.println("                  #################### Authentication ########################                    \n");
        System.out.println("1. For LogIn ");
        System.out.println("2. For SignUp  ");
        System.out.println("3. To Close the Apllication\n");
        System.out.println("                                      ####################                     ");
    }
    public void register (registrationDao reg)//!To put registration data to database
    {
        dbconnection.insertUserData(reg);

    }
    public void getFlags(loginDao log)//!To Fetch the flags
    {
            loginDao l = dbconnection.fetchUserData(log);
            flag20 = l.getSave20();
            flag50 = l.getSave50();
    }
    public void login (loginDao log)//!Checking if the login is sucessfull or not
    {
        sessionHandler cacheObject=new sessionHandler();
        boolean credentialStatus=dbconnection.logincheck(log);
        if(credentialStatus)
        {
           System.out.println("Login Successful....\n");
           System.out.println("Welcome "+log.getEmailId()+"\n");
           cacheObject.addToCache("Y", log.getEmailId(), "", "", "","","");
        }else{
            System.out.println("Wrong Credentials\n");
        }
    }
    public static void main(String[] args) throws IOException
    {
        foodappp foodapppObj=new foodappp();
        resturantDao resturantDaoObj=new resturantDao();
        cartDao cartDaoObj=new cartDao();
        foodDao foodDaoObj=new foodDao();
        sessionHandler cacheObject=new sessionHandler();
        InputStreamReader ir =  new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(ir);
       
        while(true)
        {
            
            boolean flag_view=true; /*! an integer value */
            boolean flag_authentication=true;
            boolean flag_menu=true;

            /* The below variables are session variables for storing progress */
            String sessionEmail=""; 
            String sessionLocation="";
            String food_items_id="";
            String food_items="";
            String food_items_whishlist="";
            String quantity_items="";
            String food_order_quantity="";
            String sessionWhishList="";
            
            resturantList = new ArrayList<resturantDao>();// to store array of objects for the resturants table
            foodList = new ArrayList<foodDao>();// to store array of objects for food database table
           
            sessionHandler cacheHandlerOBJ=new sessionHandler();
            sessionDao  cacheDaoObj=cacheHandlerOBJ.readFromCache();
                
            if (cacheDaoObj.getCacheStatus().equals("Y"))
            {
                    sessionEmail=cacheDaoObj.getCacheEmail();
                    flag_authentication = false;
            }
            if(!cacheDaoObj.getCacheLocation().equals(""))
            {
                    //System.out.println("Inside ");
                    sessionLocation=cacheDaoObj.getCacheLocation().toString();
                    flag_view=false;
            }
            if(!cacheDaoObj.getCachefoodItems().equals(""))
            {
                    //System.out.println("Inside cache");
                    food_items_id=cacheDaoObj.getCachefoodItems().toString();
                    food_order_quantity=cacheDaoObj.getCacheQuantity().toString();
                    flag_menu=false;
            }
            if(!cacheDaoObj.getCacheResturantId().equals(""))
            {
                    resturant_id=cacheDaoObj.getCacheResturantId().toString();
            }
            if(!cacheDaoObj.getcacheWhishList().equals(""))
            {
                    sessionWhishList=cacheDaoObj.getcacheWhishList().toString();
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
                         foodapppObj.login(log);
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
                         if(!f_cPassword.equals(f_password))
                         {
                             System.out.println("Passwords do not match");
                             continue;
                         }
                         reg.setUserName(f_user);
                         reg.setEmailId(f_emailId);
                         reg.setPassword(f_password);
                         reg.setSave20(1);
                         reg.setSave50(1);
                         foodapppObj.register(reg);
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
                                        lat=10;
                                        lon=50;
                                    }
                                    else
                                    {
                                        System.out.println("Sorry we will reach your destination shortly!!!!!!!");
                                        continue;
                                    }
                                    resturantList=dbconnection.fetchUserData(cityChoice,lat,lon);
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
                                         lat=40;
                                         lon=80;
                                    }
                                    else
                                    {
                                         System.out.println("Sorry, we are not there yet!!!!!!!");
                                         continue;
                                    }
                                 resturantList=dbconnection.fetchUserData(sessionLocation,lat,lon);
                                 resturantDaoObj.resturantDisplay(resturantList);
                                 filler();
                                 System.out.println("Add a resturant of your choice by entering the resturant id in the left");
                                 String resturant_id=br.readLine(); 
                                 filler();
                                 foodList=dbconnection.fetchUserData(Integer.parseInt(resturant_id));
                                 resturantDao r = (resturantDao) resturantList.get(Integer.parseInt(resturant_id)-1);
                                 distance = r.getResturant_distance();
                                 time = r.getEstimated_time();
                                 foodDaoObj.foodMenuDisplay(foodList);
                                 filler();
                                 System.out.println("Add the food items from the menu to cart by entering Sl seperated by comma");
                                 food_items=br.readLine();
                                 filler();
                                 food_items_id_extractor=new ArrayList<Integer>();
                                 food_items_quantity_extractor = new ArrayList<Integer>();
                                 food_items_quantity_extractor = cartDaoObj.quantityCount(food_items.split(","));
                                 for(int i=0; i<food_items.split(",").length;i++)
                                 {   
                                    if(!food_items_id_extractor.contains(Integer.parseInt(food_items.split(",")[i])))
                                    {   
                                        food_items_id_extractor.add(Integer.parseInt(food_items.split(",")[i]));
                                    }
                                 }
                                food_items="";
                                quantity_items="";
                                for(int i=0;i<food_items_id_extractor.size();i++)
                                {
                                    food_items+=food_items_id_extractor.get(i)+",";
                                }
                                for(int i=0;i<food_items_quantity_extractor.size();i++)
                                {
                                    quantity_items+=food_items_quantity_extractor.get(i)+",";
                                }
                                filler();
                                cacheObject.addToCache("Y", sessionEmail,sessionLocation,food_items, resturant_id,"",quantity_items);
                                //wishlist
                                System.out.println("##################### WhisList Option #########################");
                                System.out.println("1.WishList\n2.continue with cart");
                                filler();
                                int whishListChoice=Integer.parseInt(br.readLine());
                                switch(whishListChoice)
                                {
                                    case 1:
                                           //check if list exist 
                                           String food_item_whislist_combined="";
                                           wishlistDao wd=new wishlistDao();
                                           wd.setEmail(sessionEmail);
                                           food_item_whislist_combined=wd.wishListConfigurator(wd);
                                           //display list if exist else message no items
                                           filler();
                                           System.out.println("1.to add to the list from food menu above\n2.continue with cart\n");
                                           filler();
                                           int whishListChoiceToAdd=Integer.parseInt(br.readLine());
                                           if(whishListChoiceToAdd==2)
                                           {
                                             // do nothing
                                           }else{
                                            System.out.println("Add the food items from the menu to wishlist by entering Sl seperated by comma");
                                            food_items_whishlist=br.readLine();
                                            for(int i=0;i<food_items_whishlist.split(",").length;i++)
                                            {
                                                //System.out.println(foodList.get(Integer.parseInt(food_items_whishlist.split(",")[i])).getFood_id());
                                                 food_item_whislist_combined+=foodList.get(Integer.parseInt(food_items_whishlist.split(",")[i])).getFood_id()+",";
                                            }
                                            wd.setFoodId(food_item_whislist_combined);
                                            //System.out.println(food_item_whislist_combined);
                                            wd.addToWishList(wd);

                                           }
                                           break;
                                    case  2:
                                           break;
                                    default:
                                            break;
                                }
                               }
                               break;
                        default:
                               continue;
                        }
          }
          else
          {   //Code for part 3
                                 food_items_split=null;
                                 food_items_quantity_split=null;

                                 food_items_split=food_items_id.split(","); 
                                 food_items_quantity_split=food_order_quantity.split(","); 

                                 foodList=dbconnection.fetchUserData(Integer.parseInt(resturant_id));
                                 food_items_id_extractor=new ArrayList<Integer>();
                                 food_items_quantity_extractor=new ArrayList<Integer>();
                                 
                                 for(int i=0; i<food_items_split.length;i++)
                                {   
                                  if(!food_items_id_extractor.contains(Integer.parseInt(food_items_split[i])))
                                     {   
                                         food_items_id_extractor.add(Integer.parseInt(food_items_split[i]));
                                         food_items_quantity_extractor.add(Integer.parseInt(food_order_quantity.split(",")[i]));
                                     }
                                }
                                if(sessionLocation.equalsIgnoreCase("kolkata"))
                                {
                                         lat=10;
                                         lon=50;
                                }
                                else if(sessionLocation.equalsIgnoreCase("Bangalore"))
                                {
                                         lat=40;
                                         lon=80;
                                }
                                while(food_items_split!=null)
                                { 
                                 cartDaoObj.cartDisplay(food_items_id_extractor, foodList, food_items_quantity_extractor);
                                 totalPrice=0.0;
                                 for(int i=0; i<food_items_id_extractor.size();i++)
                                {
                                foodDao f2=foodList.get(food_items_id_extractor.get(i));
                                totalPrice += f2.getFood_price() * food_items_quantity_extractor.get(i);
                                }
                                cartDaoObj.cartOptions();
                                int cart_choice=Integer.parseInt(br.readLine());
                                cartDaoObj.setEmail(sessionEmail);
                                cartDaoObj.setResturant_id(Integer.parseInt(resturant_id));
                                cartDaoObj.setResturant_city(sessionLocation);
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
                                        List<List<Integer>> l1=new ArrayList<>();
                                        l1=cartDaoObj.removeItems(br,cartDaoObj,food_items_id_extractor,food_items_quantity_extractor);
                                        food_items_id_extractor=l1.get(0);
                                        food_items_quantity_extractor=l1.get(1);
                                        break;
                                case 4:
                                        List<List<Integer>> l2=new ArrayList<>();
                                        l2=cartDaoObj.addItems(br,cartDaoObj,food_items_id_extractor,food_items_quantity_extractor,foodList);
                                        food_items_id_extractor=l2.get(0);
                                        food_items_quantity_extractor=l2.get(1);
                                        break;
                                case 5:
                                        cartDaoObj.removeAllItems(cartDaoObj);
                                        food_items_split=null;
                                        food_items_id_extractor=null;
                                        break;
                                case 6: 
                                        cartDaoObj.changeResturant(cartDaoObj);
                                        food_items_split=null;
                                        food_items_id_extractor=null;
                                        break;
                                case 7:
                                      if(totalPrice>=100)
                                       {   
                                       //code for payment and tracking
                                       //System.out.println(lat+" "+lon);
                                       resturantList=null;
                                       resturantList=dbconnection.fetchUserData(sessionLocation, lat, lon);
                                       resturantDao estimatedTimeObj=resturantList.get(Integer.parseInt(resturant_id)-1);
                                       originalEstimatedTime=estimatedTimeObj.getEstimated_time();
                                       deliveryCharge = 5 * estimatedTimeObj.getResturant_distance();
                                       if(sessionLocation.equalsIgnoreCase("kolkata"))
                                       {
                                         lat=10;
                                         lon=50;
                                       }else if(sessionLocation.equalsIgnoreCase("Bangalore"))
                                       {
                                         lat=40;
                                         lon=80;
                                       }
                                       finalPrice = totalPrice + deliveryCharge;
                                       System.out.println("Your total cart value is: "+ totalPrice+"\nDelivery charges: "+deliveryCharge);
                                       System.out.println("1. Continue to the Payment page \n2. Apply a coupon\n3. Exit");
                                       int checkoutOptions=Integer.parseInt(br.readLine());
                                       paymentDao p=new paymentDao();
                                       p.setEmail(sessionEmail);
                                       p.setOriginalEstimateTime(originalEstimatedTime);
                                       switch(checkoutOptions)
                                         {
                                            case 1:
                                                  filler(); 
                                                  p.paymentOptions();
                                                  p.setFinalPrice(finalPrice);
                                                  food_items_id_extractor=p.paymentPage(br,food_items_id_extractor,foodList,p);
                                                  if(food_items_id_extractor==null)
                                                  {
                                                    food_items_split=null;  
                                                  }
                                                  break;
                                            case 2: 
                                                  loginDao log1 = new loginDao();
                                                  log1.setEmailId(sessionEmail);
                                                  foodapppObj.getFlags(log1);
                                                  String temp1 = flag20==1?"Applicable":"Not Applicable";
                                                  String temp2 = flag50==1?"Applicable":"Not Applicable";
                                                  filler();
                                                  System.out.println("Select a coupon\n1. SAVE20: "+ temp1+"\n2. SAVE50: "+temp2);
                                                  filler();
                                                  int couponSelector=Integer.parseInt(br.readLine());
                                                  switch(couponSelector)
                                                  {
                                                    case 1: 
                                                    loginDao lg = new loginDao();
                                                        if(flag20==1)
                                                        {
                                                        discount = totalPrice * 0.2;
                                                        finalPrice = totalPrice - discount + deliveryCharge;
                                                        flag20=0;
                                                        lg.setSave20(flag20);
                                                        lg.setSave50(flag50);
                                                        lg.setEmailId(sessionEmail);
                                                        }
                                                        filler();
                                                        System.out.println("Your total cart value is: "+ totalPrice+"\nCoupon Discount(SAVE20): "+discount+"\nDelivery Charges: "+deliveryCharge+"\nAmount to be paid: "+finalPrice);
                                                        p.setFinalPrice(finalPrice);
                                                        p.paymentOptions();
                                                        filler();
                                                        food_items_id_extractor=p.paymentPage(br,food_items_id_extractor,foodList,p);
                                                        if(food_items_id_extractor==null)
                                                        {
                                                         food_items_split=null;  
                                                        }
                                                        dbconnection.updateUserData(lg);
                                                        break;
                                                    case 2: 
                                                       loginDao lg1 = new loginDao();
                                                       if(flag50==1)
                                                       {
                                                        discount = totalPrice * 0.5;
                                                        finalPrice = totalPrice - discount+deliveryCharge;
                                                        flag50=0;
                                                        lg1.setSave50(flag50);
                                                        lg1.setSave20(flag20);
                                                        lg1.setEmailId(sessionEmail);
                                                       }
                                                        filler();
                                                        System.out.println("Your total cart value is: "+ totalPrice+"\nCoupon Discount(SAVE50): "+discount+"\nDelivery Charges: "+deliveryCharge+"\nAmount to be paid: "+finalPrice);
                                                        System.out.println("Choose a payment mode:\n1. UPI\n2. Debit Card\n3. Credit Card\n4. Net Banking\n5. Exit");
                                                        filler();
                                                        p.setFinalPrice(finalPrice);
                                                        food_items_id_extractor=p.paymentPage(br,food_items_id_extractor,foodList,p);
                                                        if(food_items_id_extractor==null)
                                                        {
                                                         food_items_split=null;  
                                                        }
                                                        dbconnection.updateUserData(lg1);
                                                        break;
                                                  }
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

