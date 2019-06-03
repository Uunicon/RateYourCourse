package com.example.navifationtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class UnivAdapter extends RecyclerView.Adapter <UnivAdapter.ViewHolder>{
    private Context mContext;
    private List<University> mUniversityList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView universityImage;
        TextView universityName;

        public ViewHolder (View view){
            super (view);
            cardView = (CardView) view;
            universityImage =(ImageView) view.findViewById(R.id.university_image);
            universityName =(TextView) view.findViewById(R.id.university_name);
        }
    }


    public UnivAdapter(List<University> universitylist){
        mUniversityList = universitylist;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.university_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                int position = holder.getAdapterPosition();

                position=position+1;
                University university = mUniversityList.get(position);
                Intent intent = new Intent(mContext, SearchCourse.class);
                intent.putExtra(SearchCourse.UNIVERSITY_NAME, university.getName());
                intent.putExtra(SearchCourse.UNIVERSITY_IMAGE_ID,university.getImageId());
                mContext.startActivity(intent);
            }
        });

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        University university =mUniversityList.get(position);
        holder.universityName.setText(university.getName());
        Glide.with(mContext).load(university.getImageId()).into(holder.universityImage);

    }
    @Override
    public int getItemCount(){
        return mUniversityList.size();
    }
}