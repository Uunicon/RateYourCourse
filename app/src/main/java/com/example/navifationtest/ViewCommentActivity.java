package com.example.navifationtest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewCommentActivity extends AppCompatActivity {
    private List<Comments> commentsList = new ArrayList<>();
    private MyDBHelper mydbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mydbhelper = new MyDBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String courseName = intent.getStringExtra("course");
        this.setTitle(courseName);

        //--------课程评价-------------------------
        initComment(courseName);
        CommentAdapter commentAdapter = new CommentAdapter(ViewCommentActivity.this,R.layout.comment_item, commentsList);
        ListView CommentlistView = findViewById(R.id.comment_list);
        CommentlistView.setAdapter(commentAdapter);
        //----------------------------------

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addintent = new Intent(ViewCommentActivity.this,AddCommentActivity.class);
                addintent.putExtra("course",courseName);
                startActivity(addintent);
            }
        });
    }

    private void initComment(String CourseName){
//        数据库查询语句
        //------评论内容数组------
        String[] RateComment =  queryRateComment(CourseName);
        //-------评论数目------
        int RateCnt = IsRateCommentEmpty(CourseName);
        if(RateCnt==0){
            //--------该课程评论为空时操作--------
            Comments p1 = new Comments("该课程暂无评论",R.drawable.post1);
            commentsList.add(p1);
        }
        else{
            //------通过评论数Rate进行评论页面布局
            for(int i=0;i<RateCnt;i++){
                int type = (i+1)%5;
                switch (type){
                    case 1:
                        Comments p1 = new Comments(RateComment[i],R.drawable.post1);
                        commentsList.add(p1);
                        break;
                    case 2:
                        Comments p2 = new Comments(RateComment[i],R.drawable.post2);
                        commentsList.add(p2);
                        break;
                    case 3:
                        Comments p3 = new Comments(RateComment[i],R.drawable.post3);
                        commentsList.add(p3);
                        break;
                    case 4:
                        Comments p4 = new Comments(RateComment[i],R.drawable.post4);
                        commentsList.add(p4);
                        break;
                    case 5:
                        Comments p5 = new Comments(RateComment[i],R.drawable.post5);
                        commentsList.add(p5);
                        break;
                }

            }
        }
//      查出当前课程所有评论 及其数量
    }

    //-------判断该课程是否有评论，是则返回0，否则评论数-------
    private int IsRateCommentEmpty(String CourseName){
        SQLiteDatabase db = mydbhelper.getWritableDatabase();
        //-------通过CourseName查找CourseID-----------
        Cursor Course_cur = db.query("Course",null,"CourseName=?",new String[]{CourseName},null,null,null);
        Course_cur.moveToFirst();
        int CourseID = Course_cur.getInt(Course_cur.getColumnIndex("CourseID"));
        Course_cur.close();
        //-------通过Course ID得到该课程的所有评分信息---------
        Cursor Rate_cur = db.query("Rate",null,"CourseID=?",new String[]{CourseID + ""},null,null,null);
        int RowCnt  = 0;
        if(Rate_cur.moveToFirst()) {
            do {
                RowCnt++;
            } while (Rate_cur.moveToNext());
        }
        Rate_cur.close();
        if(RowCnt == 0){
            return 0;
        }else{
            return RowCnt;
        }
    }

    //---------数据库Rate表评论查询---------
    private String[] queryRateComment(String CourseName){
        SQLiteDatabase db = mydbhelper.getWritableDatabase();
        //-------通过CourseName查找CourseID-----------
        Cursor Course_cur = db.query("Course",null,"CourseName=?",new String[]{CourseName},null,null,null);
        Course_cur.moveToFirst();
        int CourseID = Course_cur.getInt(Course_cur.getColumnIndex("CourseID"));
        Course_cur.close();
        //-------通过Course ID得到该课程的所有评分信息---------
        Cursor Rate_cur = db.query("Rate",null,"CourseID=?",new String[]{CourseID + ""},null,null,null);
        //------获取评论数目------
        int RowCnt = 0;
        if(Rate_cur.moveToFirst()) {
            do {
                RowCnt++;
            } while (Rate_cur.moveToNext());
        }

        String [] RateComment = new String[RowCnt];

        //------获取评论内容--------
        int Cnt = 0;
        if(Rate_cur.moveToFirst()) {
            do {
                RateComment[Cnt] = Rate_cur.getString(Rate_cur.getColumnIndex("RateComment"));
                Cnt++;
            } while (Rate_cur.moveToNext());
        }
        Toast.makeText(this, "评论数: "+ Cnt, Toast.LENGTH_SHORT).show();
        Rate_cur.close();
        return RateComment;
    }
}