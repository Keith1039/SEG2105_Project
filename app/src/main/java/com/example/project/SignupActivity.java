package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignupActivity extends AppCompatActivity {

    EditText username_signup;
    EditText password_signup;
    Button create_account;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the xml page to the corresponding activity
        // In this case, we are using signup_page.xml
        setContentView(R.layout.signup_page);
        dbHandler = new DBHandler(this);
        username_signup = (EditText) findViewById(R.id.username_signup);
        password_signup = (EditText) findViewById(R.id.password_signup);
        create_account = (Button) findViewById(R.id.create_account);

        create_account.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //take the username and password from the UI
                        String userName = username_signup.getText().toString();
                        String passWord = password_signup.getText().toString();
                        User user = dbHandler.findUser(userName);
                        //check if user is already on the database or not
                        if (!(user == null)){
                            Toast.makeText(SignupActivity.this,"Username already on the database! Try another username", Toast.LENGTH_SHORT).show();
                        }

                        //check if username format respect instructor's username format or student's username format
                        else if ((userName.matches("[a-z0-9]+"))==true || (userName.matches("[a-zA-Z]+"))==true ){
                            dbHandler.addUsers(new User(userName,passWord));
                            Toast.makeText(SignupActivity.this,"Account created.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(SignupActivity.this,"wrong username pattern! Try again.", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

        );


    }
}
