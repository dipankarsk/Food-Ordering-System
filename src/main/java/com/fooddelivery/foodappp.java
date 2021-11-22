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
            boolean flag=true;
            String sessionEmail="";
            String sessionLocation="";
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader("./resources/session.json"))
            {
                Object obj = jsonParser.parse(reader);
                JSONObject sessionList1 = (JSONObject) obj;
                //System.out.println("Session List"+sessionList1.get("Active"));
                
                if (sessionList1.get("Active").equals("Y"))
                {
                    sessionEmail=sessionList1.get("Email").toString();
                    flag = false;
                }
                if(!sessionList1.get("Location").equals(""))
                {
                    sessionLocation=sessionList1.get("Location").toString();
                    flag_view=false;
                }
            }
            catch (FileNotFoundException e) {
               e.printStackTrace();
            } catch (IOException e) {
               e.printStackTrace();
            } catch (ParseException e) {
            e.printStackTrace();
           }
           if(flag)
           {
            System.out.println("\n                               Welcome to XYZ food delivery system                              \n");
            System.out.println("                  #################### Authentication ########################                    \n");
            System.out.println("1. For LogIn ");
            System.out.println("2. For SignUp  ");
            System.out.println("3. To Close the Apllication\n");
            System.out.println("                                      ####################                     ");
            InputStreamReader ir =  new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(ir);
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
        else
        {
            
            //session stored locally on machine using Json object using file writting
            
            
            
            System.out.println("                     #################### Profile ######################                \n");
            System.out.println("1. To Close the Apllication ");
            System.out.println("2. Logout ");
            if(flag_view)
            System.out.println("3. To view your choices of Resturants and food!!!\n");
            else{
            System.out.println("3. Choose a resturant of your choice \n"); 
            }
            System.out.println("                                #########################                              \n");
            InputStreamReader ir =  new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(ir);
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


            List<resturant> arrayList = new ArrayList<resturant>();
            arrayList=dbconnection.fetchResturantDetils(cityChoice,lat,lon);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("Active", "Y");
            jsonObject1.put("Email", sessionEmail);
            jsonObject1.put("Location", cityChoice);
            jsonObject1.put("Resturant_id", "");
           
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
            for(int i=0;i<arrayList.size();i++)
            {
            resturant r=arrayList.get(i);
            System.out.print(r.getResturant_id()+"\t\t"+r.getResturant_name()+"\t\t"
            +r.getResturant_city()+"\t\t"+r.getResturant_address()+"\t\t"+String.format("%.2f",r.getResturant_distance())+" Metres "+String.format("%.2f",r.getEstimated_time())+" Minutes ");
            System.out.println("\n");

            }

            }
            else
            { //else starts
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
                List<resturant> arrayList = new ArrayList<resturant>();
                arrayList=dbconnection.fetchResturantDetils(sessionLocation,lat,lon);
                
               
              
                System.out.println("                   #############  The List of Available Resturants  #################"+"\n");
                
                System.out.print("Resturant Id "+" "+"ResturantName"+" "
                +"ResturantCity"+" "+"ResturantAddress"+" "+"Estimated Distance"+" "+"Estimated Time of Delivery"+"\n\n");
                System.out.println();
                for(int i=0;i<arrayList.size();i++)
                {
                resturant r=arrayList.get(i);
                System.out.print(r.getResturant_id()+"\t\t"+r.getResturant_name()+"\t\t"
                +r.getResturant_city()+"\t\t"+r.getResturant_address()+"\t\t"+String.format("%.2f",r.getResturant_distance())+" Metres "+String.format("%.2f",r.getEstimated_time())+" Minutes ");
                System.out.println("\n");
    
                } 
            System.out.println("####################");
            System.out.println("Choose a resturant of your choice by entering the resturant id in the left");
            String resturant_id=br.readLine();
            System.out.println("####################");
            List<food> foodList = new ArrayList<food>();
            foodList=dbconnection.fetchFoodItems(Integer.parseInt(resturant_id));

            System.out.println("                   #############  The Food Menu #################"+"\n");
                
                System.out.print("Food Id"+" "+"FoodName"+" "+"FoodPrice"+" "+"\n\n");
                System.out.println();
                for(int i=0;i<foodList.size();i++)
                {
                food f=foodList.get(i);
                System.out.print(f.getFood_id()+"\t\t"+f.getFood_name()+"\t\t"+f.getFood_price()+"\t\t"+"in Rs");
                System.out.println("\n");
    
                } 
            System.out.println("                              ####################");
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("Active", "Y");
            jsonObject1.put("Email", sessionEmail);
            jsonObject1.put("Location", sessionLocation);
            jsonObject1.put("Resturant_id", resturant_id);


            try 
            {
             FileWriter file = new FileWriter("./resources/session.json");
             file.write(jsonObject1.toJSONString());
             file.close();
            }catch (IOException e) 
             {
            System.out.println("Error due to json file creation"+e.getMessage());
             }
            }
            break;
            default:
            continue;
            
            }
          }
        }

    }
}

