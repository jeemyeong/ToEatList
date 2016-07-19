package com.jeemyeong.toeatlist;

import android.app.Activity;
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
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends Activity implements View.OnClickListener{
    ImageButton imageButton1,imageButton2;
    ImageView imageView;
    Button button;
    private ServerInterface api;

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
        

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);

        String url = "https://jeemyeongrails.s3.amazonaws.com/uploads/food/image_file/1/2_sannakji.PNG";
        Glide.with(this).load(url).into(imageView);
        imageView.setVisibility(View.VISIBLE);
        String json = "[{\"id\":1,\"name\":\"찜닭\",\"category\":\"일식\",\"description\":\"맛난다\",\"restaurant\":\"토끼정\",\"loca_simple\":\"강남\",\"loca_map\":\"강남\",\"image\":{\"image\":{\"url\":\"https://jeemyeongrails.s3.amazonaws.com/uploads/food/image/1/tokkijung.png\"}},\"url\":\"http://52.78.99.238/foods/1.json\"},{\"id\":2,\"name\":\"에그베네딕트\",\"category\":\"양식\",\"description\":\"맛\",\"restaurant\":\"르브런쉭\",\"loca_simple\":\"여의도\",\"loca_map\":\"여의도\",\"image\":{\"image\":{\"url\":\"https://jeemyeongrails.s3.amazonaws.com/uploads/food/image/2/lebrun.PNG\"}},\"url\":\"http://52.78.99.238/foods/2.json\"}]";
        List<Food> foodList = Food.getListFromJSonObject(json);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageButton1:
//                Glide.with(this).load(R.drawable.tokkijung).into(imageView);
//                imageView.setVisibility(View.VISIBLE);
                api.getFoods(new Callback<List<Food>>() {
                    @Override
                    public void success(List<Food> thumbnails, Response response) {

                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(getApplicationContext(), "Failed to load thumbnails", Toast.LENGTH_LONG).show();

                    }
                });
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
