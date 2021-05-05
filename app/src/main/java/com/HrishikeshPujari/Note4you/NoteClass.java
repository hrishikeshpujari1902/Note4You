package com.HrishikeshPujari.Note4you;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class NoteClass {

    private static String title;
    private static String description;
    private static String image;
    public static final int COL_ID=0;
    public static final int COL_TITLE=1;
    public static final int COL_DESCRIPTION=2;
    public static final int COL_IMAGE=03;



    public NoteClass(Cursor cursor){
        this.title=cursor.getString(COL_TITLE);
        this.description=cursor.getString(COL_DESCRIPTION);
        this.image=cursor.getString(COL_IMAGE);


    }



    public NoteClass(String title, String description, Bitmap bitmap) {
        this.title = title;
        this.description = description;
        this.image= BitMapToString(bitmap);
    }

    public static String getTitle() {
        return title;
    }

    public static String getDescription() {
        return description;
    }

    public static Bitmap getBitmap() {
        return StringtoBitmap(image);
    }

    public static String getImageaAsString(){return image;}

    private static Bitmap StringtoBitmap(String image) {
        try {
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    public Bitmap resizedBitmap(Bitmap  bitmap){
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();


    }

}
