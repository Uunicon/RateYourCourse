package com.example.navifationtest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

    private MyDBHelper mydbhelper;

    public static final String UNIVERSITY_NAME = " university_name";
    public static final String UNIVERSITY_IMAGE_ID = "university_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mydbhelper = new MyDBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        Intent intent = getIntent();

        final String universityName= intent.getStringExtra(UNIVERSITY_NAME);

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
         String getCourse="math";

        //-----------待插入数据库查询寻语句-------------



        FloatingActionButton floatingActionButton = findViewById(R.id.floatbutton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.edit_Ucourse); //获取输入框
                final String inputText=editText.getText().toString();//输入框内容
                //queryItem();查询在 Course 表中是否有输入框中的课程
                if(inputText.isEmpty()){
                    Toast.makeText(SearchCourse.this,"请输入课程名称",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(queryItem(inputText)){
                        Intent intent1 = new Intent(SearchCourse.this,CourseActivity.class);
                        intent1.putExtra("course",inputText);
                        startActivity(intent1);
                    }
                    else{
                        Snackbar.make(v,"未找到该课程，点击右侧添加课程",Snackbar.LENGTH_LONG).setAction("添加课程",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent addcourseintent = new Intent(SearchCourse.this,AddCourseActivity.class);
                                        addcourseintent.putExtra("schoolName",universityName);
                                        addcourseintent.putExtra("courseName",inputText);
                                        startActivity(addcourseintent);                                    }
                                })
                                .show();
                    }
                }
            }
        });

    }//end on Create

    private boolean queryItem(String courseName){
//        EditText courseinput = findViewById(R.id.edit_Ucourse);
//        String CourseName = courseinput.getText().toString();
        SQLiteDatabase db = mydbhelper.getWritableDatabase();
        Cursor cur = db.query("Course",null,"CourseName=?",new String[]{courseName},null,null,null);
        if(cur!=null && cur.getCount() >= 1){
            cur.close();
            return true;
        }else{
            cur.close();
            return  false;
        }
    }


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
