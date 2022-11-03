package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Create_Activity {


    public class LoginActivity extends AppCompatActivity {
        EditText username;
        EditText password;
        Button admin_login_btn;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.create_account);

            username = (EditText) findViewById(R.id.username_Create);
            password = (EditText) findViewById(R.id.password_Create);
            admin_login_btn = (Button) findViewById(R.id.account_create_btn);

            // user: admin
            // pass: admin123
            DBHandler db = new DBHandler(this);
            User user = new User();

            // Later we will add a universal login button to fetch a user from database using username
            // Then use user.login(user, pass) to validate user
            // If login is successful, start the corresponding activity
            // For now, we only have AdminActivity
            admin_login_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            user.username = username.getText().toString();
                            user.password = password.getText().toString();
                            db.addUsers(user);

                        }
                    }
            );
        }
    }
}
