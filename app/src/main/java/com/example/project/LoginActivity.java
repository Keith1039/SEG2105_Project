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
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login_btn = (Button) findViewById(R.id.login_btn);

        Administrator admin = new Administrator("admin", "admin123");

        login_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (admin.login(username.getText().toString(), password.getText().toString())) {
                            setContentView(R.layout.activity_admin);
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(intent);
                        }

                    }
                }
        );
    }
}