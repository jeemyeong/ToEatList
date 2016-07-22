package com.jeemyeong.toeatlist.View;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jeemyeong.toeatlist.R;

public class DetailView extends Activity {
    ImageView imageView2;
    TextView textView3, textView4, textView5;
    ImageButton imageButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);

        int id = getIntent().getExtras().getInt("foodNumber");
        String name = getIntent().getExtras().getString("name");
        String category = getIntent().getExtras().getString("category");
        String description = getIntent().getExtras().getString("description");
        String restaurant = getIntent().getExtras().getString("restaurant");
        String loca_simple = getIntent().getExtras().getString("loca_simple");
        final String loca_map = getIntent().getExtras().getString("loca_map");
        String imageUrl = getIntent().getExtras().getString("image");

        if (loca_map == null)
            imageButton2.setVisibility(View.INVISIBLE);
        else if (!loca_map.startsWith("http://") && !loca_map.startsWith("https://"))
            imageButton2.setVisibility(View.INVISIBLE);

        Glide.with(getApplicationContext()).load(imageUrl).into(imageView2);
        textView3.setText(name);
        textView4.setText(restaurant+"/"+loca_simple);
        textView5.setText(description);

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(loca_map));
                startActivity(browserIntent);
            }
        });


    }
}
