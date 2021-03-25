package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.DriverManager;
import java.sql.Connection;
import java.util.ArrayList;

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
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText etreg,etLN,etFN,etLang,etAge,etPass,etEmail,etPass2;
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
        etPass2=findViewById(R.id.conPassET);

        bt=findViewById(R.id.button);

        FirstName=regFName();
        LastName=regLName();
        age=regAge();
        Region=regRegion();
        Lang=regLang();
        Pass=regPass();
        Email=regEmail();

    }
    private static final String TAG = "db try";
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

    public String regPass2(){
        return etPass2.getText().toString().trim();
    }

    public String regEmail(){
        return etEmail.getText().toString().trim();
    }

    private boolean isUserExist(String newUserEmail) {
        ArrayList<User> allUsers = DBHelper.getAllUsersFromDB(this);
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getEmail().compareTo(newUserEmail) == 0) {
                return true;
            }
        }
        return false;
    }

    public void reg2(View view) {
        ArrayList<User> currentTasks = DBHelper.getAllUsersFromDB(RegisterActivity.this);
        idForDB = currentTasks.size();
        FirstName = regFName();
        LastName = regLName();
        age = regAge();
        Region = regRegion();
        Lang = regLang();
        Pass = regPass();
        Email = regEmail();
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        User user = new User(idForDB, FirstName, LastName, Lang, age, Region, Pass, Email);

        Log.d("user detail after creation", "" + Email + " " + FirstName);
        if (isUserExist(Email))
        {//TOAST "please select another email, user already exists"
            Toast.makeText(RegisterActivity.this,"please select another Email, user already exists",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!etPass2.getText().toString().trim().equals(etPass.getText().toString()) && !etPass.getText().toString().trim().equals("")){
            Toast.makeText(RegisterActivity.this,"please make sure the password is the same in both places",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(etLN.getText().toString().trim().equals("")){
            Toast.makeText(RegisterActivity.this,"plese enter your last name",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(etFN.getText().toString().trim().equals("")){
            Toast.makeText(RegisterActivity.this,"plese enter your first name",Toast.LENGTH_SHORT).show();
            return;
        }
        if (Email.matches(emailPattern))
            Toast.makeText(getApplicationContext(), "Valid email address", Toast.LENGTH_SHORT).show();
        else
        {
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            return ;
        }

        Log.d("CREATE", "OK");
        Log.d("CREATED", user.toString());
        //try - catch save user to db


        try {

            user.saveToDB(RegisterActivity.this);
            Log.d("SAVE", "OK");
            }
        catch (Exception e) {

            Log.d("SAVE", "NOT OK  "+ e.getMessage());
        }


        Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
        intent.putExtra("GET_USER_ID", idForDB);
        intent.putExtra("purpose", "registration"); //  registration/edit/view
        startActivity(intent);

    }
}
