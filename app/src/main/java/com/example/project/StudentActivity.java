package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    Button search;
    Button enroll, unenroll;
    Button display;

    EditText course_code, course_name;
    ListView course_ListView ;

    ArrayList<String> course_List;
    ArrayAdapter adapter;
    DBHandler dbHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the xml page to the corresponding activity
        // In this case, we are using activity_admin.xml
        setContentView(R.layout.student_page);
        course_List = new ArrayList<String>();
        Intent intent = getIntent();
        if (intent != null){
            String str = "";
            if (intent.hasExtra("usernameEditText")){
                str = intent.getStringExtra("usernameEditText");
            }
        }



        course_code = (EditText) findViewById(R.id.courseCode);
        course_name = (EditText) findViewById(R.id.courseName);
        course_ListView = (ListView) findViewById(R.id.view_list_courses);
        search = (Button) findViewById(R.id.my_courses);
        enroll = (Button) findViewById(R.id.enroll);
        unenroll = (Button) findViewById(R.id.unenroll);
        display = (Button) findViewById(R.id.all_courses);

        dbHandler = new DBHandler(this);
        String userName = intent.getStringExtra("usernameEditText");



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


        enroll.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        String courseCode = course_code.getText().toString();
                        boolean success = enroll(courseCode, userName);

                        if(success){
                            Toast.makeText(StudentActivity.this, "You have successfully enrolled", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(StudentActivity.this, "An error occurred, you may be enrolled in too many classes", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }

    private void viewCourses() {
        course_List.clear();
        Cursor cursor = dbHandler.getCourseData();
        if (cursor.getCount() == 0) {
            Toast.makeText(StudentActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                //Format should be Code:Name - "Available Seats":Available Seats
                course_List.add(cursor.getString(0) + ": " +cursor.getString(1));
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,course_List);
        course_ListView.setAdapter(adapter);
    }

    //enroll(CCode, username)
    //Takes the course code, looks for it in the courses table
    //If it exists, check that it's time does not conflict with other courses already in the list
    //otherwise return an error
    //Then add it to the course list of the user table (first available course slot)
    //If too many courses are already added (5 course slots are already filled) return an error
    private boolean enroll(String CCode, String username){
        dbHandler = new DBHandler(this);
        return dbHandler.enroll(CCode, username);
    }




    //unenroll(CCode)
    //Takes the course code and looks for it in the user's list
    //if it exists, remove it from the user's list
    //otherwise return an error

    //viewMyCourse()
    //Should check all the course columns and search for all the courses in the course table
    //Then should return all resulting courses


}
