package com.jeemyeong.toeatlist;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.net.URL;


public class MainActivity extends Activity implements View.OnClickListener{
    ImageButton imageButton1,imageButton2;
    ImageView imageView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);

        String url = "https://jeemyeongrails.s3.amazonaws.com/uploads/food/image_file/1/tokkijung.PNG";
        Glide.with(this).load(url).into(imageView);
        imageView.setVisibility(View.VISIBLE);
//        imageButton1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageButton1:
//                Glide.with(this).load(R.drawable.tokkijung).into(imageView);
//                imageView.setVisibility(View.VISIBLE);
                break;
            case R.id.imageButton2:
                break;
            case R.id.button:
                break;
            case R.id.imageView:
                break;
        }
    }
}
