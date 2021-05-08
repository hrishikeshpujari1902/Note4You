package com.HrishikeshPujari.Note4youFirebase;

import java.text.DateFormat;
import java.util.Calendar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class NoteClass {
    private String currentDate;
    private String currentTime;
    private String title;
    private String description;
    private String imageUrl;


    public NoteClass(String title1, String description1, String imageUrl1,String currentDate1,String currentTime1) {
        title = title1;
        currentDate= currentDate1;
        description = description1;
        imageUrl= imageUrl1;
        currentTime= currentTime1;
    }
    public NoteClass(){

    }

    public  String getCurrentDate() {
        return currentDate;
    }

    public  String getCurrentTime() {
        return currentTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl(){return imageUrl;}



}
