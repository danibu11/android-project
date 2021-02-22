package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.DriverManager;
import java.sql.Connection;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



    }


    public void reg2(View view) {
        Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
        startActivity(intent);
    }
}
