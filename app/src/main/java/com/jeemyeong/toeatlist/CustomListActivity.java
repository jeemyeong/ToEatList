package com.jeemyeong.toeatlist;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class CustomListActivity extends Activity {
    Dao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);

        dao = new Dao(getApplicationContext());
        ArrayList<Food> foodLocalList = dao.getLocalFoodList();

        ListView listView = (ListView) findViewById(R.id.custom_list_listView);
        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.custom_list_row, foodLocalList);
        listView.setAdapter(customAdapter);
    }
}
