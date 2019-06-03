package com.example.navifationtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CourseActivity extends AppCompatActivity {

    private String[] RateIndex={"总分","课程知识容量","课程趣味性","课后作业强度","同学课堂互动","成绩给分情况"};

    private List<CourseRate> crourseRatelist= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Intent intent = getIntent();
        final String courseName = intent.getStringExtra("course");
        this.setTitle(courseName);

        //--------------课程评分部分---------
        // ------20190603----------函数有更改 加了传递参数 传入参数为 该课程名称
        initCourseRate(courseName);
        //-----------------------
        CRAdapter adapter = new CRAdapter(CourseActivity.this,
                R.layout.rate_item, crourseRatelist);
        ListView listView = findViewById(R.id.rate_list);
        listView.setAdapter(adapter);

        //---------------------------------------------------

        //-------评论按钮------
        TextView commentTextView = findViewById(R.id.commentInRate);
        commentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewCommentintent1 = new Intent(CourseActivity.this,ViewCommentActivity.class);
                viewCommentintent1.putExtra("course",courseName);
                startActivity(viewCommentintent1);
            }
        });
        //-------------------


        //-------授课教师------
        TextView professorTextView = findViewById(R.id.teacherInRate);
        professorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewPorfessorintent1 = new Intent(CourseActivity.this,ProfessorActivity.class);
                viewPorfessorintent1.putExtra("course",courseName);
                startActivity(viewPorfessorintent1);
            }
        });
        //-------------------

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addintent = new Intent(CourseActivity.this,AddCommentActivity.class);
                addintent.putExtra("course",courseName);
                startActivity(addintent);
            }
        });


    }

    //传入变量为 课程名称
    private void initCourseRate(String courseName){
        //数据库搜索是否存在该课程
        //if( true)  课程评分存在  显示下列内容
        //{
        //数据库返回 个维度分值
            CourseRate total = new CourseRate("总分","9");
            crourseRatelist.add(total);
            CourseRate content = new CourseRate("课程知识容量","9");
            crourseRatelist.add(content);
            CourseRate interest = new CourseRate("课程趣味性","9");
            crourseRatelist.add(interest);
            CourseRate homework = new CourseRate("课后作业强度","9");
            crourseRatelist.add(homework);
            CourseRate interact = new CourseRate("同学互动情况","9");
            crourseRatelist.add(interact);
            CourseRate grade = new CourseRate("成绩给分情况","9");
            crourseRatelist.add(grade);
        //}
        //else  课程暂无评分
//        {
//            CourseRate total = new CourseRate("总分","0");
//            crourseRatelist.add(total);
//            Toast.makeText(SearchCourse.this,"该课程暂无评分",Toast.LENGTH_SHORT).show();
//        }

    }


}





