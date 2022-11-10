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
    Button log_in_btn,sign_up_btn;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHandler = new DBHandler(this);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        log_in_btn = (Button) findViewById(R.id.log_in_btn);
        sign_up_btn = (Button) findViewById(R.id.sign_up_btn);
        

        // Later we will add a universal login button to fetch a user from database using username
        // Then use user.login(user, pass) to validate user
        // If login is successful, start the corresponding activity
        // For now, we only have AdminActivity

        log_in_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //take the username and password from the UI
                        String userName = username.getText().toString();
                        String passWord = password.getText().toString();

                        User user = dbHandler.findUser(userName);
                        //check in the database to see if the user exist

                        if ( user!=null  && user.password.equals(passWord)){
                            //if the user exist check to see if it's an admin and go to the admin page
                            if (userName.equals("admin")) {
                                // Starts the AdminActivity
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                            }
                            //See if the username format match the student username format which is "adbcd123" and go to student page
                            else if ((userName.matches("[a-z0-9]+"))==true ){
                                Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                                intent.putExtra("usernameEditText", userName);
                                startActivity(intent);

                            }
                            else if ((userName.matches("[a-zA-Z]+"))== true){
                                //See if the username format match the instructor username format which is "adbcd" and go to instructor  page
                                Intent intent = new Intent(LoginActivity.this, InstructorActivity.class);
                                intent.putExtra("usernameEditText", userName);
                                startActivity(intent);
                            }
                        }
                        else {
                            //the user is not in the database prompt the correct message
                            Toast.makeText(LoginActivity.this,"Error check the username and the password", Toast.LENGTH_SHORT).show();

                        }

                    }
                }
        );

        sign_up_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Starts the SignupActivity
                        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                        startActivity(intent);

                    }

                }
        );
    }
}