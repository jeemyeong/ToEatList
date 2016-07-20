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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

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
    TextView textView6, textView7;
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
        textView6 = (TextView) findViewById(R.id.textView6);
        textView7 = (TextView) findViewById(R.id.textView7);

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
                    textView6.setText(foodList.get(foodNum).getName());
                    textView7.setText(foodList.get(foodNum).getRestaurant()+"/"+foodList.get(foodNum).getLoca_simple());
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
                Intent intent2= new Intent(this, DetailView.class);
                intent2.putExtra("foodNumber",foodList.get(foodNum).getId());
                intent2.putExtra("name", foodList.get(foodNum).getName());
                intent2.putExtra("category", foodList.get(foodNum).getCategory());
                intent2.putExtra("description", foodList.get(foodNum).getDescription());
                intent2.putExtra("restaurant", foodList.get(foodNum).getRestaurant());
                intent2.putExtra("loca_simple", foodList.get(foodNum).getLoca_simple());
                intent2.putExtra("loca_map", foodList.get(foodNum).getLoca_map());
                intent2.putExtra("image", foodList.get(foodNum).getImage().getImage().getUrl());
                intent2.putExtra("url", foodList.get(foodNum).getUrl());
                startActivity(intent2);
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
            String imageUrl = foodList.get(foodNum).getImage().getImage().getUrl();
            Glide.with(getApplicationContext()).load(imageUrl).into(imageView);
            textView6.setText(foodList.get(foodNum).getName());
            textView7.setText(foodList.get(foodNum).getRestaurant()+"/"+foodList.get(foodNum).getLoca_simple());
        }
        else
            Toast.makeText(getApplicationContext(), "마지막 음식입니다.", Toast.LENGTH_LONG).show();
    }
}
