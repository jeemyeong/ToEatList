package com.jeemyeong.toeatlist.Controller;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jeemyeong.toeatlist.ServerInterface;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by jeemyeong_id on 2016-07-18.
 */
public class AppController extends Application {

    public static AppController instance;
    public static AppController getInstance(){return instance;}

    private ServerInterface api;
    public ServerInterface getServerInterface() {return  api;}

    public void onCreate(){

        super.onCreate();
        AppController.instance = this;


    }


    private String endpoint;

    public void buildServerInterface(String ip, int port){

        //For Singleton
        if(api==null){

            //목적지 주소 즉, 서버의 주소를 설정
            endpoint = String.format("http://%s:%d",ip,port);

            //GSON에 날짜 포맷을 설정하고 Converter에 객체를 넣어줍니다.
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

            RestAdapter.Builder builder = new RestAdapter.Builder();
            builder.setConverter(new GsonConverter(gson));

            builder.setEndpoint(endpoint);

            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {

                }
            });

            RestAdapter adapter = builder.build();
            api = adapter.create(ServerInterface.class);

        }



    }



}
