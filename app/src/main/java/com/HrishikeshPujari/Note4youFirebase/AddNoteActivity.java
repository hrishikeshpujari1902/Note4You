package com.HrishikeshPujari.Note4youFirebase;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;

public class AddNoteActivity extends AppCompatActivity {
    public static final int CAMERA_REQUEST_CODE = 101;
    public static final int GALLER_REQ_CODE = 100;
    private static final int PREFERRED_WIDTH = 250;
    private static final int PREFERRED_HEIGHT =250;



    private AutoCompleteTextView titleView;
    private AutoCompleteTextView descriptionView;
    private Button galleryButton;
    private Button cameraButton;
    private ImageView mImageView;
    private Button saveButton;
    private FirebaseAuth mAuth;
    private String userUID;
    private String mTitle;
    private String mDescription;
    private String currentDate;
    private String currentTime;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;

    private Uri imageUri;
    private String currentPhotoPath;


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
        mStorageReference=FirebaseStorage.getInstance().getReference();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        mDatabaseReference=database.getReference();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
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
        private void saveData(){
            mTitle=titleView.getText().toString();
            mDescription=descriptionView.getText().toString();
            currentDate= DateFormat.getDateInstance().format(new Date());
            currentTime= DateFormat.getTimeInstance().format(new Date());
            if(imageUri!=null){
                uploadToFirebase(imageUri);
            }else{
                Toast.makeText(this, "Please capture or select an Image.", LENGTH_SHORT).show();
            }
        }

    private void uploadToFirebase(Uri uri) {
        final StorageReference fileRef=mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        NoteClass noteClass=new NoteClass(mTitle,mDescription,uri.toString(),currentDate,currentTime);
                        mDatabaseReference.child("notes").child(userUID).push().setValue(noteClass);
                        Toast.makeText(AddNoteActivity.this, "Uploaded Successfully", LENGTH_SHORT).show();
                        Intent myintent=new Intent(AddNoteActivity.this,AfterLogin.class);
                        startActivity(myintent);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddNoteActivity.this, "Uploading Failed!!", LENGTH_SHORT).show();
            }
        });


    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
//    private void saveData() {
//        finalBitmap=((BitmapDrawable)mImageView.getDrawable()).getBitmap();
//        mStringBitmap=BitMapToString(finalBitmap);
//        mTitle=titleView.getText().toString();
//        mDescription=descriptionView.getText().toString();
//        String currentDate= DateFormat.getDateInstance().format(new Date());
//        String currentTime= DateFormat.getTimeInstance().format(new Date());
//        Log.d("addbutton","addnote executed");
//        Log.d("addbutton","title:"+mTitle+"    desription:"+mDescription+"   mStringbitmap:"+mStringBitmap+"date:"+currentDate+"Time:"+currentTime);
//        NoteClass noteClass=new NoteClass(mTitle,mDescription,mStringBitmap,currentDate,currentTime);
//        mDatabaseReference.child("notes").child(userUID).push().setValue(noteClass);
//        Intent myIntent=new Intent(this,AfterLogin.class);
//        startActivity(myIntent);}


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
            File f=new File(currentPhotoPath);
            imageUri=Uri.fromFile(f);
            mImageView.setImageURI(imageUri);



        }
        if (requestCode == GALLER_REQ_CODE && resultCode == Activity.RESULT_OK && data!=null) {
            imageUri=data.getData();
            mImageView.setImageURI(imageUri);
        }

    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
                        "com.HrishikeshPujari.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myintent=new Intent(this,AfterLogin.class);
        startActivity(myintent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent myintent=getIntent();
        userUID=myintent.getStringExtra("uid");




    }
}
