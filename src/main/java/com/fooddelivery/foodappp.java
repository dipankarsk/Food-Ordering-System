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

import org.json.simple.JSONArray;
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
           System.out.println("Login Successful....");
           System.out.println("Welcome "+log.getEmailId());
           JSONObject jsonObject = new JSONObject();
           jsonObject.put("Active", "Y");
           jsonObject.put("Email", log.getEmailId());
           
           try 
           {
            FileWriter file = new FileWriter("./resources/session.json");
            file.write(jsonObject.toJSONString());
            file.close();
           }catch (IOException e) 
           {
            
           System.out.println("Error due to json file creation"+e.getMessage());
           }
         System.out.println("JSON file created: "+jsonObject);
           
        }else{
            System.out.println("Wrong Credentials");
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        System.out.println("Welcom to XYZ food delivery system");
        
        while(true)
        {
            boolean flag=true;
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader("./resources/session.json"))
            {
                Object obj = jsonParser.parse(reader);
                JSONObject sessionList1 = (JSONObject) obj;
                //System.out.println("Session List"+sessionList1.get("Active"));
                if (sessionList1.get("Active").equals("Y"))
                {
                    flag = false;
                }
            }
            catch (FileNotFoundException e) {
               e.printStackTrace();
            } catch (IOException e) {
               e.printStackTrace();
            } catch (ParseException e) {
            e.printStackTrace();
           }
           if(flag){
            System.out.println("####################");
            System.out.println("1. For LogIn ");
            System.out.println("2. For SignUp  ");
            System.out.println("3. To Close the Apllication");
            System.out.println("####################");
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
        }else{
            //session stored
            System.out.println("####################");
            System.out.println("1. To Close the Apllication ");
            System.out.println("2. Logout ");
            System.out.println("3. To view your choices of Resturants and food!!!");
            System.out.println("####################");
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
            System.out.println("####################");
            System.out.println("Currently we are open in Kolkata,Delhi,Bangalore,Hyderabad");
            System.out.println("Please Enter your city");
            System.out.println("####################");
            String cityChoice=br.readLine().toLowerCase();
            int lat=0;
            int lon=0;
            if(cityChoice.equalsIgnoreCase("kolkata"))
            {
                    lat=10;
                    lon=50;
            }
            List<resturant> arrayList = new ArrayList<resturant>();
            arrayList=dbconnection.fetchResturantDetils(cityChoice,lat,lon);
            System.out.println("#############The List of Available Resturants....#######");
            for(int i=0;i<arrayList.size();i++){
            resturant r=arrayList.get(i);
            System.out.print(r.getResturant_id()+" "+r.getResturant_name()+" "
            +r.getResturant_city()+" "+r.getResturant_address()+" "+String.format("%.2f",r.getResturant_distance())+" Metres "+String.format("%.2f",r.getEstimated_time())+" Minutes ");
            System.out.println();
            }
            break;
            default:
            continue;
            
            }
          }
        }

    }
}

