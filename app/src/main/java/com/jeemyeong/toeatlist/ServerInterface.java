package com.jeemyeong.toeatlist;

import com.jeemyeong.toeatlist.Model.Food;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by jeemyeong_id on 2016-07-19.
 */
public interface ServerInterface {

    /**
     * Food List를 GET방식으로 요청하는 메소드
     *
     * @param callback
     */

    @GET("/foods.json")
    public void getFoods(Callback<List<Food>> callback);

    /**
     * Client으로부터 Server에게 POST방식으로 데이터를 전송하는 메소드
     *
     * @param name
     * @param category
     * @param description
     * @param restaurant
     * @param loca_simple
     * @param image
     * @param callback
     */

    @Multipart
    @POST("/foods/upload")
    public void upload(@Part("name")String name,
                       @Part("category")String category,
                       @Part("description")String description,
                       @Part("restaurant")String restaurant,
                       @Part("loca_simple")String loca_simple,
                       @Part("image")TypedFile image,
                       Callback<Object> callback);
}
