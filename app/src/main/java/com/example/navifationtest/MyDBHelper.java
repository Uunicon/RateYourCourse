package com.example.navifationtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    final static String DB_NAME = "RateYourCourse.db";
    final  static int DB_VERSION = 1;

    private Context mContext;
    static final String TABLE_NAME = "Test";
    static final String CREATE_TABLE_TEST = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + "(" +
            " TestID INTEGER PRIMARY KEY AUTOINCREMENT," +
            " TestName TEXT NOT NULL" +
            " )";
    //评价表
    static final String CREATE_TABLE_RATE = "create table if not exists Rate(RateID int primary key not null,RateKnowlCap real not null,RateEnjoy real not null,RateHomework real not null,RateInteract real not null,RateScore real not null,RateComment text not null,CourseID int not null,StudentID int not null);";
    //课程表
    static final String CREATE_TABLE_COURSE = "create table if not exists Course(CourseID int primary key not null,CourseName char(50) not null,RateKnowlCap real not null,RateEnjoy real not null,RateHomework real not null,RateInteract real not null,RateScore real not null,SchoolID int not null);";
    //学校表
    static  final String CREATE_TABLE_SCHOOL = "create table if not exists School(SchoolID int primary key not null,SchoolName char(50) not null);";
    //教师表
    static final String CREATE_TABLE_TEACHER = "create table if not exists Teacher(TeacherID int primary key not null,TeacherName char(50) not null,JobTitle char(50) not null,SchoolID int not null,CollegeID int not null);";
    //讲授表
    static final String CREATE_TABLE_TEACH = "create table if not exists Teach(TeacherID int not null, CourseID int not null);";
    //学院表
    static final String CREATE_TABLE_COLLEGE = "create table if not exists College(CollegeID int primary key not null, CollegeName char(50) not null);";
    //学生表
    static final String CREATE_TABLE_STUDENT = "create table if not exists Student(StudentID int primary key not null, StudentName char(50) not null, StudentPWD char(20) not null, SchoolID int not null, CollegeID int not null);";

    public MyDBHelper(Context context){
        super(context, DB_NAME,null,DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(CREATE_TABLE_TEST);
        db.execSQL(CREATE_TABLE_COURSE);
        db.execSQL(CREATE_TABLE_RATE);
        db.execSQL(CREATE_TABLE_SCHOOL);
        db.execSQL(CREATE_TABLE_TEACHER);
        db.execSQL(CREATE_TABLE_TEACH);
        db.execSQL(CREATE_TABLE_COLLEGE);
        db.execSQL(CREATE_TABLE_STUDENT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
