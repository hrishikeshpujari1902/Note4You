package com.HrishikeshPujari.Note4youFirebase;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;

import android.widget.GridView;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AfterLogin extends AppCompatActivity {


    public static final String APP_TAG = "Note4You";
    private NoteClassAdapter mAdapter;

    private FirebaseAuth mAuth;
    private ListView mListView;
    private FloatingActionButton mFloatingActionButton;
    private String userUID;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        Log.d(APP_TAG,"Oncreate() executed.");
        mListView=(ListView)findViewById(R.id.main_list) ;
        mFloatingActionButton=(FloatingActionButton)findViewById(R.id.floatingActionButton);


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newintent=new Intent(AfterLogin.this,AddNoteActivity.class);
                newintent.putExtra("uid",userUID);
                startActivity(newintent);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoteClass noteClass=mAdapter.getItem(position);
                Intent newIntent=new Intent(AfterLogin.this,DetailViewActivity.class);
                newIntent.putExtra("title",noteClass.getTitle());
                newIntent.putExtra("description",noteClass.getDescription());
                newIntent.putExtra("image",noteClass.getImageUrl());
                newIntent.putExtra("date",noteClass.getCurrentDate());
                newIntent.putExtra("time",noteClass.getCurrentTime());
                startActivity(newIntent);
            }
        });









    }









    @Override
    public void onStart(){
        super.onStart();
        mAdapter=new NoteClassAdapter(this);
        mListView.setAdapter(mAdapter);
        Intent myintent=getIntent();
        userUID=myintent.getStringExtra("uid");
    }

}
