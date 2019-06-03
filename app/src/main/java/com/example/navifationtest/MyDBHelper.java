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
    static final String CREATE_TABLE_RATE = "create table if not exists Rate(RateID int primary key not null,RateKnowlCap real not null,RateEnjoy real not null,RateHomeword real not null,RateInteract real not null,RateScore real not null,RateComment text not null,CourseID int not null,StudentID int not null);";

    static final String CREATE_TABLE_COURSE = "create table if not exists Course(CourseID int primary key not null,CourseName char(50) not null,RateKnowlCap real not null,RateEnjoy real not null,RateHomeword real not null,RateInteract real not null,RateScore real not null,SchoolID);";


    public MyDBHelper(Context context){
        super(context, DB_NAME,null,DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(CREATE_TABLE_TEST);
        db.execSQL(CREATE_TABLE_COURSE);
        db.execSQL(CREATE_TABLE_RATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
