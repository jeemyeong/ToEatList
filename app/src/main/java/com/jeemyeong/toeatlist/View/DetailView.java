package com.jeemyeong.toeatlist.View;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jeemyeong.toeatlist.R;

import com.kakao.KakaoLink;
import com.kakao.KakaoParameterException;
import com.kakao.KakaoTalkLinkMessageBuilder;

public class DetailView extends Activity {
    ImageView imageView2;
    TextView textView3, textView4, textView5;
    ImageButton imageButton2, kakaoSend;
    private KakaoLink kakaoLink;
    private KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        kakaoSend = (ImageButton) findViewById(R.id.kakao_send);

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

        final String text = name + " / " + restaurant + " / " + loca_simple;
        final String image = imageUrl;
        final String link = loca_map;

        try {
            kakaoLink = KakaoLink.getKakaoLink(this);
            kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
            addListenerOnSendButton(text, image, link);
        } catch (KakaoParameterException e) {
            alert(e.getMessage());
        }
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(loca_map));
                startActivity(browserIntent);
            }
        });
    }
    void addListenerOnSendButton(String text, String link, String image) {
        ImageButton sendButton = (ImageButton) findViewById(R.id.kakao_send);
        final String textType = text;
        final String linkType = link;
        final String imageType = image;

        sendButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final String buttonType = String.valueOf(button.getSelectedItem());

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

    private void sendKakaoTalkLink(String text, String link, String image) {
        try {
                kakaoTalkLinkMessageBuilder.addText(text);

                kakaoTalkLinkMessageBuilder.addImage(image, 300, 200);


            // 웹싸이트에 등록한 "http://www.kakao.com"을 overwrite함. overwrite는 같은 도메인만 가능.
                kakaoTalkLinkMessageBuilder.addWebLink("with Naver", link);


            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder.build(), this);
        } catch (KakaoParameterException e) {
            alert(e.getMessage());
        }
    }

    private void alert(String message) {
        new android.app.AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .create().show();
    }
}
