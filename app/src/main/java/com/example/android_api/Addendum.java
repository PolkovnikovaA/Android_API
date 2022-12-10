package com.example.android_api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Addendum extends AppCompatActivity {
    String image;
    private ImageView addendumImage;
    private Button btnAdd;
    private EditText Name, Breed, Age;
    String Img = "";
    Mask mask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addendum);

        mask=getIntent().getParcelableExtra("Dogs");

        getSupportActionBar().hide();   //Убирает шапку в layout

        Name = findViewById(R.id.addendum_Name);
        Breed = findViewById(R.id.addendum_Breed);
        Age = findViewById(R.id.addendum_Age);
        btnAdd = findViewById(R.id.addendum_Delete);

        addendumImage = findViewById(R.id.details_Image);
    }

    public void btnAdd(View v) {

        Name = findViewById(R.id.addendum_Name);
        Breed = findViewById(R.id.addendum_Breed);
        Age = findViewById(R.id.addendum_Age);

        if (Breed.getText().length() == 0 || Age.getText().length() == 0 || Name.getText().length() == 0) {
            Toast.makeText(Addendum.this, "Не заполненны обязательные поля", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (Img == "") {
                Img = null;
                postData(Img, Name.getText().toString(), Breed.getText().toString(), Age.getText().toString());
            } else {
                postData(Img, Name.getText().toString(), Breed.getText().toString(), Age.getText().toString());
            }
            finish();
        }

    }


    public void onClickChooseImage(View view)
    {
        getImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!= null && data.getData()!= null)
        {
            if(resultCode==RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                addendumImage.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)addendumImage.getDrawable()).getBitmap();
                encodeImage(bitmap);

            }
        }
    }

    private void getImage()
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Img= Base64.getEncoder().encodeToString(bytes);
            return Img;
        }
        return "";
    }

    private void postData(String name, String breed, String age, String Image) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/полковниковаав/api/Dogs/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Modal modal = new Modal(name, breed, age, Image);

        Call<Modal> call = retrofitAPI.createPost(modal);

        call.enqueue(new Callback<Modal>() {
            @Override
            public void onResponse(Call<Modal> call, Response<Modal> response) {
                Toast.makeText(Addendum.this, "Данные добавлены", Toast.LENGTH_SHORT).show();

                Name.setText("");
                Breed.setText("");
                Age.setText("");
                Modal responseFromAPI = response.body();

            }

            @Override
            public void onFailure(Call<Modal> call, Throwable t) {
            }
        });
    }

    private void goBackIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}