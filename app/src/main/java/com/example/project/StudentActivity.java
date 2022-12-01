package com.example.project;

import android.annotation.SuppressLint;
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
import java.util.List;

public class StudentActivity extends AppCompatActivity {
    Button search,search_course,myCourse;
    Button enroll, unenroll;
    Button display;

    EditText course_code, course_name,day;
    ListView course_ListView ;

    ArrayList<String> course_List;
    ArrayAdapter adapter;
    DBHandler dbHandler;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the xml page to the corresponding activity
        // In this case, we are using activity_admin.xml
        setContentView(R.layout.student_page);
        course_List = new ArrayList<String>();
        Intent intent = getIntent();
        String str = "";
        if (intent != null){

            if (intent.hasExtra("usernameEditText")){
                str = intent.getStringExtra("usernameEditText");
            }
            TextView textView = (TextView) findViewById(R.id.student_welcome);
            textView.setText("Current Student : "+str);
        }



        course_code = (EditText) findViewById(R.id.courseCode);
        course_name = (EditText) findViewById(R.id.courseName);
        day= (EditText) findViewById(R.id.Day) ;
        course_ListView = (ListView) findViewById(R.id.view_list_courses);
        enroll = (Button) findViewById(R.id.enroll);
        unenroll = (Button) findViewById(R.id.unenroll);
        display = (Button) findViewById(R.id.all_courses);
        search_course= (Button) findViewById(R.id.Find);
        myCourse =(Button) findViewById(R.id.my_courses);

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
        //Search a course either bu code name or day(in this case we will display the courses offered on that given day
        search_course.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(StudentActivity.this, CourseDetails.class);
                        String code = course_code.getText().toString();
                        String name = course_name.getText().toString();
                        String days = day.getText().toString();
                        intent.putExtra("code", code);
                        intent.putExtra("name", name);
                        intent.putExtra("days", days);
                        if (code.length()==0 && name.length()==0 && days.length()==0){
                            Toast.makeText(StudentActivity.this, "Please give at least one criteria to search for", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            startActivity(intent);
                        }


                        }
                    }
        );


        String finalStr = str;
        myCourse.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewMyCourse(finalStr);

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
        if (dbHandler.is_enrolled(username, CCode)){
            Toast.makeText(StudentActivity.this, "Already enrolled", Toast.LENGTH_SHORT).show();

        }
        else {
            int index = 0;

            //looking for first empty position
            for (int i = 2; i <= 6; i++) {
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        if (cursor.isNull(i) || cursor.getString(i).equals("")) {
                            index = i;
                            break;
                        }
                    }
                }
            }

            //this will set the course to the course position we want in the db
            switch (index) {
                case 0:
                    Toast.makeText(StudentActivity.this, "Course list is full", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    if (checkNoConflicts(CCode, username)) {
                        dbHandler.enroll(CCode, username, "course1");
                        Toast.makeText(StudentActivity.this, "Successfully enrolled", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StudentActivity.this, "You have a time conflict", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    if (checkNoConflicts(CCode, username)) {
                        dbHandler.enroll(CCode, username, "course2");
                        Toast.makeText(StudentActivity.this, "Successfully enrolled", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StudentActivity.this, "You have a time conflict", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 4:
                    if (checkNoConflicts(CCode, username)) {
                        dbHandler.enroll(CCode, username, "course3");
                        Toast.makeText(StudentActivity.this, "Successfully enrolled", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StudentActivity.this, "You have a time conflict", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 5:
                    if (checkNoConflicts(CCode, username)) {
                        dbHandler.enroll(CCode, username, "course4");
                        Toast.makeText(StudentActivity.this, "Successfully enrolled", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StudentActivity.this, "You have a time conflict", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 6:
                    if (checkNoConflicts(CCode, username)) {
                        dbHandler.enroll(CCode, username, "course5");
                        Toast.makeText(StudentActivity.this, "Successfully enrolled", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StudentActivity.this, "You have a time conflict", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }


            if (cursor.getString(3) != null) {
                Log.d("Second", cursor.getString(3));
            }
        }
    }

    public boolean checkNoConflicts(String code, String username){
        dbHandler = new DBHandler(this);
        //get first and second times of course I want
        //loop through student's courses and get those times
        //compare the current used up times to the new times

        boolean out = true;
        Cursor courses = dbHandler.getSpecificData(code);
        String day1 = "";
        String day2 = "";
        String time1 = "";
        String time2 = "";
        //Stores the values for the course we want to add
        if(courses != null){
            if(courses.moveToFirst()){
                day1 = courses.getString(3);
                day2 = courses.getString(4);
                time1 = courses.getString(5);
                time2 = courses.getString(6);
            }
        }

        Cursor user = dbHandler.getSpecificUser(username);
        //first loop will check first day and time
        for(int i = 2; i<=6; i++){
            if(user != null){
                if(user.moveToFirst()){
                    //contains the course code at index i
                    String temp = user.getString(i);

                    Cursor cursor1 = dbHandler.getSpecificData(temp);

                    //contain the values of time and day 1 for the course at index i
                    if(cursor1 != null) {
                        if (cursor1.moveToFirst()) {
                            String d1 = cursor1.getString(3);
                            String t1 = cursor1.getString(5);


                            if(d1 != null && t1 != null) {
                                if (day1.equals(d1) && time1.equals(t1)) {
                                    return false;
                                }
                            }


                        }
                    }



                }
            }
        }

        //this for loop will check the second day and time
        for(int i = 2; i<=6; i++){
            if(user != null){
                if(user.moveToFirst()){
                    //contains the course code at index i
                    String temp = user.getString(i);
                    Cursor cursor1 = dbHandler.getSpecificData(temp);


                    if(cursor1 != null) {
                        if (cursor1.moveToFirst()) {
                            //contain the values of time and day 2 for the course at index i
                            String d2 = cursor1.getString(4);
                            String t2 = cursor1.getString(6);


                            if (d2 != null && t2 != null) {
                                if (!day2.equals(d2) && !time2.equals(t2)) {
                                    return false;
                                }
                            }


                        }
                    }



                }
            }
        }

        return out;

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
        if (dbHandler.is_enrolled(username, CCode)) {
            int index = 0;

            for (int i = 2; i <= 6; i++) {
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        if (cursor.getString(i).equals(CCode)) {
                            index = i;
                            break;
                        }
                    }
                }
            }

            switch (index) {
                case 0:
                    Toast.makeText(StudentActivity.this, "Course list is full", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    dbHandler.deleteClass(username, "course1", CCode);
                    break;
                case 3:
                    dbHandler.deleteClass(username, "course2", CCode);
                    break;
                case 4:
                    dbHandler.deleteClass(username, "course3", CCode);
                    break;
                case 5:
                    dbHandler.deleteClass(username, "course4", CCode);
                    break;
                case 6:
                    dbHandler.deleteClass(username, "course5", CCode);
                    break;
            }
        }

        else {
            Toast.makeText(StudentActivity.this, "Not enrolled in any course", Toast.LENGTH_SHORT).show();
        }
    }










    //viewMyCourse()
    //Should check all the course columns and search for all the courses in the course table
    //Then should return all resulting courses
    public void viewMyCourse(String userid){
        course_List.clear();
        Cursor cursor = dbHandler.get_my_course(userid);
        if (cursor.getCount() == 0) {
            Toast.makeText(StudentActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                //Format should be Code:Name - "Available Seats":Available Seats
                course_List.add(cursor.getString(0) + "\n " +cursor.getString(1)+"\n " +cursor.getString(2)+
                        "\n " +cursor.getString(3)+"\n " +cursor.getString(4));
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,course_List);
        course_ListView.setAdapter(adapter);
    }



    public void find_course (String name,String code,String day){
        DBHandler dbHandler = new DBHandler(this);
        Cursor cursor = null;


        if(name!=null) {
            cursor = dbHandler.findCoursebyName(name);
        }else if(code!=null){
            cursor = dbHandler.findCourse(code);
        }else if (day!=null){
            cursor = dbHandler.find_course_by_day(day);
        }
        try{
            foundDisplay(cursor);
        }catch (Exception e){
            Toast.makeText(StudentActivity.this, "No courses with that name found", Toast.LENGTH_SHORT).show();
        }
    }
    private void foundDisplay(Cursor cursor){

        course_List.clear();
        if (cursor.getCount() == 0) {
            Toast.makeText(StudentActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }
        else {

            course_List.add("CODE : NAME | INSTRUCTOR | DESCRIPTION | CURRENT CAPACITY | DAY1 : TIME | DAY2 : TIME ");
            while (cursor.moveToNext()) {
                course_List.add(cursor.getString(0) + " : " + cursor.getString(1) + " | " + cursor.getString(2) + " | " + cursor.getString(7) + " | " + cursor.getString(9) + " | " + cursor.getString(3) + ": " + cursor.getString(5) + " | " + cursor.getString(4) + ": " + cursor.getString(6));
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, course_List);
        course_ListView.setAdapter(adapter);
    }


}
