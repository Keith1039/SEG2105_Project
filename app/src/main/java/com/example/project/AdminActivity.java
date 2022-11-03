package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements Serializable {

    EditText data_id, data_name;
    ListView data_ListView ;

    Button view_users;
    Button delete_user;

    Button view_courses;
    Button create_course;
    Button edit_course;
    Button delete_course;

    ArrayList<String> data_List;
    ArrayAdapter adapter;
    DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the xml page to the corresponding activity
        // In this case, we are using activity_admin.xml
        setContentView(R.layout.activity_admin);
        data_List = new ArrayList<String>();

        //input data info
        data_id = (EditText) findViewById(R.id.data_id);
        data_name = (EditText) findViewById(R.id.data_name);

        //Button
        view_users = (Button) findViewById(R.id.view_users);
        delete_user = (Button) findViewById(R.id.delete_user);

        view_courses = (Button) findViewById(R.id.view_courses);
        create_course = (Button) findViewById(R.id.create_course);
        edit_course = (Button) findViewById(R.id.edit_course);
        delete_course = (Button) findViewById(R.id.delete_course);

        // Since there is only 1 admin, we do not need to fetch user from database
        // When we implement Student and Instructor,
        // we will need to fetch the user from database using username
        dbHandler = new DBHandler(this);

        data_ListView = findViewById(R.id.data_ListView);
        // Display list of users
        view_users.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Fetch all users from database and display them
                        viewUsers();
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
                        boolean result = dbHandler.deleteUser(data_id.getText().toString());
                        //check if it was delete or if it was not found then print the according message
                        if (result){
                            Toast.makeText(AdminActivity.this,"User deleted", Toast.LENGTH_SHORT).show();
                            data_id.setText("");
                            data_name.setText("");
                            viewUsers();

                        }
                        else  Toast.makeText(AdminActivity.this,"No match found on the list of users", Toast.LENGTH_SHORT).show();

                    }
                }
        );

        // Display list of courses
        view_courses.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Fetch all courses from database and display them
                        viewCourses();

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

                        String courseCode = data_id.getText().toString();
                        String courseName = data_name.getText().toString();
                        Course course = new Course(courseCode,courseName);
                        dbHandler.addCourse(course);

                        data_id.setText("");
                        data_name.setText("");

                        viewCourses();

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

                        String oldCID = data_id.getText().toString();

                        Intent intent = new Intent(AdminActivity.this, EditCourse.class);
                        intent.putExtra("OldID", oldCID);
                        startActivity(intent);

                        data_id.setText("");
                        data_name.setText("");
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

                        //delete product into database using deleteCourse()
                        boolean result = dbHandler.deleteCourse(data_id.getText().toString());


                        //check if it was delete or if it was not found then print the according message
                        if (result){
                            Toast.makeText(AdminActivity.this,"Course deleted", Toast.LENGTH_SHORT).show();
                            data_id.setText("");
                            data_name.setText("");
                            viewCourses();

                        }
                        else  Toast.makeText(AdminActivity.this,"No match found on the list of courses", Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }


    private void viewCourses() {

        data_List.clear();
        Cursor cursor = dbHandler.getCourseData();
        if (cursor.getCount() == 0) {
            Toast.makeText(AdminActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                data_List.add(cursor.getString(0) + ": " +cursor.getString(1));
            }
        }
        adapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, data_List);
        data_ListView.setAdapter(adapter);
    }


    private void viewUsers() {

        data_List.clear();
        Cursor cursor = dbHandler.getUserData();
        if (cursor.getCount() == 0) {
            Toast.makeText(AdminActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                data_List.add(cursor.getString(0) + ": " +cursor.getString(1));
            }
        }
        adapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, data_List);
        data_ListView.setAdapter(adapter);
    }

    public DBHandler getDBHandler() {
        return this.dbHandler;
    }
}