package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {
    Button view_users;
    Button view_courses;
    Button create_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        view_users = (Button) findViewById(R.id.view_users);
        view_courses = (Button) findViewById(R.id.view_courses);
        create_course = (Button) findViewById(R.id.create_course);

        view_users.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // list all users and option to delete them from the database
                    }
                }
        );

        view_courses.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // list all courses and option to edit or delete them from the database
                    }
                }
        );

        create_course.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // open prompt to create a new Course object and add to database
                        // prompt should ask admin for a course code and course name
                    }
                }
        );
    }
}