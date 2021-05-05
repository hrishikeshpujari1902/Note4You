package com.HrishikeshPujari.Note4you;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;



import androidx.annotation.Nullable;

public class MemoryObHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE=" TEXT";
    private static final String INTEGER_TYPE=" INTEGER";
    private static final String COMMA_SEP=",";
    private static final String DATABASE_NAME="memories.db";
    private static final int DATBASE_VERSION=1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NoteContract.NoteEntry.TABLE_NAME + " (" +
                    NoteContract.NoteEntry._ID + " INTEGER PRIMARY KEY," +
                    NoteContract.NoteEntry.COLUMN_TITLE + " TEXT," +
                    NoteContract.NoteEntry.COLUMN_DESCRIPTION + " TEXT," +
                    NoteContract.NoteEntry.COLUMN_IMAGE + " TEXT)";

    public MemoryObHelper(Context context) {
        super(context, DATABASE_NAME,null,DATBASE_VERSION);

    }
    public Cursor readAllMemories(){
        SQLiteDatabase db=getReadableDatabase();
        return db.query(
                NoteContract.NoteEntry.TABLE_NAME,
                null,null,null,null,null,null
        );
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addMemory(NoteClass noteClass){
        SQLiteDatabase db=getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(NoteContract.NoteEntry.COLUMN_TITLE,noteClass.getTitle());
        contentValues.put(NoteContract.NoteEntry.COLUMN_DESCRIPTION,noteClass.getDescription());
        contentValues.put(NoteContract.NoteEntry.COLUMN_IMAGE,noteClass.getImageaAsString());
        return db.insert(NoteContract.NoteEntry.TABLE_NAME,null,contentValues)!=-1;


    }
}
