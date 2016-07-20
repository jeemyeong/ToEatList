package com.jeemyeong.toeatlist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends Activity implements View.OnClickListener{
    ImageButton imageButton1,imageButton2;
    ImageView imageView;
    Button button;
    private ServerInterface api;
    List<Food> foodList;
    int foodNum = 0;
    boolean networkState = false;
    Dao dao;
    boolean bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Config ip&port
        String ip = "52.78.99.238";
        int port = 80;

        //take ServerInterface
        AppController.getInstance().buildServerInterface(ip,port);
        api = AppController.getInstance().getServerInterface();
        if(api!=null)
            networkState = true;
        else
            Toast.makeText(getApplicationContext(),"서버에 문제가 생겼습니다.",Toast.LENGTH_SHORT).show();

        imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);

        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageView.setOnClickListener(this);
        button.setOnClickListener(this);

        dao = new Dao(getApplicationContext());
        foodNum = getFoodNum(dao.getLocalFoodList());

        if (networkState) {
            api.getFoods(new Callback<List<Food>>() {
                @Override
                public void success(List<Food> foods, Response response) {
                    foodList = foods;
                    String imageUrl = foodList.get(foodNum).getImage().getImage().getUrl();
                    Glide.with(getApplicationContext()).load(imageUrl).into(imageView);
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), "Failed to load foods", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageButton1:
                bookmark = true;
                setNextFood(bookmark);
                break;
            case R.id.imageButton2:
                bookmark = false;
                setNextFood(bookmark);
                break;
            case R.id.button:
                Intent intent = new Intent(this, CustomListActivity.class);
                startActivity(intent);
                break;
            case R.id.imageView:
                break;
        }
    }

    int getFoodNum(ArrayList<Food> localFoodList){
        return localFoodList.size();
    }

    void setNextFood(boolean bookmark){
        if(foodNum<foodList.size()-1) {
            dao.insertFoodData(foodList.get(foodNum), bookmark);
            if(bookmark)
                Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "스킵되었습니다.", Toast.LENGTH_LONG).show();
            foodNum++;
            Glide.with(getApplicationContext())
                    .load(foodList.get(foodNum).getImage().getImage().getUrl())
                    .into(imageView);
        }
        else
            Toast.makeText(getApplicationContext(), "마지막 음식입니다.", Toast.LENGTH_LONG).show();
    }
}
