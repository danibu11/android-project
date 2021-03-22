package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RadioButton a_d_dButton,ritalinButton,konsertaButton,adhdButton;
    static int countadd=0;
    static int countrit=0;
    static int countkonserta=0;
    static int countadhd=0;
     Spinner spinner;
    List<String> stringsChoicse;
    static int spinnerResult;
    boolean buttonsEnabled = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        if (true) { // if mode is view - buttons are disabled
            buttonsEnabled = true;
        } else {
            buttonsEnabled = false;
        }

        a_d_dButton = findViewById(R.id.addBtn);
        ritalinButton= findViewById(R.id.ritalinBtn);
        konsertaButton= findViewById(R.id.konsertaBtn);
        adhdButton=findViewById(R.id.adhdBtn);
        spinner=findViewById(R.id.spinner);
        a_d_dButton.setEnabled(buttonsEnabled);
//create array to push into the spinner adapter

        stringsChoicse=new ArrayList<String>();
        stringsChoicse.add("less then 3 meals");
        stringsChoicse.add("3 meals per day");
        stringsChoicse.add("more then 3 meals");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,stringsChoicse);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelected(false);
        spinner.setSelection(0,true);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String item=parent.getItemAtPosition(position).toString();
        if(item =="less then 3 meals")
            spinnerResult=1;
        else if(item =="3 meals per day" )
            spinnerResult=2;
        else
            spinnerResult=3;
        Toast.makeText(parent.getContext(),"Selected :"+ item+ " "  +"Choise number :" +spinnerResult ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    public void clickOnADDButton(View view)
    {

        if ((a_d_dButton.isPressed() && countadd % 2 == 0))
        {


            a_d_dButton.setPressed(true);

            countadd++;
        }
        else if(( a_d_dButton.isPressed() && countadd % 2 == 1))
        {
            a_d_dButton.setPressed(false);

            countadd++;
        }

    }

    public void clickOnRitalinButton(View view)
    {
        if ((ritalinButton.isPressed() && countrit % 2 == 0))
        {

            ritalinButton.setPressed(true);

            countrit++;
        }
        else if((ritalinButton.isPressed() && countrit % 2 == 1))
        {

            ritalinButton.setPressed(false);

            countrit++;
        }
    }

    public void clickOnKonsertaButton(View view)
    {
        if ((konsertaButton.isPressed() && countkonserta % 2 == 0))
        {


            konsertaButton.setPressed(true);

            countkonserta++;
        }
        else if((konsertaButton.isPressed() && countkonserta % 2 == 1))
        {

            konsertaButton.setPressed(false);

            countkonserta++;
        }
    }

    public void clickOnADHDButton(View view) {
        if ((adhdButton.isPressed() && countadhd % 2 == 0))
        {


            adhdButton.setPressed(true);

            countadhd++;
        }
        else if((adhdButton.isPressed() && countadhd % 2 == 1))
        {

            adhdButton.setPressed(false);

            countadhd++;
        }
    }

    public void endRegFunc(View view) {
        int userId = 100;
        try {
            userId= getIntent().getIntExtra("ID_FOR_USER",100);
        }
        catch (Exception e){
            Log.d("Register2", ""+userId);
        }

        StudyHelper studyHelper=new StudyHelper(userId, a_d_dButton.isPressed(), adhdButton.isPressed(),ritalinButton.isPressed(),konsertaButton.isPressed(),spinnerResult);
        try {
            Log.d("Regiter2", "      "+userId);
            String purpose = getIntent().getStringExtra("purpose");
            if (purpose.compareTo("registration") == 0) {
                studyHelper.saveToDB(RegisterActivity2.this);
            }
            if (purpose.compareTo("edit") == 0) {
                studyHelper.deleteFromDB(RegisterActivity2.this);
                studyHelper.saveToDB(RegisterActivity2.this);
            }
        }
        catch (Exception ex) {
            Log.d("DB ERROR! - INSERT USER EXCEPTION", ex.toString());
        }
        Intent intent = new Intent(RegisterActivity2.this, LoginActivity.class);
        startActivity(intent);
    }
}
