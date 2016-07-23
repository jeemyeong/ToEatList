package com.jeemyeong.toeatlist.View;

import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by jeemyeong on 2016-07-20.
 */
public class ViewHolder {

    private  CircularImageView icon;
    private  TextView item;
    private  TextView category;
    private  TextView detail;

    public ViewHolder() {
    }

    public ViewHolder(CircularImageView icon, TextView item, TextView category, TextView detail) {
        this.icon = icon;
        this.item = item;
        this.category = category;
        this.detail = detail;
    }

    public CircularImageView getIcon() {
        return icon;
    }

    public TextView getItem() {
        return item;
    }

    public TextView getCategory() {
        return category;
    }

    public TextView getDetail() {
        return detail;
    }

    public void setIcon(CircularImageView icon) {
        this.icon = icon;
    }

    public void setItem(TextView item) {
        this.item = item;
    }

    public void setCategory(TextView category) {
        this.category = category;
    }

    public void setDetail(TextView detail) {
        this.detail = detail;
    }
}