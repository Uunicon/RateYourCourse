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

public class TeacherAdapter extends ArrayAdapter <Teacher> {

    private int resourceId;

    public TeacherAdapter(Context context, int textViewResourceId, List<Teacher> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Teacher teacher = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView teacherImage= view.findViewById(R.id.teacher_image);
        TextView teacherNameText = view.findViewById(R.id.teacherName_text);
        TextView teacherTitleText = view.findViewById(R.id.teacherTitle_text);
        teacherImage.setImageResource(teacher.getImageId());
        teacherNameText.setText(teacher.Nametext());
        teacherTitleText.setText(teacher.Titletext());
        return view;
    }

}
