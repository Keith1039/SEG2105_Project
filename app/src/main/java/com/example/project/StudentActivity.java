package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
                        String code = course_code.getText().toString();

                        //takes the course code and username and sends it to the enroll() method
                        enroll(code, userName);
                    }
                }
        );

        unenroll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String code = course_code.getText().toString();
                        unenroll(code, userName);

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
    public void enroll(String CCode, String username){
        dbHandler = new DBHandler(this);
        //takes those parameters and sends them to DBHandler to get processed
        //now check if successful or not
        Cursor cursor = dbHandler.getSpecificUser(username);

        int index = 0;

        //looking for first empty position
        for(int i = 2; i<=6; i++){
            if(cursor != null){
                if(cursor.moveToFirst()){
                    if(cursor.isNull(i) || cursor.getString(i).equals("")){
                        index = i;
                        break;
                    }
                }
            }
        }

        //this will set the course to the course position we want in the db
        switch(index){
            case 0:
                Toast.makeText(StudentActivity.this, "Course list is full", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                dbHandler.enroll(CCode, username, "course1");
                break;
            case 3:
                dbHandler.enroll(CCode, username, "course2");
                break;
            case 4:
                dbHandler.enroll(CCode, username, "course3");
                break;
            case 5:
                dbHandler.enroll(CCode, username, "course4");
                break;
            case 6:
                dbHandler.enroll(CCode, username, "course5");
                break;
        }

        Log.d("First", cursor.getString(2));
        if(cursor.getString(3) != null) {
            Log.d("Second", cursor.getString(3));
        }
    }

    public boolean checkNoConflicts(String code, String username){
        dbHandler = new DBHandler(this);

        //make this default false
        boolean result = true;

        Cursor courses = dbHandler.getSpecificData(code);
        Cursor user = dbHandler.getSpecificUser(username);
        //index 5 and 6 have the course times in the courses cursor
        Cursor cursor;

        String firstTime = null;
        String secondTime = null;

        if(courses != null) {
            if (courses.moveToFirst()) {
                firstTime = courses.getString(5);
                secondTime = courses.getString(6);
            }
        }


        return result;

    }


    //unenroll(CCode)
    //Takes the course code and looks for it in the user's list
    //if it exists, remove it from the user's list
    //otherwise return an error
    //COMPLETE
    public void unenroll(String CCode, String username){
        dbHandler = new DBHandler(this);
        //takes those parameters and sends them to DBHandler to get processed
        //now check if successful or not
        Cursor cursor = dbHandler.getSpecificUser(username);

        int index = 0;

        for(int i = 2; i<=6; i++){
            if(cursor != null){
                if(cursor.moveToFirst()){
                    if(cursor.getString(i).equals(CCode)){
                        index = i;
                        break;
                    }
                }
            }
        }

        switch(index){
            case 0:
                Toast.makeText(StudentActivity.this, "Course list is full", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                dbHandler.deleteClass(username, "course1");
                break;
            case 3:
                dbHandler.deleteClass(username, "course2");
                break;
            case 4:
                dbHandler.deleteClass(username, "course3");
                break;
            case 5:
                dbHandler.deleteClass(username, "course4");
                break;
            case 6:
                dbHandler.deleteClass(username, "course5");
                break;
        }
    }










    //viewMyCourse()
    //Should check all the course columns and search for all the courses in the course table
    //Then should return all resulting courses


}
