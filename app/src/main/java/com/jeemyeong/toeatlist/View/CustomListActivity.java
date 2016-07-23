package com.jeemyeong.toeatlist.View;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jeemyeong.toeatlist.Controller.FoodListAdapter;
import com.jeemyeong.toeatlist.Model.Dao;
import com.jeemyeong.toeatlist.Model.Food;
import com.jeemyeong.toeatlist.R;

import java.util.ArrayList;

public class CustomListActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private Dao dao;
    private ArrayList<Food> foodLocalList;
    private ImageButton food_list_add_imageButton;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //get button & list view
        food_list_add_imageButton = (ImageButton) findViewById(R.id.food_list_add_imageButton);
        ListView food_list_listView = (ListView) findViewById(R.id.food_list_listView);

        //get DAO(Data Access Object)
        dao = new Dao(getApplicationContext());
        //get Local Data
        foodLocalList = dao.getLocalFoodList();

        //set adapter for list view
        FoodListAdapter foodListAdapter = new FoodListAdapter(this, R.layout.list_row, foodLocalList);
        food_list_listView.setAdapter(foodListAdapter);

        //add listener
        food_list_add_imageButton.setOnClickListener(this);
        food_list_listView.setOnItemClickListener(this);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.food_list_add_imageButton: //food add button
                Intent intent= new Intent(this, UploadActivity.class);
                startActivity(intent);
                break;
        }

    }

    //when clicking item, go to detail view
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent= new Intent(this, DetailView.class);
        intent.putExtra("foodNumber", foodLocalList.get(position).getId());
        intent.putExtra("name", foodLocalList.get(position).getName());
        intent.putExtra("category", foodLocalList.get(position).getCategory());
        intent.putExtra("description", foodLocalList.get(position).getDescription());
        intent.putExtra("restaurant", foodLocalList.get(position).getRestaurant());
        intent.putExtra("loca_simple", foodLocalList.get(position).getLoca_simple());
        intent.putExtra("loca_map", foodLocalList.get(position).getLoca_map());
        intent.putExtra("image", foodLocalList.get(position).getImageUrl());
        intent.putExtra("url", foodLocalList.get(position).getUrl());
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CustomList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.jeemyeong.toeatlist/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CustomList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.jeemyeong.toeatlist/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


}
