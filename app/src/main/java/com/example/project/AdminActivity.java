package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    Button view_users;
    Button delete_user;

    Button view_courses;
    Button create_course;
    Button edit_course;
    Button delete_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the xml page to the corresponding activity
        // In this case, we are using activity_admin.xml
        setContentView(R.layout.activity_admin);

        view_users = (Button) findViewById(R.id.view_users);
        delete_user = (Button) findViewById(R.id.delete_user);

        view_courses = (Button) findViewById(R.id.view_courses);
        create_course = (Button) findViewById(R.id.create_course);
        edit_course = (Button) findViewById(R.id.edit_course);
        delete_course = (Button) findViewById(R.id.delete_course);

        // Since there is only 1 admin, we do not need to fetch user from database
        // When we implement Student and Instructor,
        // we will need to fetch the user from database using username
        Administrator admin = new Administrator();

        // Display list of users
        view_users.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Fetch all users from database and display them
                    }
                }
        );

        // Delete a user
        delete_user.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Prompt admin for username of a user to delete
                        // This can be done by opening a new xml file with
                        // setContentView(R.layout.delete_user)
                        // which hasn't been made yet
                        // admin.delete(username)
                    }
                }
        );

        // Display list of courses
        view_courses.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Fetch all courses from database and display them
                    }
                }
        );

        // Create a new course
        create_course.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // open prompt to create a new Course object and add to database
                        // prompt should ask admin for a course code and course name
                        // This should be done by opening a new xml file with
                        // setContentView(R.layout.create_course);
                        // which hasn't been made yet
                        // admin.createCourse(code, name)
                    }
                }
        );

        // Edit an existing course
        edit_course.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Open prompt to edit an existing course
                        // prompt should ask admin for the existing course's code,
                        // and the new code and name of the course
                        // This should be done by opening a new xml file with
                        // setContentView(R.layout.edit_course);
                        // which hasn't been made yet
                        // admin.editCourse(oldCode, newCode, newName);
                    }
                }
        );

        // Delete an existing course
        delete_course.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Open prompt to delete an existing course
                        // Prompt should ask admin for the existing course's code
                        // This should be done by opening a new xml file with
                        // setContentView(R.layout.delete_course);
                        // which hasn't been made yet
                        // admin.deleteCourse(code);
                    }
                }
        );
    }
}