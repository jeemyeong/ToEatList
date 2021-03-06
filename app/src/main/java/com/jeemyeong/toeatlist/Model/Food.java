package com.jeemyeong.toeatlist.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeemyeong_id on 2016-07-18.
 */
public class Food {
    int id;
    String name;
    String category;
    String description;
    String restaurant;
    String loca_simple;
    String loca_map;
    Image image;
    String imageUrl;
    String url;
    boolean bookmark;

    public Food(int id, String name, String category, String description, String restaurant, String loca_simple, String loca_map, String imageUrl, String url, boolean bookmark) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.restaurant = restaurant;
        this.loca_simple = loca_simple;
        this.loca_map = loca_map;
        this.imageUrl = imageUrl;
        this.url = url;
        this.bookmark = bookmark;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getLoca_simple() {
        return loca_simple;
    }

    public String getLoca_map() {
        return loca_map;
    }

    public Image getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public static String makeJSon(List<Food> list) {
        String gsonResult = null;
        Gson gson = new Gson();

        gsonResult = gson.toJson(list);
        return gsonResult;
    }

    public static ArrayList<Food> getListFromJSonObject(String jsonObject) {
        ArrayList<Food> result = null;

        Gson gson = new Gson();
        Type resultType = new TypeToken<ArrayList<Food>>() {
        }.getType();
        result = gson.fromJson(jsonObject, resultType);

        return result;
    }
}
