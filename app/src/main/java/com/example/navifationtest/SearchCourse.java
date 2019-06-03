package com.example.navifationtest;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class SearchCourse extends AppCompatActivity {

    public static final String UNIVERSITY_NAME = " university_name";
    public static final String UNIVERSITY_IMAGE_ID = "university_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        Intent intent = getIntent();
        String universityName= intent.getStringExtra(UNIVERSITY_NAME);
        int universityImageId = intent.getIntExtra(UNIVERSITY_IMAGE_ID,0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        ImageView universityImageView = findViewById(R.id.university_image_view);
        TextView universityContentText = findViewById(R.id.university_content_text);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar !=null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
        collapsingToolbarLayout.setTitle(universityName);
        Glide.with(this).load(universityImageId).into(universityImageView);
        String universityContent= "请输入查询的课程名称";
        universityContentText.setText(universityContent);


        //-----------待插入数据库查询寻语句-------------
        final String getCourse="math";

        //-----------待插入数据库查询寻语句-------------


        final EditText editText = findViewById(R.id.edit_Ucourse);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatbutton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText=editText.getText().toString();
                //   数据库查询课程 查询字段inputText
                if(inputText.equals(getCourse)){
                    Intent intent1 = new Intent(SearchCourse.this,CourseActivity.class);
                    intent1.putExtra("course",getCourse);
                    startActivity(intent1);
                }
                else{
                    Toast.makeText(SearchCourse.this,"未找到该课程",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }//end on Create



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
