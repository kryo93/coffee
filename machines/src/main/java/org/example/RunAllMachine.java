package org.example;

import org.example.property.Property;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RunAllMachine {

  /*
  removing these so as to follow given json
   */
//  public void makeGingerTea(){
//
//  }
//  public void makeElaichiTea(){
//
//  }
  public void makeCoffee(){
    Map<String,Integer> map = readBeverages("hot_coffee");
    makeBeverage(map);
  }
  public void makeBlackTea(){
    Map<String,Integer> map = readBeverages("black_tea");
    makeBeverage(map);
  }
  public void makeGreenTea(){
    Map<String,Integer> map = readBeverages("green_tea");
    makeBeverage(map);
  }
  public void makeTea(){
    Map<String,Integer> map = readBeverages("hot_tea");
    makeBeverage(map);
  }
  public synchronized Map<String,Integer> readBeverages(String type){
    Map<String,Integer> map = new HashMap<>();
    try{
       FileReader reader = new FileReader(Property.FILE_LOCATIONS);
       JSONTokener token =  new JSONTokener(reader);
       JSONObject jobj = new JSONObject(token);
       JSONObject machine = jobj.getJSONObject("machine");
       JSONObject beverage  = jobj.getJSONObject("beverage");
       JSONObject drink = beverage.getJSONObject(type);

       for (String ingredientName : drink.keySet()) {
         int ingredientQuantity = drink.getInt(ingredientName);
         map.put(ingredientName,ingredientQuantity);
       }
     } catch(IOException e){
       e.printStackTrace();
    }
    finally {
      return map;
    }
  }

  public synchronized String makeBeverage(Map<String,Integer> map){
    try{
      FileReader reader = new FileReader(Property.FILE_LOCATIONS);
      JSONTokener token =  new JSONTokener(reader);
      JSONObject jobj = new JSONObject(token);
      JSONObject machine = jobj.getJSONObject("machine");
      JSONObject total  = jobj.getJSONObject("total_items_quantity");

      for (String ingredientName : total.keySet()) {
        int reduceBy = map.get(ingredientName);
        int ingredientQuantity = total.getInt(ingredientName);
        if(ingredientQuantity-reduceBy > 0){
          total.put(ingredientName, ingredientQuantity-reduceBy);
        } else{
          return ingredientName+ " is over, kindly restock";
        }
      }
      FileWriter writer = new FileWriter(Property.FILE_LOCATIONS);
      writer.write(jobj.toString(4));
    } catch(IOException e){
      e.printStackTrace();
    } finally {
      return "success";
    }
  }
}
