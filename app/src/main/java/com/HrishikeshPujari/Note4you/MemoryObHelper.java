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

    public MemoryObHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addMemory(){
        SQLiteDatabase db=getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(NoteContract.NoteEntry.COLUMN_TITLE,NoteClass.getTitle());
        contentValues.put(NoteContract.NoteEntry.COLUMN_DESCRIPTION,NoteClass.getDescription());
        contentValues.put(NoteContract.NoteEntry.COLUMN_IMAGE,NoteClass.getBitmap());
        return db.insert(NoteContract.NoteEntry.TABLE_NAME,null,contentValues)!=-1;


    }
}
