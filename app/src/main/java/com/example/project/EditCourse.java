package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class EditCourse extends AppCompatActivity implements Serializable {

    Button done;
    EditText newCID, newCName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_course);

        done = (Button) findViewById(R.id.updateCourse);
        newCID = (EditText) findViewById(R.id.newCID);
        newCName = (EditText) findViewById(R.id.newCName);


        done.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view){
                        String newID = newCID.getText().toString();
                        String newName = newCName.getText().toString();

                        Log.d("NewID", newID);
                        Log.d("NewName", newName);

                        edit_course(newID, newName);

                    }
                }
        );
    }


    public void edit_course(String newID, String newName){
        Intent intent = getIntent();
        AdminActivity old = (AdminActivity) intent.getSerializableExtra("OldID");
        Log.d("Old ID", String.valueOf(old));
        finish(); 
    }






}
