package com.HrishikeshPujari.Note4you;

import android.content.Intent;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;

import android.widget.GridView;



import androidx.appcompat.app.AppCompatActivity;




public class AfterLogin extends AppCompatActivity {


    



    private NoteClassAdapter mAdapter;

    private Button addNewNoteButton;
    private MemoryObHelper dbhelper;
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        Log.d("Quickchat","Oncreate() executed.");
        addNewNoteButton=(Button)findViewById(R.id.addnote_button);
        mGridView=(GridView)findViewById(R.id.main_grid);

        this.dbhelper=new MemoryObHelper(this);
        mAdapter=new NoteClassAdapter(this,this.dbhelper.readAllMemories(),false);
        mGridView.setAdapter(mAdapter);







        addNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newintent=new Intent(AfterLogin.this,AddNoteActivity.class);
                startActivity(newintent);
            }
        });


    }









    @Override
    public void onStart(){
        super.onStart();

        this.dbhelper=new MemoryObHelper(this);
        mAdapter=new NoteClassAdapter(this,this.dbhelper.readAllMemories(),false);
        mGridView.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((CursorAdapter)mGridView.getAdapter()).swapCursor(this.dbhelper.readAllMemories());
    }
}
