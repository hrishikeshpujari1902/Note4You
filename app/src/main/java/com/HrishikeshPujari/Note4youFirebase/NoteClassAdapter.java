package com.HrishikeshPujari.Note4youFirebase;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NoteClassAdapter extends BaseAdapter {
    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private String userUID;
    private ArrayList<DataSnapshot> mSnapshotList;
    private final ChildEventListener mListener=new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            mSnapshotList.add(snapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    public NoteClassAdapter(Activity activity){
        mActivity=activity;
        FirebaseUser user=mAuth.getInstance().getCurrentUser();
        userUID=user.getUid();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        mDatabaseReference=database.getReference();
        mDatabaseReference.child("notes").child(userUID).addChildEventListener(mListener);
        mSnapshotList=new ArrayList<>();
    }
    static class ViewHolder{
        TextView titleTextView;
        TextView descriptionTextView;
        TextView dateTextView;
        TextView timeTextView;
        ImageView mImageView;

    }

    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public NoteClass getItem(int position) {
        DataSnapshot snapshot=mSnapshotList.get(position);
        return snapshot.getValue(NoteClass.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.main_list_displaynotes,parent,false);
            final ViewHolder holder=new ViewHolder();
            holder.titleTextView=(TextView)convertView.findViewById(R.id.display_title_list);
            holder.descriptionTextView=(TextView)convertView.findViewById(R.id.display_description_list);
            holder.dateTextView=(TextView)convertView.findViewById(R.id.display_date);
            holder.timeTextView=(TextView)convertView.findViewById(R.id.display_time);
            holder.mImageView=(ImageView)convertView.findViewById(R.id.imageView_list);
            convertView.setTag(holder);
        }
        final NoteClass noteClass=getItem(position);
        final ViewHolder holder=(ViewHolder)convertView.getTag();
        String mTitle=noteClass.getTitle();
        holder.titleTextView.setText(mTitle);
        String mDescription=noteClass.getDescription();
        holder.descriptionTextView.setText(mDescription);
        String mDate=noteClass.getCurrentDate();
        holder.dateTextView.setText(mDate);
        String mTime=noteClass.getCurrentTime();
        holder.timeTextView.setText(mTime);
        Glide.with(mActivity).load(noteClass.getImageUrl()).into(holder.mImageView);
        return convertView;

    }
}
