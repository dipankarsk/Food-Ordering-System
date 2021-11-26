package com.fooddelivery;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import com.fooddelivery.Authentication.registration;
import com.fooddelivery.Authentication.login;
import com.fooddelivery.Database.dbconnection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Arrays;

public final class foodappp {
    static int totalPrice = 0;
    static List<String> food_items_id_extractor = new ArrayList<String>();
    static List<Integer> quantity = new ArrayList<Integer>();
    public void authenticationDisplay()
    {
        System.out.println("\n                               Welcome to XYZ food delivery system                              \n");
        System.out.println("                  #################### Authentication ########################                    \n");
        System.out.println("1. For LogIn ");
        System.out.println("2. For SignUp  ");
        System.out.println("3. To Close the Apllication\n");
        System.out.println("                                      ####################                     ");
    }
    public void resturantDisplay(List resturantList)
    {
        System.out.println("                   #############  The List of Available Resturants  #################"+"\n");
        System.out.print("Resturant Id "+" "+"ResturantName"+" "
        +"ResturantCity"+" "+"ResturantAddress"+" "+"Estimated Distance"+" "+"Estimated Time of Delivery"+"\n\n");
        System.out.println();
        for(int i=0;i<resturantList.size();i++)
        {
        resturant r=(resturant) resturantList.get(i);
        System.out.print(r.getResturant_id()+"\t\t"+r.getResturant_name()+"\t\t"
        +r.getResturant_city()+"\t\t"+r.getResturant_address()+"\t\t"+String.format("%.2f",r.getResturant_distance())+" Km "+String.format("%.2f",r.getEstimated_time())+" Minutes ");
        System.out.println("\n");
        }
    }
    public void foodMenuDisplay(List foodList)
    {
                System.out.println("                   #############  The Food Menu #################"+"\n");
                
                System.out.print("SL"+"\t\t"+"Food Name"+"\t\t"+"Food Price"+"\t\t"+"\n\n");
                System.out.println();
                for(int i=0;i<foodList.size();i++)
                {
                food f=(food) foodList.get(i);
                System.out.print(i+"\t\t"+f.getFood_name()+"\t\t"+f.getFood_price()+"  in Rs");
                System.out.println("\n");
    
                } 
    }
    public static void addToCache(String cacheStatus, String cacheEmail,String cacheLocation, String cachefoodItems, String cacheResturantId)
    {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Active", cacheStatus);
            jsonObject.put("Email", cacheEmail);
            jsonObject.put("Location", cacheLocation);
            jsonObject.put("Resturant_id", cacheResturantId);
            jsonObject.put("food_items", cachefoodItems);
            try 
            {
             FileWriter file = new FileWriter("./resources/session.json");
             file.write(jsonObject.toJSONString());
             file.close();
            }catch (IOException e) 
            {
            System.out.println("Error due to json file creation"+e.getMessage());
            }

    }
    public static void register (registration reg)
    {
        dbconnection.insertUserData(reg);

    }
    public static void login (login log)
    {
        boolean credentialStatus=dbconnection.logincheck(log);
        if(credentialStatus)
        {
           System.out.println("Login Successful....\n");
           System.out.println("Welcome "+log.getEmailId()+"\n");
           addToCache("Y", log.getEmailId(), "", "", "");
        }else{
            System.out.println("Wrong Credentials\n");
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        foodappp foodapppObj=new foodappp();
        InputStreamReader ir =  new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(ir);
        while(true)
        {
            boolean flag_view=true;
            boolean flag_authentication=true;
            boolean flag_menu=true;
            String sessionEmail="";
            String sessionLocation="";
            String food_items_id="";
            String food_items="";
            
            List<resturant> resturantList = new ArrayList<resturant>();// to store array of objects for the resturants table
            List<food> foodList = new ArrayList<food>();// to store array of objects for food database table

            
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader("./resources/session.json"))
            {
                Object obj = jsonParser.parse(reader);
                JSONObject sessionList1 = (JSONObject) obj;
                //System.out.println("Session List"+sessionList1.get("Active"));
                
                if (sessionList1.get("Active").equals("Y"))
                {
                    sessionEmail=sessionList1.get("Email").toString();
                    flag_authentication = false;
                }
                if(!sessionList1.get("Location").equals(""))
                {
                    sessionLocation=sessionList1.get("Location").toString();
                    flag_view=false;
                }
                if(!sessionList1.get("food_items").equals(""))
                {
                    food_items_id=sessionList1.get("food_items").toString();
                    flag_menu=false;

                }
            }
            catch (FileNotFoundException e) {
               e.printStackTrace();
            } catch (IOException e) {
               e.printStackTrace();
            } catch (ParseException e) {
            e.printStackTrace();
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
                login log=new login();
                log.setPassword(password);
                log.setEmailId(email);
                login(log);
                break;

            case 2 :
                registration reg = new registration();
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
            else{
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
               addToCache("N", "", "", "", "");
                break;
            case 3:
            if(flag_view)
            {
            System.out.println("####################");
            System.out.println("Currently we are open in Kolkata , Delhi , Bangalore , Hyderabad");
            System.out.println("Please Enter your city");
            System.out.println("####################");
            String cityChoice=br.readLine().toLowerCase();
            int lat=0;
            int lon=0;
            if(cityChoice.equalsIgnoreCase("kolkata"))
            {
                    lat=10;
                    lon=50;
            }else if(cityChoice.equalsIgnoreCase("Delhi"))
            {
                   //
            }
            else if(cityChoice.equalsIgnoreCase("Bangalore"))
            {
                //
            }
            else if(cityChoice.equalsIgnoreCase("Hyderabad"))
            {
                //
            }
            else
            {
                System.out.println("Sorry we will reach your destination shortly!!!!!!!");
                continue;
            }


            
            resturantList=dbconnection.fetchResturantDetils(cityChoice,lat,lon);

            addToCache("Y", sessionEmail, cityChoice, "", "");

            
            }
            else
            { //else starts

            //start    
            int lat=0;
            int lon=0;

            if(sessionLocation.equalsIgnoreCase("kolkata"))
            {
                    lat=10;
                    lon=50;
            }else if(sessionLocation.equalsIgnoreCase("Delhi"))
            {
                   //
            }
            else if(sessionLocation.equalsIgnoreCase("Bangalore"))
            {
                //
            }
            else if(sessionLocation.equalsIgnoreCase("Hyderabad"))
            {
                //
            }
            else
            {
                System.out.println("Sorry, we are not there yet!!!!!!!");
                continue;
            }
                
            resturantList=dbconnection.fetchResturantDetils(sessionLocation,lat,lon);
            foodapppObj.resturantDisplay(resturantList);
            System.out.println("####################");
            System.out.println("Choose a resturant of your choice by entering the resturant id in the left");
            String resturant_id=br.readLine();
            System.out.println("####################");
            
            foodList=dbconnection.fetchFoodItems(Integer.parseInt(resturant_id));

            foodapppObj.foodMenuDisplay(foodList);
            
            System.out.println("####################");
            System.out.println("Choose the food items from the menu to cart by entering Sl seperated by comma");
            food_items=br.readLine();
            String food_items_new="";
            String food_items_id_temporary[]=food_items.split(",");
            //String food_items_id_extractor[];
            quantity = returnCount(food_items_id_temporary);
            for(int i=0; i<food_items_id_temporary.length;i++)
            {   
                if(!food_items_id_extractor.contains(food_items_id_temporary[i]))
                {   
                    food_items_id_extractor.add(food_items_id_temporary[i]);
               }
                
            }
            System.out.println(food_items_id_extractor);
            System.out.println("Quantity:"+ quantity);
            for(int i=0;i<food_items_id_extractor.size();i++)
            {

               //System.out.println(food_items_id_extractor.get(i));
               food f1=foodList.get(Integer.parseInt(food_items_id_extractor.get(i)));
               food_items_new=food_items_new+f1.getFood_id()+",";
               //System.out.println(food_items_new);
            }
           
            System.out.println("####################\n");



            System.out.println("                              ####################");
            addToCache("Y", sessionEmail, sessionLocation, food_items, resturant_id);

             ///////// end
         
            }
            break;
            default:
            continue;
            
            }
          }
          else
          {   //Code for part 3
            String resturant_id="";
            try (FileReader reader = new FileReader("./resources/session.json"))
            {
                Object obj = jsonParser.parse(reader);
                JSONObject sessionList1 = (JSONObject) obj;

                
                if(!sessionList1.get("Resturant_id").equals(""))
                {
                    resturant_id=sessionList1.get("Resturant_id").toString();
                }
                if(!sessionList1.get("food_items").equals(""))
                {
                    food_items_id=sessionList1.get("food_items").toString();                   

                }else{
                    flag_menu=true;
                }
            }
            catch (FileNotFoundException e) {
               e.printStackTrace();
            } catch (IOException e) {
               e.printStackTrace();
            } catch (ParseException e) {
            e.printStackTrace();
           }
            String food_items_split[]=food_items_id.split(",");
            foodList=dbconnection.fetchFoodItems(Integer.parseInt(resturant_id));
            
            while(food_items_split!=null)
            { 
               //part 3 work area 2
               //food_items_split contains the id of the food 
               //foodList contains the objects of the food table
               System.out.println("                   #############  Your order cart #################"+"\n");
               
               System.out.print("Sl"+"\t\t"+"Food Name"+"\t\t"+"Food Price"+"\t\t"+"\n\n");
               System.out.println();
               for(int i=0;i<food_items_split.length;i++)
               {
               if(food_items_split[i].equals(""))
               {
                   continue;
               }
               food f=foodList.get(Integer.parseInt(food_items_split[i]));
               System.out.print(food_items_split[i]+"\t\t"+f.getFood_name()+"\t\t"+f.getFood_price()+"  in Rs");
               System.out.println("\n");
               
               }
               for(int i=0; i<food_items_id_extractor.size();i++)
               {
                food f2=foodList.get(Integer.parseInt(food_items_id_extractor.get(i)));
                totalPrice += f2.getFood_price() * quantity.get((i));
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
                       addToCache("N", "", "", "", "");
                       food_items_split=null;
                       break;
               case 3:
                      System.out.println("Enter the SL no of the items to be deleted");
                      String food_items_to_delete=br.readLine();
                      String food_items_to_delete_split[]=food_items_to_delete.split(",");
                  
                      
                      for(int i=0;i<food_items_to_delete_split.length;i++)
                          {
                              for(int j=0;j<food_items_split.length;j++)
                              {
                                 if(food_items_split[j].equals(food_items_to_delete_split[i]))
                                 {
                                    food_items_split[j]="";
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
                          }
                          addToCache("Y", sessionEmail, sessionLocation, food_items, resturant_id);
                      break;
                case 4:
                    addToCache("Y", sessionEmail, sessionLocation, "", resturant_id);
                    food_items_split=null;
                    break;
                case 5:
                    addToCache("Y", sessionEmail, sessionLocation, "", resturant_id);
                    food_items_split=null;
                    break;
                case 6:
                    if(totalPrice>=100)
                    {
                        System.out.println("Your total cart value is: "+ totalPrice);
                        System.out.println("1. Continue to the Payment page \n2. Exit");
                        int checkoutOptions=Integer.parseInt(br.readLine());
                        switch(checkoutOptions)
                        {
                            case 1: System.out.println("Choose a payment mode:\n1. UPI\n2. Debit Card\n3. Credit Card\n4. Net Banking\n5. Exit");
                                    int paymentModeOptions=Integer.parseInt(br.readLine());
                                    switch(paymentModeOptions)
                                    {
                                        case 1: System.out.println("Payment Done.\nPaid: "+totalPrice+"\nPayment mode: UPI");
                                                break;
                                        case 2: System.out.println("Payment Done.\nPaid: "+totalPrice+"\nPayment mode: Debit Card");
                                                break;
                                        case 3: System.out.println("Payment Done.\nPaid: "+totalPrice+"\nPayment mode: Credit Card");
                                                break;
                                        case 4: System.out.println("Payment Done.\nPaid: "+totalPrice+"\nPayment mode: Net Banking");
                                                break;
                                        case 5: System.exit(0);
                                                break;
                                        default: System.out.println("Invalid Selection");
                                    }
                                    break;
                            case 2: System.exit(0);;
                                    break;
                        }

                    }
                    else{
                        System.out.println("Minimum order value should be 100. Your current cart value: "+ totalPrice);
                    }
                    break;
                    
              }                   
            }//part 3 ends here
          }
        }
    }
public static List<Integer> returnCount(String array[])
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

