package com.jeemyeong.toeatlist;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeemyeong_id on 2016-07-18.
 */
public class Image {
    Image image;
    String url;

    public Image getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public static String makeJSon(List<Image> list) {
        String gsonResult = null;
        Gson gson = new Gson();

        gsonResult = gson.toJson(list);
        return gsonResult;
    }

    public static ArrayList<Image> getListFromJSonObject(String jsonObject) {
        ArrayList<Image> result = null;

        Gson gson = new Gson();
        Type resultType = new TypeToken<ArrayList<Image>>() {
        }.getType();
        result = gson.fromJson(jsonObject, resultType);

        return result;
    }
}
