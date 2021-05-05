package com.HrishikeshPujari.Note4you;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;

public class AfterLoginAdapter extends BaseAdapter implements Filterable {
    private Activity mActivity;

    private String mDisplayName;
    private ArrayList<NoteClass> mNoteClassArrayList;


    private ArrayList<NoteClass> mNoteClassArrayListfiltered;




    public AfterLoginAdapter(ArrayList<NoteClass> mNoteClassArrayList1, Activity activity) {
        mActivity = activity;
        mNoteClassArrayList=mNoteClassArrayList1;
        mNoteClassArrayListfiltered=mNoteClassArrayList1;



    }

    @Override
    public Filter getFilter() {
        Filter filter =new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults=new FilterResults();
                if(constraint==null||constraint.toString().isEmpty()||constraint.length()==0){


                    filterResults.count=mNoteClassArrayListfiltered.size();
                    filterResults.values=mNoteClassArrayListfiltered;


                }else{

                    String searchstr=constraint.toString().toLowerCase();
                    ArrayList<NoteClass> resultData=new ArrayList<>();
                    for(NoteClass noteClass:mNoteClassArrayList){

                        if (noteClass.getTitle().toLowerCase().contains(searchstr) || noteClass.getDescription().toLowerCase().contains(searchstr)) {
                            resultData.add(noteClass);
                        }}
                    filterResults.count=resultData.size();
                    filterResults.values=resultData;


                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mNoteClassArrayList.clear();

                mNoteClassArrayList= (ArrayList<NoteClass>) results.values;
                notifyDataSetChanged();


            }
        };
        return filter;
    }

    static class ViewHolder{
        TextView title;
        TextView description;
        ImageView mImageView;
    }

    @Override
    public int getCount() {
        return mNoteClassArrayList.size();
    }

    @Override
    public NoteClass getItem(int position) {
        NoteClass noteClass=mNoteClassArrayList.get(position);
        return noteClass;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.main_list_displaynotes, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.display_title_list);
            holder.description = (TextView) convertView.findViewById(R.id.display_description_list);
            holder.mImageView=(ImageView)convertView.findViewById(R.id.imageView_list);
            convertView.setTag(holder);
        }
        final NoteClass noteClass=getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();


        String title1 = noteClass.getTitle();
        holder.title.setText(title1);
        String description1 = noteClass.getDescription();
        holder.description.setText(description1);
        holder.mImageView.setImageBitmap(StringToBitMap(noteClass.getBitmapString()));



        return convertView;
    }
    public Bitmap StringToBitMap(String encodedString) {

        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
