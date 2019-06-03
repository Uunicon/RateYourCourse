package com.example.navifationtest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //----------学校-----------
        final EditText editTextSchool = findViewById(R.id.addschoolname);

        //----------课程-----------
        final EditText editTextCourse = findViewById(R.id.addcoursename);

        //----------老师-----------
        EditText editTextTeacher = findViewById(R.id.addteachername);
        String addteachername=editTextSchool.getText().toString();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String addschoolname=editTextSchool.getText().toString();
                final String addcoursename=editTextSchool.getText().toString();
                if(addschoolname.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "学校不能为空", Toast.LENGTH_SHORT).show();
                }
                if(addcoursename.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "课程不能为空", Toast.LENGTH_SHORT).show();
                }
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
