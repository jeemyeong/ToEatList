package com.jeemyeong.toeatlist.View;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jeemyeong.toeatlist.R;

import com.kakao.AppActionBuilder;
import com.kakao.AppActionInfoBuilder;
import com.kakao.KakaoLink;
import com.kakao.KakaoParameterException;
import com.kakao.KakaoTalkLinkMessageBuilder;

public class DetailView extends Activity {
    private ImageView detail_food_image_imageView;
    private TextView detail_food_name_textView, detail_food_restaurant_loca_simple_textView, detail_food_description_textView;
    private ImageButton naver_map_web_imageButton, kakao_link_app_imageButton;
    private KakaoLink kakaoLink;
    private KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        //get button & image view
        detail_food_image_imageView = (ImageView) findViewById(R.id.detail_food_image_imageView);
        detail_food_name_textView = (TextView) findViewById(R.id.detail_food_name_textView);
        detail_food_restaurant_loca_simple_textView = (TextView) findViewById(R.id.detail_food_restaurant_loca_simple_textView);
        detail_food_description_textView = (TextView) findViewById(R.id.detail_food_description_textView);
        naver_map_web_imageButton = (ImageButton) findViewById(R.id.naver_map_web_imageButton);
        kakao_link_app_imageButton = (ImageButton) findViewById(R.id.kakao_link_app_imageButton);

        //get intent data from before activity
        int indent_data_foodNumber = getIntent().getExtras().getInt("foodNumber");
        String indent_data_name = getIntent().getExtras().getString("name");
        String indent_data_category = getIntent().getExtras().getString("category");
        String indent_data_description = getIntent().getExtras().getString("description");
        String indent_data_restaurant = getIntent().getExtras().getString("restaurant");
        String indent_data_loca_simple = getIntent().getExtras().getString("loca_simple");
        String indent_data_image_url = getIntent().getExtras().getString("image");
        final String indent_data_loca_map = getIntent().getExtras().getString("loca_map");

        if (indent_data_loca_map == null) // when location data is null, then set invisible naver map icon
            naver_map_web_imageButton.setVisibility(View.INVISIBLE);
        else if (!indent_data_loca_map.startsWith("http://") && !indent_data_loca_map.startsWith("https://"))
            naver_map_web_imageButton.setVisibility(View.INVISIBLE);

        //set data to view
        Glide.with(getApplicationContext()).load(indent_data_image_url).into(detail_food_image_imageView);
        detail_food_name_textView.setText(indent_data_name);
        detail_food_restaurant_loca_simple_textView.setText(indent_data_restaurant+"/"+indent_data_loca_simple);
        detail_food_description_textView.setText(indent_data_description);

        //set naver map button
        naver_map_web_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(indent_data_loca_map));
                startActivity(browserIntent);
            }
        });

        //set kakao link data
        final String kakao_link_text = indent_data_name + " / " + indent_data_restaurant + " / " + indent_data_loca_simple;
        final String kakao_link_imageUrl = indent_data_image_url;
        final String kakao_link_loca_map = indent_data_loca_map;

        //make kakao link button work
        try {
            kakaoLink = KakaoLink.getKakaoLink(this);
            kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
            addListenerOnSendButton(kakao_link_text, kakao_link_imageUrl, kakao_link_loca_map);
        } catch (KakaoParameterException e) {
            alert(e.getMessage());
        }
    }

    //add Listener on kakao link button
    void addListenerOnSendButton(String text, String link, String image) {
        ImageButton kakao_link_app_imageButton = (ImageButton) findViewById(R.id.kakao_link_app_imageButton);
        final String textType = text;
        final String linkType = link;
        final String imageType = image;

        kakao_link_app_imageButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = "Text : " + textType +
                        "\nLink : " + linkType +
                        "\nImage : " + imageType;
                new android.app.AlertDialog.Builder(DetailView.this)
                        .setTitle("아래 내용을 전송할까요?")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendKakaoTalkLink(textType, linkType, imageType);
                                kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
        });
    }

    //send kakao link
    private void sendKakaoTalkLink(String text, String link, String image) {
        try {
                kakaoTalkLinkMessageBuilder.addText(text);
                kakaoTalkLinkMessageBuilder.addImage(image, 300, 200);
//                kakaoTalkLinkMessageBuilder.addWebLink("with Naver", link); //Error when use link on naver
              kakaoTalkLinkMessageBuilder.addAppButton("go to app");
            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder.build(), this);
        } catch (KakaoParameterException e) {
            alert(e.getMessage());
        }
    }

    //Alert when user click send button
    private void alert(String message) {
        new android.app.AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .create().show();
    }
}
