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
import android.widget.EditText;
import android.widget.Toast;

public class AddCommentActivity extends AppCompatActivity {
    private MyDBHelper mydbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mydbhelper = new MyDBHelper(this);
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
                insertItem();
                Snackbar.make(view, "提交评价成功", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent1 = new Intent(AddCommentActivity.this,CourseActivity.class);
                intent1.putExtra("course",courseName);
                startActivity(intent1);
                finish();
            }
        });
    }

    //--------------数据库数据Rate表插入，Course表更新
    private void insertItem(){
        String CourseName = getIntent().getStringExtra("course");//课程名字
        int StudentID = 1001;//并不知道学生ID，备用
        //----------课程知识容量-----------
        EditText editTextKnowledge = findViewById(R.id.knowledgePoint);
        //String KnowledgePoint=editTextKnowledge.getText().toString();
        //----------课程趣味性-----------
        EditText editTextInterest = findViewById(R.id.interstPoint);
        //String interestPoint=editTextKnowledge.getText().toString();
        //----------课后作业强度-----------
        EditText editTextHomework = findViewById(R.id.homeworkPoint);
        //String homeworkPoint=editTextKnowledge.getText().toString();
        //----------同学互动情况-----------
        EditText editTextInteract = findViewById(R.id.interactPoint);
        //String interactPoint=editTextKnowledge.getText().toString();
        //----------成绩给分情况-----------
        EditText editTextGrade = findViewById(R.id.gradePoint);
        //String gradePoint=editTextKnowledge.getText().toString();
        //----------评论-----------
        EditText editTextComment = findViewById(R.id.commentContent);
        String commentContent=editTextComment.getText().toString();

        SQLiteDatabase db = mydbhelper.getWritableDatabase();

        //-----------通过课程名称获取课程ID--------
        Cursor cur = db.query("Course",null,"CourseName=?",new String[]{CourseName},null,null,null);
        cur.moveToFirst();
        int CourseID = cur.getInt(cur.getColumnIndex("CourseID"));
        cur.close();

        //-----------查询Rate表获取Rate ID属性， 从而得到新插入的评价页面的RateID------
        //Cursor _cur = db.query("Rate",null,"CourseID=?",new String[]{CourseID},null,null,null);
        Cursor _cur = db.query("Rate",null,null,null,null,null,null);
        int RateID = 1001;//无评价时自动使用默认Rate ID
        if(_cur.moveToLast()) {
            //获取最大Rate ID
            RateID = _cur.getInt(_cur.getColumnIndex("RateID"));
            RateID = RateID + 1;
        }
        _cur.close();

        //-----------执行插入评论操作---------
        String str_insert_sql = "insert into Rate(RateID, RateKnowlCap, RateEnjoy, RateHomework, RateInteract, RateScore, RateComment, CourseID, StudentID)" +
                                " values(" + RateID + "," + editTextKnowledge.getText() + "," + editTextInterest.getText() + "," + editTextHomework.getText() + "," + editTextInteract.getText() + "," + editTextGrade.getText() +
                                ",'" + commentContent + "'," + CourseID + "," + StudentID + ");" ;

        db.execSQL(str_insert_sql);

        //-----------更新数据库中Course中的评分-----------
        //通过课程ID获取课程在Rate表中的各项评分
        Cursor update_cur = db.query("Rate",null,"CourseID=?",new String[]{CourseID + ""},null,null,null);
        update_cur.moveToFirst();
        float [] RateOptions = new float[5];
        RateOptions[0] = 0;
        RateOptions[1] = 0;
        RateOptions[2] = 0;
        RateOptions[3] = 0;
        RateOptions[4] = 0;
        int count = 0;
        //Toast.makeText(this, "RateKnowlCap: "+ RateOptions[0], Toast.LENGTH_SHORT).show();
        do {
            count = count + 1;
            RateOptions[0] = RateOptions[0] + update_cur.getFloat(update_cur.getColumnIndex("RateKnowlCap"));
            RateOptions[1] = RateOptions[1] + update_cur.getFloat(update_cur.getColumnIndex("RateEnjoy"));
            RateOptions[2] = RateOptions[2] + update_cur.getFloat(update_cur.getColumnIndex("RateHomework"));
            RateOptions[3] = RateOptions[3] + update_cur.getFloat(update_cur.getColumnIndex("RateInteract"));
            RateOptions[4] = RateOptions[4] + update_cur.getFloat(update_cur.getColumnIndex("RateScore"));

        }while(update_cur.moveToNext());
        RateOptions[0] = RateOptions[0]/count;
        RateOptions[1] = RateOptions[1]/count;
        RateOptions[2] = RateOptions[2]/count;
        RateOptions[3] = RateOptions[3]/count;
        RateOptions[4] = RateOptions[4]/count;
        Toast.makeText(this, "更新: "+ CourseID, Toast.LENGTH_SHORT).show();
        db.execSQL("update Course set RateKnowlCap = ?, RateEnjoy = ?, RateHomework = ?, RateInteract = ?, RateScore = ? where CourseID = ?",
                    new String[]{RateOptions[0] + "",RateOptions[1] + "",RateOptions[2] + "",RateOptions[3] + "",RateOptions[4] + "",CourseID + ""});
        update_cur.close();
        Toast.makeText(this, "添加评价成功", Toast.LENGTH_SHORT).show();
    }

}
