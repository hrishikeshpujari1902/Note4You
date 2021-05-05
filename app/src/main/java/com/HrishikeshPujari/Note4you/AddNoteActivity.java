package com.HrishikeshPujari.Note4you;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;

public class AddNoteActivity extends AppCompatActivity {
    public static final int CAMERA_REQUEST_CODE = 101;
    public static final int CAMERA_INTENT_CODE = 102;
    public static final int GALLER_REQ_CODE = 100;

    public static final String NOTE_PREFS_1 = "note_prefs1";


    private AutoCompleteTextView titleView;
    private AutoCompleteTextView descriptionView;
    private ArrayList<NoteClass> mNoteClassArrayList;
    private Button galleryButton;
    private Button cameraButton;
    private ImageView mImageView;
    private Button saveButton;
    String currentPhotoPath;
    private String mTitle;
    private String mDescription;
    private Bitmap finalBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        titleView = (AutoCompleteTextView) findViewById(R.id.get_title);
        descriptionView = (AutoCompleteTextView) findViewById(R.id.get_description);
        galleryButton = (Button) findViewById(R.id.gallery_button);
        cameraButton = (Button) findViewById(R.id.camera_button);
        mImageView = (ImageView) findViewById(R.id.get_imageView);
        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent myintent=new Intent(AddNoteActivity.this,AfterLogin.class);
                startActivity(myintent);


            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNoteActivity.this, "Opening Camera", LENGTH_SHORT).show();
                askCameraPermission();

            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNoteActivity.this, "Opening Gallery", LENGTH_SHORT).show();
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery,"Select Picture"), GALLER_REQ_CODE);

            }
        });
    }

    private void saveData() {
        mTitle = titleView.getText().toString();
        mDescription = descriptionView.getText().toString();
        NoteClass noteclass = new NoteClass(mTitle, mDescription, finalBitmap);
        mNoteClassArrayList.add(noteclass);
        SharedPreferences mSharepreferences = getSharedPreferences(NOTE_PREFS_1, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharepreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mNoteClassArrayList);
        editor.putString("note_list", json);
        editor.apply();

    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera Permission Required!!", LENGTH_SHORT).show();
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Bundle extras=data.getExtras();
            Bitmap image=(Bitmap)extras.get("data");
            mImageView.setImageBitmap(image);
            finalBitmap=image;

        }
        if (requestCode == GALLER_REQ_CODE && resultCode == Activity.RESULT_OK) {

            try{
                Uri selectedImage=data.getData();
                InputStream imageStream=getContentResolver().openInputStream(selectedImage);
                mImageView.setImageBitmap(BitmapFactory.decodeStream(imageStream));
                finalBitmap=BitmapFactory.decodeStream(imageStream);

            }catch(IOException exception){
                exception.printStackTrace();

            }


        }

    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));

    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private void loaddata() {
        SharedPreferences mSharepreferences = getSharedPreferences(NOTE_PREFS_1, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mSharepreferences.getString("note_list", null);
        Type type = new TypeToken<ArrayList<NoteClass>>() {
        }.getType();
        mNoteClassArrayList = gson.fromJson(json, type);
        if (mNoteClassArrayList == null) {
            mNoteClassArrayList = new ArrayList<>();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        loaddata();
    }
}