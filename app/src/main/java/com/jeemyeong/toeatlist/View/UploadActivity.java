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

    ImageButton uploadImage;
    ImageButton uploadSend;
    EditText uploadName;
    EditText uploadCategory;
    EditText uploadDescription;
    EditText uploadRestaurant;
    EditText uploadLocaSimple;
    private ServerInterface api;
    private static final int REQUEST_PHOTO_ALBOM = 1;
    private String filePath;
    private String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadImage = (ImageButton) findViewById(R.id.upload_image);
        uploadSend = (ImageButton) findViewById(R.id.upload_send);
        uploadName = (EditText) findViewById(R.id.upload_name);
        uploadCategory = (EditText) findViewById(R.id.upload_category);
        uploadDescription = (EditText) findViewById(R.id.upload_description);
        uploadRestaurant = (EditText) findViewById(R.id.upload_restaurant);
        uploadLocaSimple = (EditText) findViewById(R.id.upload_loca);

        uploadImage.setOnClickListener(this);
        uploadSend.setOnClickListener(this);

//        String lF = Food.makeJSon(foodList);
//        Log.e("foodListlog",lF);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload_image:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PHOTO_ALBOM);
                break;
            case R.id.upload_send:
                if(filePath == null || uploadName.getText().toString().length() == 0 || uploadRestaurant.getText().toString().length() == 0)
                    Toast.makeText(getApplicationContext(),"모두 채워주세요", Toast.LENGTH_LONG).show();
                else{
                    final Handler handler = new Handler();
                    final ProgressDialog progressDialog = ProgressDialog.show(this, "", "업로드 중 입니다.");


                    TypedFile uploadFile = new TypedFile("multipart/form-data", new File(filePath));
                    api = AppController.getInstance().getServerInterface();
                    api.upload(uploadName.getText().toString(), uploadCategory.getText().toString(), uploadDescription.getText().toString(),
                            uploadRestaurant.getText().toString(), uploadLocaSimple.getText().toString(), uploadFile, new Callback<String>() {

                        @Override
                        public void success(String s, Response response) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{
            if(requestCode == REQUEST_PHOTO_ALBOM){
                Uri uri = getRealPathUri(data.getData());
                filePath = uri.toString();
                fileName = uri.getLastPathSegment();

                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                uploadImage.setImageBitmap(bitmap);
            }
        }catch(Exception e){
            Log.e("Upload","onActivityResult ERROR:" + e);
        }
    }

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
