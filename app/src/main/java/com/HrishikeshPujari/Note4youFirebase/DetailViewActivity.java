package com.HrishikeshPujari.Note4youFirebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class DetailViewActivity extends AppCompatActivity {

        private String mTitle;
        private String mDescription;
        private String mImageUri;
        private TextView mDateTextView;
        private TextView mTimeTextView;
        private TextView mTitleTextview;
        private TextView mDesciptionTextview;
        private ImageView mImageView;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        mTitleTextview=(TextView)findViewById(R.id.display_title);
        mDesciptionTextview=(TextView)findViewById(R.id.display_description);
        mImageView=(ImageView)findViewById(R.id.display_image);
        mDateTextView=(TextView)findViewById(R.id.date_detail);
        mTimeTextView=(TextView)findViewById(R.id.time_detail);





    }
    @Override
    public void onStart(){
        super.onStart();
        Intent noteIntent=getIntent();
        mTitle=noteIntent.getStringExtra("title");
        mDescription=noteIntent.getStringExtra("description");
        mImageUri=noteIntent.getStringExtra("image");
        mDateTextView.setText(noteIntent.getStringExtra("date"));
        mTimeTextView.setText(noteIntent.getStringExtra("time"));
        Glide.with(this).load(mImageUri).into(mImageView);
        mDesciptionTextview.setText(mDescription);
        mTitleTextview.setText(mTitle);


    }
    public Bitmap StringToBitMap(String encodedString) {

        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myintent=new Intent(DetailViewActivity.this,AfterLogin.class);
        startActivity(myintent);
    }

    @Override
    public void onStop() {
        super.onStop();


    }

}
