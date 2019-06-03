package com.example.navifationtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.navifationtest.CourseRate;
import com.example.navifationtest.R;

import java.util.List;

public class CRAdapter extends ArrayAdapter<CourseRate> {
    private int resourceId;
    public CRAdapter(Context context, int textViewResourceId, List<CourseRate> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        CourseRate courseRate=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView rateName= view.findViewById(R.id.rate_name);
        TextView rateValue= view.findViewById(R.id.rate_value);
        rateName.setText(courseRate.getRateName());
        rateValue.setText(courseRate.getRateValue());
        return view;
    }
}
