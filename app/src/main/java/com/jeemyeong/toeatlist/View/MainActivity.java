package com.jeemyeong.toeatlist.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jeemyeong.toeatlist.Controller.AppController;
import com.jeemyeong.toeatlist.Model.Dao;
import com.jeemyeong.toeatlist.Model.Food;
import com.jeemyeong.toeatlist.R;
import com.jeemyeong.toeatlist.ServerInterface;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends Activity implements View.OnClickListener{
    private ImageButton main_food_heart_imageView, main_food_trash_imageView, my_list_imageButton;
    private CircularImageView main_food_image_circularImageView;
    private TextView main_food_title_textView, main_food_detail_textView;
    private Dao dao;
    private ServerInterface api;
    private List<Food> foodList;
    private int foodNum = 0;
    private boolean networkState = false;
    private boolean bookmark;

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

        //get button & image view
        main_food_heart_imageView = (ImageButton) findViewById(R.id.main_food_heart_imageView);
        main_food_trash_imageView = (ImageButton) findViewById(R.id.main_food_trash_imageView);
        my_list_imageButton = (ImageButton) findViewById(R.id.main_food_list_imageButton);
        main_food_image_circularImageView = (CircularImageView) findViewById(R.id.main_food_image_circularImageView);
        main_food_title_textView = (TextView) findViewById(R.id.main_food_title_textView);
        main_food_detail_textView = (TextView) findViewById(R.id.main_food_detail_textView);

        //set Listener
        main_food_heart_imageView.setOnClickListener(this);
        main_food_trash_imageView.setOnClickListener(this);
        main_food_image_circularImageView.setOnClickListener(this);
        my_list_imageButton.setOnClickListener(this);

        //get DAO(Data Access Object)
        dao = new Dao(getApplicationContext());

        //refresh data by online
        refreshData();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.main_food_heart_imageView: //Click heart, then bookmark
                bookmark = true;
                setNextFood(bookmark);
                break;
            case R.id.main_food_trash_imageView: //Click trash, then not bookmark
                bookmark = false;
                setNextFood(bookmark);
                break;
            case R.id.main_food_list_imageButton: //go to food list
                Intent intent = new Intent(this, CustomListActivity.class);
                startActivity(intent);
                break;
            case R.id.main_food_image_circularImageView: //go to food detail view
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

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    void setNextFood(boolean bookmark){ //set next food when click heart or trash
        if(foodNum<foodList.size()-1) {
            dao.insertFoodData(foodList.get(foodNum), bookmark);
            if(bookmark)
                Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "스킵되었습니다.", Toast.LENGTH_LONG).show();
            foodNum++;
            String imageUrl = foodList.get(foodNum).getImage().getImage().getUrl();
            Glide.with(getApplicationContext()).load(imageUrl).into(main_food_image_circularImageView);
            main_food_title_textView.setText(foodList.get(foodNum).getName());
            main_food_detail_textView.setText(foodList.get(foodNum).getRestaurant()+"/"+foodList.get(foodNum).getLoca_simple());
        }
        else
            Toast.makeText(getApplicationContext(), "마지막 음식입니다.", Toast.LENGTH_LONG).show();
    }

    void refreshData() { //refresh data if network has been working
        if (networkState) {
            api.getFoods(new Callback<List<Food>>() {
                @Override
                public void success(List<Food> foods, Response response) {
                    foodList = foods;
                    foodNum = dao.getLastFoodIndex(foodList)+1;
                    String imageUrl = foodList.get(foodNum).getImage().getImage().getUrl();
                    Glide.with(getApplicationContext()).load(imageUrl).into(main_food_image_circularImageView);
                    main_food_title_textView.setText(foodList.get(foodNum).getName());
                    main_food_detail_textView.setText(foodList.get(foodNum).getRestaurant() + "/" + foodList.get(foodNum).getLoca_simple());
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), "Failed to load foods", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
