package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    TextView emailField, passwordField;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.loginemail_editText);
        passwordField = findViewById(R.id.loginpassword_editText);
        loginButton = findViewById(R.id.loginbutton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                String credentials = "Email: "+email+", Password: " + password;
                boolean emailEmpty, passwordEmpty;
                //check not empty
                if (email.trim().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please input an Email Address", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.trim().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please input a password", Toast.LENGTH_LONG).show();
                    return;
                }
                // TODO: credentials validation when db is ready
                // validate with db:
                // check db for a record with this email
                // IF no record, add these email and password that we just got and go to main page
                // ELSE (found record) check if the entered password is identical to the one in our db for this email
                // TRUE go to login page --> goToMainPage();
                // FALSE show wrong password alert --> Toast.makeText(LoginActivity.this, "Wrong password! please try again", Toast.LENGTH_LONG).show();

                // TODO delete these next 2 lines when db validation is done
                Toast.makeText(LoginActivity.this, credentials, Toast.LENGTH_SHORT).show();
                goToMainPage();
            }
        });
    }
    private void goToMainPage() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}