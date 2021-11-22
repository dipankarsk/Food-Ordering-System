package com.fooddelivery;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
           jsonObject.put("Email", "dipankarsrkr23@gmail.com");
           JSONArray sessionList = new JSONArray();
           sessionList.add(jsonObject);
           
           try 
           {
            FileWriter file = new FileWriter("/Users/dipankar/Desktop/fooddelivery/src/resources/session.json");
            file.write(sessionList.toJSONString());
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
            
            System.out.println("####################");
            System.out.println("1. For LogIn ");
            System.out.println("2. For SignUp  ");
            System.out.println("3. To Close the Apllication");
            System.out.println("####################");
            InputStreamReader ir =  new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(ir);
            int choice = Integer.parseInt(br.readLine());
            boolean flag=true;
            
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader("/Users/dipankar/Desktop/fooddelivery/src/resources/session.json"))
            {
                Object obj = jsonParser.parse(reader);
                JSONArray sessionList1 = (JSONArray) obj;
                System.out.println("Session List"+sessionList1);
                
            }
            catch (FileNotFoundException e) {
               e.printStackTrace();
            } catch (IOException e) {
               e.printStackTrace();
            } catch (ParseException e) {
            e.printStackTrace();
           }

            if(flag){
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
            switch (choice)
            {
            

            case 1:
               System.exit(0);
               break;
            case 2:
                //pass
                break;

            default:
            continue;
            
            }
          }
        }

    }
}

