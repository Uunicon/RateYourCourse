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

public class AddCourseActivity extends AppCompatActivity {
    private MyDBHelper mydbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mydbhelper = new MyDBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        final String schoolName = intent.getStringExtra("schoolName");
        final String courseName = intent.getStringExtra("courseName");
        //----------学校-----------

        final EditText editTextSchool = findViewById(R.id.addschoolname);
        editTextSchool.setText(schoolName);
        //----------课程-----------
        final EditText editTextCourse = findViewById(R.id.addcoursename);
        editTextCourse.setText(courseName);
        //----------老师-----------
        final EditText editTextTeacher = findViewById(R.id.addteachername);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addschoolname=editTextSchool.getText().toString();
                String addcoursename=editTextCourse.getText().toString();
                //String addteachername=editTextSchool.getText().toString();
                String addteachername=editTextTeacher.getText().toString();
                if(addschoolname.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "学校不能为空", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(addcoursename.isEmpty()) {
                        Toast.makeText(AddCourseActivity.this, "课程不能为空", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        insertCourse(addcoursename,addschoolname,addteachername);
                        Intent intent1 = new Intent(AddCourseActivity.this,CourseActivity.class);
                        intent1.putExtra("course",addcoursename);
                        startActivity(intent1);
                        finish();
                    }
                }
            }
        });
    }

    //-----将要添加的课程信息插入数据库中---------
    private void insertCourse(String CourseName, String SchoolName, String TeacherName){
        //----courseID 自行生成  评分采用默认值，School ID通过School Name查表获取
        SQLiteDatabase db = mydbhelper.getWritableDatabase();

        //---------计算添加Course的Course ID---------
        Cursor CourseID_cur = db.query("Course",null,null,null,null,null,null);
        int CourseID = 1001;//无课程时自动赋值
        if(CourseID_cur.moveToLast()){
            CourseID = CourseID_cur.getInt(CourseID_cur.getColumnIndex("CourseID"));
            CourseID = CourseID + 1;
        }
        CourseID_cur.close();

        //----------获取School的SchoolID--------
        Cursor SchoolID_cur = db.query("School",null,"SchoolName=?",new String[]{SchoolName},null,null,null);
        int SchoolID = 1001;
        if(SchoolID_cur.moveToLast()){
            //存在该学校名则直接得到SchoolID
            SchoolID = SchoolID_cur.getInt(SchoolID_cur.getColumnIndex("SchoolID"));
        }else{
            //不存在该学校则自动添加SchoolID以及SchoolName
            Cursor _SchoolID_cur = db.query("School",null,null,null,null,null,null);
            if(_SchoolID_cur.moveToLast()){
                //School表中存在SchoolID则取得后 最大ID + 1
                SchoolID = _SchoolID_cur.getInt(_SchoolID_cur.getColumnIndex("SchoolID"));
                SchoolID = SchoolID + 1;
            }else{
                //School表中不存在则赋值1001 申明时已赋值
                //SchoolID = 1001;
            }
            //进行SchoolID的插入操作
            String SchoolInsert_sql = "insert into School(SchoolID, SchoolName)" +
                                    " values(" + SchoolID + ", '" + SchoolName  +"');";
            db.execSQL(SchoolInsert_sql);
            _SchoolID_cur.close();
        }
        SchoolID_cur.close();

        //----------通过School ID 以及 Teacher Name 获取TeacherID
        Cursor TeacherExist_cur = db.query("Teacher",null,"SchoolID=? and TeacherName=?",new String[]{SchoolID + "", TeacherName},null,null,null);
        int TeacherID = 1001; //TeacherID 初始化
        if(TeacherExist_cur.moveToFirst()){
            //----------存在则直接添加CourseID 与 TeacherID至 Teach表中
            TeacherID = TeacherExist_cur.getInt(TeacherExist_cur.getColumnIndex("TeacherID"));
            String TeachInsert_sql = "insert into Teach(TeacherID, CourseID)" + " values(" + TeacherID + ", " + CourseID + ");";
            db.execSQL(TeachInsert_sql);
        }else{
            //不存在则 插入 教师信息至Teacher表，并更新Teach表

            //获取当前插入Teacher表的TeacherID
            Cursor ReadLastTeacherID_cur = db.query("Teacher",null,null,null,null,null,null);
            if(ReadLastTeacherID_cur.moveToLast()){
                TeacherID = ReadLastTeacherID_cur.getInt(ReadLastTeacherID_cur.getColumnIndex("TeacherID"));
                TeacherID = TeacherID + 1;
            }else{
                //TeacherID = 1001;
            }
            ReadLastTeacherID_cur.close();

            //插入 教师表 数据
            String TeacherInsert_sql = "insert into Teacher(TeacherID,TeacherName,JobTitle,SchoolID,CollegeID) values("
                                        + TeacherID + ",'" + TeacherName + "','Professor'," + SchoolID + ",1001);";
            db.execSQL(TeacherInsert_sql);

            //插入 讲授表 数据
            String TeachNew_sql = "insert into Teach(TeacherID, CourseID)" + " values(" + TeacherID + ", " + CourseID + ");";
            db.execSQL(TeachNew_sql);

        }
        TeacherExist_cur.close();
        //-------通过获取的信息进行Course表插入操作-------
        String str_insert_sql = "insert into Course(CourseID, CourseName, RateKnowlCap, RateEnjoy, RateHomework, RateInteract, RateScore, SchoolID)" +
                " values(" + CourseID + ",'" + CourseName + "'," + 0.0 + "," + 0.0 + "," + 0.0 + "," + 0.0 +
                "," + 0.0 + "," + SchoolID + ");" ;

        db.execSQL(str_insert_sql);
    }
}
