package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class EditCourse extends AdminActivity {

    Button update;
    EditText newCID, newCName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_course);

        update = (Button) findViewById(R.id.updateCourse);
        newCID = (EditText) findViewById(R.id.newCID);
        newCName = (EditText) findViewById(R.id.newCName);

        update.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view){
                        Intent intent = getIntent();
                        String oldID = intent.getStringExtra("OldID");

                        String newID = newCID.getText().toString();
                        String newName = newCName.getText().toString();

                        edit_course(oldID, newID, newName);

                        finish();


                    }
                }
        );
    }

    public void edit_course(String oldID, String newID, String newName){
        DBHandler dbHandler = super.getDBHandler();
        Cursor cursor = dbHandler.getCourseData();

        while(cursor.moveToNext()){
            Log.d("DB", cursor.getString(0));
        }
        dbHandler.editCourse(oldID, newID, newName);

        Log.d("OldID", oldID);
        Log.d("NewID", newID);
        Log.d("NewName", newName);



    }

}
