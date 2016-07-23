package com.jeemyeong.toeatlist.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jeemyeong.toeatlist.Controller.AppController;
import com.jeemyeong.toeatlist.R;
import com.jeemyeong.toeatlist.ServerInterface;

import java.io.File;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class UploadActivity extends Activity implements View.OnClickListener{

    private ImageButton upload_image_imageButton;
    private ImageButton upload_send_imageButton;
    private EditText upload_name_editText;
    private EditText upload_category_editText;
    private EditText upload_description_editText;
    private EditText upload_restaurant_editText;
    private EditText upload_loca_simple_editText;
    private ServerInterface api;
    private static final int REQUEST_PHOTO_ALBOM = 1;
    private String filePath;
    private String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        upload_image_imageButton = (ImageButton) findViewById(R.id.upload_image_imageButton);
        upload_send_imageButton = (ImageButton) findViewById(R.id.upload_send_imageButton);
        upload_name_editText = (EditText) findViewById(R.id.upload_name_editText);
        upload_category_editText = (EditText) findViewById(R.id.upload_category_editText);
        upload_description_editText = (EditText) findViewById(R.id.upload_description_editText);
        upload_restaurant_editText = (EditText) findViewById(R.id.upload_restaurant_editText);
        upload_loca_simple_editText = (EditText) findViewById(R.id.upload_loca_simple_editText);

        upload_image_imageButton.setOnClickListener(this);
        upload_send_imageButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload_image_imageButton: // to upload image
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PHOTO_ALBOM);
                break;
            case R.id.upload_send_imageButton: //send data to server
                //when some data is null
                if(filePath == null || upload_name_editText.getText().toString().length() == 0 || upload_restaurant_editText.getText().toString().length() == 0)
                    Toast.makeText(getApplicationContext(),"모두 채워주세요", Toast.LENGTH_LONG).show();
                else{ //not null
                    //when uploading, dialog  is working
                    final Handler handler = new Handler();
                    final ProgressDialog progressDialog = ProgressDialog.show(this, "", "업로드 중 입니다.");

                    //send data to server
                    TypedFile uploadFile = new TypedFile("multipart/form-data", new File(filePath));
                    api = AppController.getInstance().getServerInterface();
                    api.upload(upload_name_editText.getText().toString(), upload_category_editText.getText().toString(), upload_description_editText.getText().toString(),
                            upload_restaurant_editText.getText().toString(), upload_loca_simple_editText.getText().toString(), uploadFile, new Callback<Object>() {

                        @Override
                        public void success(Object s, Response response) {
                            progressDialog.cancel();
                            Toast.makeText(getApplicationContext(),"입력되었습니다", Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            progressDialog.cancel();
                            Toast.makeText(getApplicationContext(),"...", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }
                break;
        }
    }

    @Override //user should bring photo from gallery
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //get file path & set image
        try{
            if(requestCode == REQUEST_PHOTO_ALBOM){
                Uri uri = getRealPathUri(data.getData());
                filePath = uri.toString();
                fileName = uri.getLastPathSegment();

                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                upload_image_imageButton.setImageBitmap(bitmap);
            }
        }catch(Exception e){
            Log.e("Upload","onActivityResult ERROR:" + e);
        }
    }

    //method to get real path
    private Uri getRealPathUri(Uri uri){
        Uri filePathUri = uri;
        if (uri.getScheme().toString().compareTo("content") == 0){
            Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
            if(cursor.moveToFirst()){
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                filePathUri = Uri.parse(cursor.getString(column_index));
            }
        }
        return filePathUri;
    }
}
