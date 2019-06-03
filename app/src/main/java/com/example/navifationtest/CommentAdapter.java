package com.example.navifationtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class CommentAdapter extends ArrayAdapter <Comments> {

    private  int resourceId;

    public CommentAdapter(Context context, int textViewResourceId,List<Comments> objects){
        super(context, textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Comments comments = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView commentsImage= view.findViewById(R.id.comment_image);
        TextView commentsText = view.findViewById(R.id.comment_text);
        commentsImage.setImageResource(comments.getImageId());
        commentsText.setText(comments.getText());
        return view;
    }


}