package com.jeemyeong.toeatlist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by jeemyeong on 2016-07-20.
 */
public class CustomAdapter extends ArrayAdapter<Food>{
    private Context context;
    private int layoutResourceId;
    private ArrayList<Food> foodList;

    public CustomAdapter(Context context, int layoutResourceId, ArrayList<Food> foodList) {
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

            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.item = (TextView) convertView.findViewById(R.id.item);
            viewHolder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            viewHolder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Food food = foodList.get(position);

        Glide.with(context).load(food.getImageUrl()).into(viewHolder.icon);

        viewHolder.item.setText(food.getName());
        viewHolder.textView1.setText(food.getCategory());
        viewHolder.textView2.setText("("+food.getRestaurant()+"/"+food.getLoca_simple()+")");


        return convertView;
    }


}
