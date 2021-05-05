package com.HrishikeshPujari.Note4you;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class AfterLogin extends AppCompatActivity {
    public static final String NOTE_PREFS_1 = "note_prefs1";
    private ListView mListDisplayView;
    private ArrayList<NoteClass> mNoteClassArrayList;



    private AfterLoginAdapter mAdapter;
    private SearchView searchView;
    private Button addNewNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        Log.d("Quickchat","Oncreate() executed.");
        loaddata();

        addNewNoteButton=(Button)findViewById(R.id.addnote_button);
        addNewNoteButton=(Button)findViewById(R.id.addnote_button);
        mListDisplayView=(ListView)findViewById(R.id.note_class_list);
        EditText mSearchEditText=(EditText) findViewById(R.id.search_bar);
        mSearchEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mListDisplayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoteClass noteClass =mAdapter.getItem(position);
                Intent chatIntent=new Intent(AfterLogin.this, DetailViewActivity.class);
                chatIntent.putExtra("title",noteClass.getTitle());
                chatIntent.putExtra("description",noteClass.getDescription());
                chatIntent.putExtra("bitmap",noteClass.getBitmapString());
                startActivity(chatIntent);


            }
        });
        addNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newintent=new Intent(AfterLogin.this,AddNoteActivity.class);
                startActivity(newintent);
            }
        });


    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
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
    public void onStart(){
        super.onStart();
        Log.d("Quickchat","Onstart() executed");
        mAdapter=new AfterLoginAdapter(mNoteClassArrayList,this);
        mListDisplayView.setAdapter(mAdapter);
    }
}
