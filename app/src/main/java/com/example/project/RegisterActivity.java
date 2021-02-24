package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.DriverManager;
import java.sql.Connection;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    EditText etreg,etLN,etFN,etLang,etAge,etPass,etEmail;
    Button bt;
    static int idForDB=0;
    String FirstName, LastName, Region, Lang, Pass, Email;
    int age;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail=findViewById(R.id.mailET);
        etPass=findViewById(R.id.passEdit);
        etLang=findViewById(R.id.languegeEdit);
        etreg=findViewById(R.id.regionEdit);
        etAge=findViewById(R.id.ageEdit);
        etFN=findViewById(R.id.firstNameEdit);
        etLN=findViewById(R.id.lastNameEdit);

        bt=findViewById(R.id.button);

        FirstName=regFName();
        LastName=regLName();
        age=regAge();
        Region=regRegion();
        Lang=regLang();
        Pass=regPass();
        Email=regEmail();





    }
    public String regFName(){

        return etFN.getText().toString().trim();
    }
    public String regLName(){

        return etLN.getText().toString().trim();
    }
    public int regAge(){
        try
        {
            return Integer.parseInt(etAge.getText().toString().trim());        }
        catch(Exception ex)
        {
            return 0;
        }

    }
    public String regRegion(){

        return etreg.getText().toString().trim();
    }
    public String regLang(){

        return etLang.getText().toString().trim();
    }
    public String regPass(){

        return etPass.getText().toString().trim();
    }
    public String regEmail(){

        return etEmail.getText().toString().trim();
    }



    public void reg2(View view) {
        User user = new User(++idForDB,FirstName,LastName,Lang,age,Region,Pass,Email);
        user.saveToDB(this);

        Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
        startActivity(intent);
    }
}
