package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText emailField, passwordField;
    Button loginButton, registerButton;
    static String email, userName;//for moving them between intents


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailField = findViewById(R.id.loginemail_editText);
        passwordField = findViewById(R.id.loginpassword_editText);
        loginButton = findViewById(R.id.loginbutton);
        registerButton = findViewById(R.id.regbutton);

/////
 //       this.deleteDatabase("buchnitzDB");
//        ArrayList<User> allUsers = DBHelper.getAllUsersFromDB(this);
//        for (int i = 0; i < allUsers.size(); i++) {
//            Log.d("FOUND USER", allUsers.get(i).toString());
//        }
        /////
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                String credentials = "Email: "+email+", Password: " + password;
                String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                boolean emailEmpty, passwordEmpty;
                //check not empty
                if (email.trim().length() == 0)
                {
                    Toast.makeText(LoginActivity.this, "Please input an Email Address ", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    if (email.matches(emailPattern))
                        Toast.makeText(getApplicationContext(), "Valid email address", Toast.LENGTH_SHORT).show();
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                        return ;
                    }
                }
                if (password.trim().length() == 0)
                {
                    Toast.makeText(LoginActivity.this, "Please input a password", Toast.LENGTH_LONG).show();
                    return;
                }

                // validate with db:
                // check db for a record with this email
                // IF no record, add these email and password that we just got and go to main page
                // ELSE (found record) check if the entered password is identical to the one in our db for this email
                // TRUE go to login page --> goToMainPage();
                // FALSE show wrong password alert --> Toast.makeText(LoginActivity.this, "Wrong password! please try again", Toast.LENGTH_LONG).show();

                if (validateCredentials(email, password)) {
                    Toast.makeText(LoginActivity.this, credentials, Toast.LENGTH_SHORT).show();
                    goToMainPage();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials! please sign up or try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validateCredentials(String email, String password) {
        boolean isValidCredentials = false;
        ArrayList<User> allUsers = DBHelper.getAllUsersFromDB(this);
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getEmail().compareTo(email) == 0) {
                if (allUsers.get(i).getPassword().compareTo(password) == 0) {
                    isValidCredentials = true;
                    //save the full name of the user name for later use.
                    userName = (allUsers.get(i).getF_name()+" "+allUsers.get(i).getL_name());

                }
            }
        }
        return isValidCredentials;
    }


    private void goToMainPage() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("GET_EMAIL", email);
        intent.putExtra("GET_FULL_NAME", userName);
        startActivity(intent);
    }

    public void registerFunc(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);

    }
}