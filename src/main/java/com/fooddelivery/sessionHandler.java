package com.fooddelivery;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The class implements the concept of encapsulation and inherits Session for session Management
 */
public class sessionHandler extends Session{

    /* Class to handle Session related operations read and write */
    @Override
    void addToCache(String cacheStatus, String cacheEmail, String cacheLocation, String cachefoodItems,
            String cacheResturantId, String cacheWhishlist, String cacheQuantity) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Active", cacheStatus);
                jsonObject.put("Email", cacheEmail);
                jsonObject.put("Location", cacheLocation);
                jsonObject.put("Resturant_id", cacheResturantId);
                jsonObject.put("food_items", cachefoodItems);
                jsonObject.put("whishlist", cacheWhishlist);
                jsonObject.put("quantity", cacheQuantity);
                try 
                {
                 FileWriter file = new FileWriter(cachePath);
                 file.write(jsonObject.toJSONString());
                 file.close();
                }catch (IOException e) 
                {
                System.out.println("Error due to json file creation"+e.getMessage());
                }
        
    }

    @Override
    sessionDao readFromCache() {
        JSONParser jsonParser = new JSONParser();
        sessionDao cacheDaoObj=new sessionDao();
        try (FileReader reader = new FileReader(cachePath))
        {
            Object obj = jsonParser.parse(reader);
            JSONObject sessionList1 = (JSONObject) obj;
            
            cacheDaoObj.setCacheEmail(sessionList1.get("Email").toString());
            cacheDaoObj.setCacheStatus(sessionList1.get("Active").toString());
            cacheDaoObj.setCacheLocation(sessionList1.get("Location").toString());
            cacheDaoObj.setCacheQuantity(sessionList1.get("quantity").toString());
            cacheDaoObj.setCachefoodItems(sessionList1.get("food_items").toString());
            cacheDaoObj.setCacheResturantId(sessionList1.get("Resturant_id").toString());
            cacheDaoObj.setCacheWhishList(sessionList1.get("whishlist").toString());
        } 
        catch (IOException e) 
        {
            System.err.println("Error due to parsing input/output from the cache json file "+e.getMessage());    
        } 
        catch (ParseException e) 
        {
             System.err.println("Error due to parsing the cache json file "+e.getMessage());    
        }
        return cacheDaoObj;
    }
    
    
}
