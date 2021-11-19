package com.fooddelivery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.fooddelivery.Authentication.registration;
import com.fooddelivery.Authentication.login;
import com.fooddelivery.Database.dbconnection;

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
        }else{
            System.out.println("Wrong Credentials");
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        System.out.println("Welcom to XYZ food delivery system");
        while(true)
        {
            System.out.println("Do you want to 1)Login 2)Register3)exit application");
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

    }
}

