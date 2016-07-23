package com.jeemyeong.toeatlist.Controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jeemyeong.toeatlist.Model.Food;
import com.jeemyeong.toeatlist.R;
import com.jeemyeong.toeatlist.View.ViewHolder;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

/**
 * Created by jeemyeong on 2016-07-20.
 */
public class FoodListAdapter extends ArrayAdapter<Food>{
    private Context context;
    private int layoutResourceId;
    private ArrayList<Food> foodList;

    public FoodListAdapter(Context context, int layoutResourceId, ArrayList<Food> foodList) {
        super(context, layoutResourceId, foodList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.foodList = foodList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.setIcon((CircularImageView) convertView.findViewById(R.id.food_list_thumbnail_circularImageView));
            viewHolder.setItem((TextView) convertView.findViewById(R.id.food_list_name_textView));
            viewHolder.setCategory((TextView) convertView.findViewById(R.id.food_list_category_textView));
            viewHolder.setDetail((TextView) convertView.findViewById(R.id.food_list_detail_textView));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Food food = foodList.get(position);

        //set food icon
        Glide.with(context).load(food.getImageUrl()).into(viewHolder.getIcon());

        //set text about food
        viewHolder.getItem().setText(food.getName());
        viewHolder.getCategory().setText(food.getCategory());
        viewHolder.getDetail().setText("("+food.getRestaurant()+"/"+food.getLoca_simple()+")");


        return convertView;
    }


}
