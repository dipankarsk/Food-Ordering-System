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

public final class foodappp {
   

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
           JSONObject jsonObject = new JSONObject();
           jsonObject.put("Active", "Y");
           jsonObject.put("Email", log.getEmailId());
           jsonObject.put("Location", "");
           jsonObject.put("Resturant_id", "");
           jsonObject.put("food_items", "");
           try 
           {
            FileWriter file = new FileWriter("./resources/session.json");
            file.write(jsonObject.toJSONString());
            file.close();
           }catch (IOException e) 
           {
            
           System.out.println("Error due to json file creation"+e.getMessage());
           }
         //System.out.println("JSON file created: "+jsonObject);
           
        }else{
            System.out.println("Wrong Credentials\n");
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        
        
        while(true)
        {
            Boolean flag_view=true;
            boolean flag_authentication=true;
            boolean flag_menu=true;
            String sessionEmail="";
            String sessionLocation="";
            String food_items_id="";
            String food_items="";
            List<resturant> resturantList = new ArrayList<resturant>();
            List<food> foodList = new ArrayList<food>();
            InputStreamReader ir =  new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(ir);
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

            System.out.println("\n                               Welcome to XYZ food delivery system                              \n");
            System.out.println("                  #################### Authentication ########################                    \n");
            System.out.println("1. For LogIn ");
            System.out.println("2. For SignUp  ");
            System.out.println("3. To Close the Apllication\n");
            System.out.println("                                      ####################                     ");
          
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
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Active", "N");
            jsonObject.put("Email", "");
            jsonObject.put("Location", "");
            jsonObject.put("Resturant_id", "");
            jsonObject.put("food_items", "");
            try 
            {
             FileWriter file = new FileWriter("./resources/session.json");
             file.write(jsonObject.toJSONString());
             file.close();
            }catch (IOException e) 
            {
            System.out.println("Error due to json file creation"+e.getMessage());
            }
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
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("Active", "Y");
            jsonObject1.put("Email", sessionEmail);
            jsonObject1.put("Location", cityChoice);
            jsonObject1.put("Resturant_id", "");
            jsonObject1.put("food_items", "");
           try 
           {
            FileWriter file = new FileWriter("./resources/session.json");
            file.write(jsonObject1.toJSONString());
            file.close();
           }catch (IOException e) 
           {
            
           System.out.println("Error due to json file creation"+e.getMessage());
           }
            System.out.println("                   #############  The List of Available Resturants  #################"+"\n");
            System.out.print("Resturant Id "+" "+"ResturantName"+" "
            +"ResturantCity"+" "+"ResturantAddress"+" "+"Estimated Distance"+" "+"Estimated Time of Delivery"+"\n\n");
            System.out.println();
            for(int i=0;i<resturantList.size();i++)
            {
            resturant r=resturantList.get(i);
            System.out.print(r.getResturant_id()+"\t\t"+r.getResturant_name()+"\t\t"
            +r.getResturant_city()+"\t\t"+r.getResturant_address()+"\t\t"+String.format("%.2f",r.getResturant_distance())+" Km "+String.format("%.2f",r.getEstimated_time())+" Minutes ");
            System.out.println("\n");
            }
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
                System.out.println("Sorry we will reach your destination shortly!!!!!!!");
                continue;
            }
                
            resturantList=dbconnection.fetchResturantDetils(sessionLocation,lat,lon);
                
               
              
                System.out.println("                   #############  The List of Available Resturants  #################"+"\n");
                
                System.out.print("Resturant Id "+"\t"+"Resturant Name"+"\t"
                +"Resturant City"+"\t"+"Resturant Address"+"\t"+"Estimated Distance"+"\t"+"Estimated Time of Delivery"+"\n\n");
                System.out.println();
                for(int i=0;i<resturantList.size();i++)
                {
                resturant r=resturantList.get(i);
                System.out.print(r.getResturant_id()+"\t\t"+r.getResturant_name()+"\t\t"
                +r.getResturant_city()+"\t\t"+r.getResturant_address()+"\t\t"+String.format("%.2f",r.getResturant_distance())+" Km "+String.format("%.2f",r.getEstimated_time())+" Minutes ");
                System.out.println("\n");
    
                } 
            System.out.println("####################");
            System.out.println("Choose a resturant of your choice by entering the resturant id in the left");
            String resturant_id=br.readLine();
            System.out.println("####################");
            
            foodList=dbconnection.fetchFoodItems(Integer.parseInt(resturant_id));

            System.out.println("                   #############  The Food Menu #################"+"\n");
                
                System.out.print("SL"+"\t\t"+"Food Id"+"\t\t"+"Food Name"+"\t\t"+"Food Price"+"\t\t"+"\n\n");
                System.out.println();
                for(int i=0;i<foodList.size();i++)
                {
                food f=foodList.get(i);
                System.out.print(i+"\t\t"+f.getFood_id()+"\t\t"+f.getFood_name()+"\t\t"+f.getFood_price()+"  in Rs");
                System.out.println("\n");
    
                } 
            
            System.out.println("####################");
            System.out.println("Choose the food items from the menu to cart by entering Sl seperated by comma");
            food_items=br.readLine();
            System.out.println("####################\n");



            System.out.println("                              ####################");
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("Active", "Y");
            jsonObject1.put("Email", sessionEmail);
            jsonObject1.put("Location", sessionLocation);
            jsonObject1.put("Resturant_id", resturant_id);
            jsonObject1.put("food_items", food_items);
            
            try 
            {
             FileWriter file = new FileWriter("./resources/session.json");
             file.write(jsonObject1.toJSONString());
             file.close();
            }catch (IOException e) 
             {
            System.out.println("Error due to json file creation"+e.getMessage());
             }

             ///////// end
         
            }
            break;
            default:
            continue;
            
            }
          }else
          {
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
               System.out.println("                   #############  Your order cart #################"+"\n");
               
               System.out.print("Sl"+"\t\t"+"Food Name"+"\t\t"+"Food Price"+"\t\t"+"\n\n");
               System.out.println();
               for(int i=0;i<food_items_split.length;i++)
               {
               food f=foodList.get(Integer.parseInt(food_items_split[i]));
               System.out.print(i+"\t\t"+f.getFood_name()+"\t\t"+f.getFood_price()+"  in Rs");
               System.out.println("\n");
   
               }
              
               System.out.println("####################");
               System.out.println("1. Close the application");
               System.out.println("2. Logout");
               System.out.println("3. Remove an item from cart");
               System.out.println("4. Remove all items from cart");
               System.out.println("5. change the resturant");
               System.out.println("####################");
               int cart_choice=Integer.parseInt(br.readLine());
               switch(cart_choice)
               {
               case 1:
                      System.exit(0);
                      break;
               case 2:
                       JSONObject jsonObject3 = new JSONObject();
                       jsonObject3.put("Active", "N");
                       jsonObject3.put("Email", "");
                       jsonObject3.put("Location", "");
                       jsonObject3.put("Resturant_id", "");
                       jsonObject3.put("food_items", "");
                       food_items_split=null;
                       try 
                       {
                       FileWriter file = new FileWriter("./resources/session.json");
                       file.write(jsonObject3.toJSONString());
                       file.close();
                       }catch (IOException e) 
                       {
                       System.out.println("Error due to json file creation"+e.getMessage());
                       }
                       break;
                case 3:
                      System.out.println("Enter the SL no of the items to be deleted");
                      String food_items_to_delete=br.readLine();
                      String food_items_to_delete_split[]=food_items_to_delete.split(",");
                      String new_food_items="";
                      
                      for(int i=0;i<food_items_split.length-1;i++)
                          {
                              for(int j=i+1;j<food_items_to_delete_split.length;j++)
                              {
                                 if(!food_items_split[i].equals(food_items_to_delete_split[j]))
                                 {
                                    new_food_items=food_items_split[j]+",";
                                 }
                              }
                          }
                          System.out.println("The latest items"+new_food_items);
                      break;
                case 4:
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("Active", "Y");
                    jsonObject1.put("Email", sessionEmail);
                    jsonObject1.put("Location", sessionLocation);
                    jsonObject1.put("Resturant_id", resturant_id);
                    jsonObject1.put("food_items", "");
                
                    try 
                    {
                    FileWriter file = new FileWriter("./resources/session.json");
                    file.write(jsonObject1.toJSONString());
                    file.close();
                    }catch (IOException e) 
                    {
                    System.out.println("Error due to json file creation"+e.getMessage());
                    }
                    food_items_split=null;
                    break;
                case 5:
                    JSONObject jsonObject4 = new JSONObject();
                    jsonObject4.put("Active", "Y");
                    jsonObject4.put("Email", sessionEmail);
                    jsonObject4.put("Location", sessionLocation);
                    jsonObject4.put("Resturant_id", resturant_id);
                    jsonObject4.put("food_items", "");
                
                     try 
                     {
                     FileWriter file = new FileWriter("./resources/session.json");
                     file.write(jsonObject4.toJSONString());
                     file.close();
                    }catch (IOException e) 
                    {
                    System.out.println("Error due to json file creation"+e.getMessage());
                    }
                    food_items_split=null;
                    break;
              }                   
            }
          }
        }
    }
}

