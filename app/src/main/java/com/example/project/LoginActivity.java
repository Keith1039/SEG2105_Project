package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button admin_login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        admin_login_btn = (Button) findViewById(R.id.admin_login_btn);

        // user: admin
        // pass: admin123
        Administrator admin = new Administrator();

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
    }
}