package com.example.navifationtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class AddCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        final String courseName = intent.getStringExtra("course");
        this.setTitle(courseName);

        //----------课程知识容量-----------
        EditText editTextKnowledge = findViewById(R.id.knowledgePoint);
        String KnowledgePoint=editTextKnowledge.getText().toString();
        //----------课程趣味性-----------
        EditText editTextInterest = findViewById(R.id.interstPoint);
        String interestPoint=editTextKnowledge.getText().toString();
        //----------课后作业强度-----------
        EditText editTextHomework = findViewById(R.id.homeworkPoint);
        String homeworkPoint=editTextKnowledge.getText().toString();
        //----------同学互动情况-----------
        EditText editTextInteract = findViewById(R.id.interactPoint);
        String interactPoint=editTextKnowledge.getText().toString();
        //----------成绩给分情况-----------
        EditText editTextGrade = findViewById(R.id.gradePoint);
        String gradePoint=editTextKnowledge.getText().toString();
        //----------评论-----------
        EditText editTextComment = findViewById(R.id.commentContent);
        String commentContent=editTextKnowledge.getText().toString();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "提交评价成功", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent1 = new Intent(AddCommentActivity.this,CourseActivity.class);
                intent1.putExtra("course",courseName);
                startActivity(intent1);
                finish();
            }
        });
    }

}
