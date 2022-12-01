package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CourseDetails  extends AppCompatActivity {
    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the xml page to the corresponding activity
        // In this case, we are using activity_admin.xml
        setContentView(R.layout.course_description);
        details = (TextView) findViewById(R.id.course_details);
        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        String name = intent.getStringExtra("name");
        String days= intent.getStringExtra("days");

        if(code == null && name==null && days == null){
            Toast.makeText(CourseDetails.this,"Error! Please fill out at list one criteria to search for", Toast.LENGTH_SHORT).show();
        }
        else {
            find_course(name,code,days);

        }



}
    public void find_course (String name,String code,String day){
        DBHandler dbHandler = new DBHandler(this);
        Cursor cursor = null;


        if(name.length()!=0) {
            cursor = dbHandler.findCoursebyName(name);
        }else if(code.length()!=0){
            cursor = dbHandler.findCourse(code);
        }else if (day.length()!=0) {
            cursor = dbHandler.find_course_by_day(day);
        }

        try{
            foundDisplay(cursor);
        }catch (Exception e){
            Toast.makeText(CourseDetails.this, "No courses with that information found", Toast.LENGTH_SHORT).show();
        }
    }
    private void foundDisplay(Cursor cursor){


        if (cursor.getCount() == 0) {
            Toast.makeText(CourseDetails.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }
        else {
            details.setText("");
            while (cursor.moveToNext()) {
                details.append("\nCOURSE CODE AND NAME:  "+cursor.getString(0)+":"+cursor.getString(1)+"\n"+
                                "\nINSTRUCTOR: "+cursor.getString(2)+"\n"+
                                "\nDESCRIPTION: "+cursor.getString(7)+"\n"+
                                "\nNBR OF STUDENT ENROLLED: "+cursor.getString(9)+"\n"+
                                "\nSCHEDULE -> DAY1: "+cursor.getString(3)+" : "+cursor.getString(5)+ " - DAY2: "+cursor.getString(4)+" : "+cursor.getString(6)+"\n"+
                                "\n***************************************************\n"
                        );

            }

            }
        }
    }
