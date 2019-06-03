package com.example.navifationtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class ProfessorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);
        Intent intent = getIntent();
        final String courseName = intent.getStringExtra("course");


        TextView courseTitle=(TextView)findViewById(R.id.course);
        courseTitle.setText(courseName);

    }
}
