package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the xml page to the corresponding activity
        // In this case, we are using activity_admin.xml
        setContentView(R.layout.student_page);
        Intent intent = getIntent();
        if (intent != null){
            String str = "";
            if (intent.hasExtra("usernameEditText")){
                str = intent.getStringExtra("usernameEditText");
            }
            TextView textView = (TextView) findViewById(R.id.student_welcome);
            textView.setText("welcome student : "+str);
        }
    }
}
