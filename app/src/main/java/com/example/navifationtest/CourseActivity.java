package com.example.navifationtest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Locale;


public class CourseActivity extends AppCompatActivity {

    private MyDBHelper mydbhelper;
    private String[] RateIndex={"总分","课程知识容量","课程趣味性","课后作业强度","同学课堂互动","成绩给分情况"};

    private List<CourseRate> crourseRatelist= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mydbhelper = new MyDBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Intent intent = getIntent();
        final String courseName = intent.getStringExtra("course");
        this.setTitle(courseName);

        //--------------课程评分部分---------
        initCourseRate();
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
                if(isTeacherExists()){
                    Intent viewPorfessorintent1 = new Intent(CourseActivity.this,ProfessorActivity.class);
                    viewPorfessorintent1.putExtra("course",courseName);
                    startActivity(viewPorfessorintent1);
                }
                else {//不存在授课老师
                    Snackbar.make(v, "当前课程不存在老师，点击右侧添加老师", Snackbar.LENGTH_LONG).setAction("添加老师",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent viewteacherintent = new Intent(CourseActivity.this, ViewTeacherListActivity.class);
                                    viewteacherintent.putExtra("courseName", courseName);
                                    startActivity(viewteacherintent);
                                }
                            })
                            .show();
                }
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
                finish();
            }
        });


    }

    //----------查询当前课程教师是否存在-----------
    private boolean isTeacherExists(){
        SQLiteDatabase db = mydbhelper.getWritableDatabase();
        String CourseName = getIntent().getStringExtra("course");
        //--------通过课程名获取课程号------
        Cursor cur = db.query("Course",null,"CourseName=?",new String[]{CourseName},null,null,null);
        cur.moveToFirst();
        int CourseID = cur.getInt(cur.getColumnIndex("CourseID"));//通过课程名称获取课程ID
        cur.close();

        //-------通过课程号搜索Teach（讲授）表中对应的TeacherID（教师号）---
        //---存在则返回True，否则返回false
        Cursor _cur = db.query("Teach",null,"CourseID=?",new String[]{CourseID + ""},null,null,null);
        if(_cur.moveToFirst()){
            _cur.close();
            return true;
        }else{
            _cur.close();
            return false;
        }
    }

    //----------查询相应课程的各项评分----------
    private float[] queryItem(){
        String CourseName = getIntent().getStringExtra("course");
        SQLiteDatabase db = mydbhelper.getWritableDatabase();
        Cursor cur = db.query("Course",null,"CourseName=?",new String[]{CourseName},null,null,null);
        cur.moveToFirst();
        int CourseID = cur.getInt(cur.getColumnIndex("CourseID"));//通过课程名称获取课程ID
        //Toast.makeText(this, "CourseID: "+ CourseID, Toast.LENGTH_SHORT).show();

        Cursor _cur = db.query("Course",null,"CourseID=?",new String[]{CourseID + ""},null,null,null);
        _cur.moveToFirst();
        float [] RateOptions = new float[5];
        RateOptions[0] = _cur.getFloat(_cur.getColumnIndex("RateKnowlCap"));//通过课程ID获取课程各项评分
        RateOptions[1] = _cur.getFloat(_cur.getColumnIndex("RateEnjoy"));
        RateOptions[2] = _cur.getFloat(_cur.getColumnIndex("RateHomework"));
        RateOptions[3] = _cur.getFloat(_cur.getColumnIndex("RateInteract"));
        RateOptions[4] = _cur.getFloat(_cur.getColumnIndex("RateScore"));
        cur.close();
        _cur.close();
        return RateOptions;
    }


    private void initCourseRate(){
        float [] RateOptions = queryItem();
        CourseRate total = new CourseRate("总分",String.format(Locale.US,"%.2f",(RateOptions[0] + RateOptions[1] + RateOptions[2] + RateOptions[3] + RateOptions[4])/5));
        crourseRatelist.add(total);
        CourseRate content = new CourseRate("课程知识容量",String.format(Locale.US,"%.2f",RateOptions[0]));
        crourseRatelist.add(content);
        CourseRate interest = new CourseRate("课程趣味性",String.format(Locale.US,"%.2f",RateOptions[1]));
        crourseRatelist.add(interest);
        CourseRate homework = new CourseRate("课后作业强度",String.format(Locale.US,"%.2f",RateOptions[2]));
        crourseRatelist.add(homework);
        CourseRate interact = new CourseRate("同学互动情况",String.format(Locale.US,"%.2f",RateOptions[3]));
        crourseRatelist.add(interact);
        CourseRate grade = new CourseRate("成绩给分情况",String.format(Locale.US,"%.2f",RateOptions[4]));
        crourseRatelist.add(grade);
    }

}





