package com.fooddelivery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fooddelivery.Authentication.registration;
import com.fooddelivery.Database.dbconnection;

public final class foodappp {
   

    public static void register ()
    {
        dbconnection.insertUserData();

    }
    
    public static void main(String[] args) throws IOException
    {
        System.out.println("Welcom to XYZ food delivery system");
        while(true)
        {
            System.out.println("Do you want to 1)Login 2)Register");
            InputStreamReader ir =  new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(ir);
            int choice = Integer.parseInt(br.readLine());
            switch (choice)
            {
            case 1 :
            System.out.println("Username : ");
            String userName = br.readLine();
            System.out.println("Password : ");
            String password = br.readLine();

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
            register();

            default:
            continue;
            }
        }

    }
}

