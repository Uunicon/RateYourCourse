package com.example.navifationtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class ViewCommentActivity extends AppCompatActivity {
    private List<Comments> commentsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String courseName = intent.getStringExtra("course");
        this.setTitle(courseName);

        //--------课程评价-------------------------
        initComment();
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

    private void initComment(){
//        数据库查询语句
//      查出当前课程所有评论 及其数量
        Comments p1 = new Comments("课程很满意，老师给分好",R.drawable.post1);
        commentsList.add(p1);
        Comments p2 = new Comments("难度很大，挂科了",R.drawable.post3);
        commentsList.add(p2);
        Comments p3 = new Comments("大佬多压力大",R.drawable.post2);
        commentsList.add(p3);
        Comments p4 = new Comments("作业多，老师讲的快",R.drawable.post4);
        commentsList.add(p4);
        Comments p5 = new Comments("只要努力，老师给分不差",R.drawable.post5);
        commentsList.add(p5);
    }
}
