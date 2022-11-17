package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;


public class InstructorEditCourseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button update, leave;
    EditText Cname, Ccode, CDesc, firstDay, secondDay;
    Spinner second, dropdown, capacity;
    TextView Professor;
    DBHandler db;
    String cap;
    String time1, time2;
    String[] items = new String[]{"8:00","9:00","10:00","11:00","12:00","1:00","2:00","3:00","4:00","5:00","6:00","7:00"};
    String[] sizes = new String[]{"50", "100", "150", "200", "250", "300"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the xml page to the corresponding activity
        // In this case, we are using activity_admin.xml
        setContentView(R.layout.edit_course_activity);
        update = (Button) findViewById(R.id.upDate);


        dropdown = findViewById(R.id.timesel);
        second = findViewById(R.id.timesel2);
        Cname = findViewById(R.id.courseName);
        Ccode = findViewById(R.id.Ccode);
        CDesc = findViewById(R.id.course_description);
        firstDay = findViewById(R.id.firstDay);
        secondDay = findViewById(R.id.secondDay);
        Professor = findViewById(R.id.profName);
        capacity = findViewById(R.id.spinner2);
        leave = findViewById(R.id.unassign);
        db = new DBHandler(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        second.setAdapter(adapter);

        ArrayAdapter<String> ada = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sizes);
        capacity.setAdapter(ada);

        Intent intent = getIntent();
        String oldCode = intent.getStringExtra("CCode");
        String oldName = intent.getStringExtra("CName");
        String prof = intent.getStringExtra("profName");



        Professor.setText(prof);
        Cname.setText(oldName);
        Ccode.setText(oldCode);


        capacity.setOnItemSelectedListener(this);
        dropdown.setOnItemSelectedListener(this);
        second.setOnItemSelectedListener(this);

        update.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        String CName = Cname.getText().toString();
                        String CCode = Ccode.getText().toString();
                        String day1 = firstDay.getText().toString();
                        String day2 = secondDay.getText().toString();
                        String desc = CDesc.getText().toString();

                        db.updateCourse(oldCode, CCode, CName, prof, day1, day2, desc, Integer.parseInt(cap), time1, time2);
                        finish();
                    }
                }
        );


        leave.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        db.updateCourse(oldCode, oldCode, oldName, "", "", "", "", 0, "", "");
                        finish();
                    }
                }
        );






    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.spinner2){
            cap = capacity.getSelectedItem().toString();
        }else if(adapterView.getId() == R.id.timesel){
            time1 = dropdown.getSelectedItem().toString();
        }else if(adapterView.getId() == R.id.timesel2){
            time2 = second.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

