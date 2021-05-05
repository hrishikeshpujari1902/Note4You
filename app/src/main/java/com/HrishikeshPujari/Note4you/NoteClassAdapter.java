package com.HrishikeshPujari.Note4you;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteClassAdapter extends CursorAdapter {
    public NoteClassAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.main_list_displaynotes,parent,false);
        view.setTag(new ViewHolder(view));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder =(ViewHolder)view.getTag();
        NoteClass noteClass=new NoteClass(cursor);
        holder.mTextView_Title.setText(noteClass.getTitle());
        holder.mTextView_Description.setText(noteClass.getDescription());
        holder.mImageView.setImageBitmap(noteClass.getBitmap());

    }
    private class ViewHolder{
        final ImageView mImageView;
        final TextView mTextView_Title;
        final TextView mTextView_Description;
        ViewHolder(View view){
            mImageView=view.findViewById(R.id.imageView_list);
            mTextView_Description=view.findViewById(R.id.display_description_list);
            mTextView_Title=view.findViewById(R.id.display_title_list);

        }
    }
}
