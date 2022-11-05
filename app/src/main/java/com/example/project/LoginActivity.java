package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button admin_login_btn;
    Button student_log;
    Button instructor_log, create_student, create_instructor;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHandler = new DBHandler(this);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        admin_login_btn = (Button) findViewById(R.id.admin_login_btn);
        student_log = (Button) findViewById(R.id.student_log);
        instructor_log = (Button) findViewById(R.id.instructor_log);
        create_instructor = (Button) findViewById(R.id.create_instructor);
        create_student = (Button) findViewById(R.id.create_student);




        // user: admin
        // pass: admin123
        Administrator admin = new Administrator();
        Student student = new Student();
        Instructor instructor = new Instructor();

        create_instructor.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!username.getText().toString().equals("")) {
                            if (!password.getText().toString().equals("")) {

                                instructor.username = username.getText().toString();
                                instructor.password = password.getText().toString();

                                dbHandler.addUsers(instructor);

                                Toast.makeText(LoginActivity.this,"Instructor Created", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }
        );

        create_student.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Validate admin login
                        // There is only 1 admin so no need to communicate with database
                        if (!username.getText().toString().equals("")) {
                            if (!password.getText().toString().equals("")) {

                                student.username = username.getText().toString();
                                student.password = password.getText().toString();

                                dbHandler.addUsers(student);

                                Toast.makeText(LoginActivity.this,"Student Created", Toast.LENGTH_SHORT).show();

                            }

                        }

                    }
                }
        );

        

        // Later we will add a universal login button to fetch a user from database using username
        // Then use user.login(user, pass) to validate user
        // If login is successful, start the corresponding activity
        // For now, we only have AdminActivity
        admin_login_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Validate admin login
                        // There is only 1 admin so no need to communicate with database
                        if (admin.login(username.getText().toString(), password.getText().toString())) {

                            // Starts the AdminActivity
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(intent);
                        }

                    }
                }
        );

        instructor_log.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (instructor.login(username.getText().toString(), password.getText().toString())) {

                            // Starts the AdminActivity
                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                            startActivity(intent);
                        }

                    }
                }
        );

        student_log.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Validate admin login
                        // There is only 1 admin so no need to communicate with database
                        if (student.login(username.getText().toString(), password.getText().toString())) {

                            // Starts the AdminActivity
                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                            startActivity(intent);
                        }

                    }
                }
        );
    }
}