package com.jeemyeong.toeatlist.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeemyeong on 2016-07-20.
 */
public class Dao {
    private Context context;
    private SQLiteDatabase database;
    public Dao(Context context){
        this.context = context;

        database = context.openOrCreateDatabase("LocalDATA.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);


        try{
            String sql = "CREATE TABLE IF NOT EXISTS LocalFoods (ID integer primary key autoincrement,"
                    + "                                        FoodNumber integer UNIQUE not null,"
                    + "                                        Name text,"
                    + "                                        Category text,"
                    + "                                        Description text,"
                    + "                                        Restaurant text,"
                    + "                                        Loca_simple text,"
                    + "                                        Loca_map text,"
                    + "                                        ImageURL text,"
                    + "                                        JsonURL text,"
                    + "                                        Bookmark boolean);";
            database.execSQL(sql);
        }catch(Exception e){
            Log.e("Dao", "CREATE TABLE FAILED! -" + e);
            e.printStackTrace();
        }
    }


    public void insertFoodData(Food foodData, boolean bookmark){
        int foodNumber = foodData.getId();
        String name = foodData.getName();
        String category = foodData.getCategory();
        String description = foodData.getDescription();
        String restaurant = foodData.getRestaurant();
        String loca_simple = foodData.getLoca_simple();
        String loca_map = foodData.getLoca_map();
        String image = foodData.getImage().getImage().getUrl();
        String url = foodData.getUrl();
        String sql = "INSERT INTO LocalFoods(FoodNumber, Name, Category, Description, Restaurant, Loca_simple, Loca_map, ImageURL, JsonURL, Bookmark)"
                + " VALUES(" + foodNumber + ",'" + name + "','" + category + "','" + description + "','" + restaurant + "','" + loca_simple + "','" + loca_map + "','"
                + image + "','" + url + "','" + bookmark + "');";
        try{
            database.execSQL(sql);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public int size(List<Food> foodList){
        int i = 0;
        String sql = "SELECT * FROM LocalFoods;";
        Cursor cursor;
        for(int j = 0; j<foodList.size(); j++){
            cursor = database.rawQuery(sql, null);
            while(cursor.moveToNext()){
                if(cursor.getString(9).toString().equals(foodList.get(foodList.size()-1-j).getUrl().toString())){
                    Log.i("Find", Integer.toString(foodList.size()-1-j));
                    return foodList.size()-1-j;
                }
            }
        }
        return 0;

    }
    public ArrayList<Food> getLocalFoodList(){

        ArrayList<Food> localFoodList = new ArrayList<Food>();

        int foodNumber;
        String name;
        String category;
        String description;
        String restaurant;
        String loca_simple;
        String loca_map;
        String image;
        String url;
        boolean bookmark;

        String sql = "SELECT * FROM LocalFoods;";
        Cursor cursor = database.rawQuery(sql, null);

        while(cursor.moveToNext()){
            foodNumber = cursor.getInt(1);
            name = cursor.getString(2);
            category = cursor.getString(3);
            description = cursor.getString(4);
            restaurant = cursor.getString(5);
            loca_simple = cursor.getString(6);
            loca_map = cursor.getString(7);
            image = cursor.getString(8);
            url = cursor.getString(9);
            String str = cursor.getString(10);
            bookmark = Boolean.parseBoolean(str);
            if(bookmark){
                Food foodData = new Food(foodNumber, name, category, description, restaurant, loca_simple, loca_map, image, url, bookmark);
                localFoodList.add(foodData);
            }
        }
        return localFoodList;
    }
}
