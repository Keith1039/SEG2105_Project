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
    String cap, oldCode;
    String time1, time2;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> ada;


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


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        second.setAdapter(adapter);

        ada = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sizes);
        capacity.setAdapter(ada);

        Intent intent = getIntent();
        oldCode = intent.getStringExtra("CCode");
        String oldName = intent.getStringExtra("CName");
        String prof = intent.getStringExtra("profName");

        Cursor cursor = db.getSpecificData(oldCode);

        if (cursor != null)
        {
            if(cursor.moveToFirst())
            {
                String cday1 = cursor.getString(3);
                String cday2 = cursor.getString(4);
                String ct1 = cursor.getString(5);
                String ct2 = cursor.getString(6);
                String cdesc = cursor.getString(7);
                String ccap = cursor.getString(8);


                firstDay.setText(cday1);
                secondDay.setText(cday2);
                CDesc.setText(cdesc);


                int sel = adapter.getPosition(ccap);
                capacity.setSelection(sel);

                int ft = ada.getPosition(ct1);
                dropdown.setSelection(ft);

                int st = ada.getPosition(ct2);
                second.setSelection(st);

            }
        }


        //sets the text when we enter the edit page
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

                        if(desc.equals("") || day1.equals("") || day2.equals("")){
                            Toast.makeText(InstructorEditCourseActivity.this, "Please enter a response for all available text boxes", Toast.LENGTH_SHORT).show();

                        }else {
                            db.updateCourse(oldCode, CCode, CName, prof, day1, day2, desc, Integer.parseInt(cap), time1, time2);
                            Toast.makeText(InstructorEditCourseActivity.this, "Successfully updated this course", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
        );


        leave.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        db.updateCourse(oldCode, oldCode, oldName, "", "", "", "", 0, "", "");
                        Toast.makeText(InstructorEditCourseActivity.this, "You are no longer the professor of this course", Toast.LENGTH_SHORT).show();
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

