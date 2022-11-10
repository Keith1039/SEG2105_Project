package com.example.project;

import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class InstructorActivity extends AppCompatActivity implements Serializable {

    EditText course_code, course_name;
    ListView course_ListView ;

    Button search;
    Button edit;
    Button display;

    ArrayList<String> course_List;
    ArrayAdapter adapter;
    DBHandler dbHandler;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the xml page to the corresponding activity
        // In this case, we are using activity_admin.xml
        setContentView(R.layout.instructor_page);

        //this code will simply get the username typed in the login page and display it here so we know which user is currently on this page
        Intent intent = getIntent();
        if (intent != null){
            String str = "";
            if (intent.hasExtra("usernameEditText")){
                str = intent.getStringExtra("usernameEditText");
            }
            TextView textView = (TextView) findViewById(R.id.instructor_welcome);
            textView.setText("Welcome instructor : "+str);
        }

        //linked button to the button in instructor layout
        course_code = (EditText) findViewById(R.id.courseCode);
        course_name = (EditText) findViewById(R.id.courseName);
        course_ListView = (ListView) findViewById(R.id.view_list_courses);
        search = (Button) findViewById(R.id.search);
        edit = (Button) findViewById(R.id.edit);
        display = (Button) findViewById(R.id.display_course);

        //connect to database
        dbHandler = new DBHandler(this);

        //find course by course code and course name
        search.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //take the course code and the course name
                        String courseCode = course_code.getText().toString();
                        String courseName = course_name.getText().toString();








                    }
                }
        );

        edit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Starts the EditActivity
                        Intent intent = new Intent(InstructorActivity.this, InstructorEditCourseActivity.class);
                        startActivity(intent);
                    }
                }
        );

        // Display list of courses
        display.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Fetch all courses from database and display them
                        viewCourses();

                    }
                }
        );






    }

    private void viewCourses() {

        course_List.clear();
        Cursor cursor = dbHandler.getCourseData();
        if (cursor.getCount() == 0) {
            Toast.makeText(InstructorActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                course_List.add(cursor.getString(0) + ": " +cursor.getString(1));
            }
        }
        adapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1,course_List);
        course_ListView.setAdapter(adapter);
    }

}
