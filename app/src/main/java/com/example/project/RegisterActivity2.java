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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RadioButton a_d_dButton,ritalinButton,konsertaButton,adhdButton;
    static int countadd=0;
    static int countrit=0;
    static int countkonserta=0;
    static int countadhd=0;
    int userId = 100;
     Spinner spinner;
    List<String> stringsChoicse;
    static int spinnerResult;
    boolean buttonsEnabled = true;
    String purpose = "" ;
    Button goBackToMainBt, editStudyHelperBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        purpose = getIntent().getStringExtra("purpose");
        userId= getIntent().getIntExtra("GET_USER_ID",100);

        Log.d("Register2",purpose);

        if (purpose.equals("watch")) { // if mode is view - buttons are disabled
            buttonsEnabled = false;
            Log.d("reg2","if");

        }
        else {
            Log.d("reg2","else");
            buttonsEnabled = true;
        }

        a_d_dButton = findViewById(R.id.addBtn);
        ritalinButton= findViewById(R.id.ritalinBtn);
        konsertaButton= findViewById(R.id.konsertaBtn);
        adhdButton=findViewById(R.id.adhdBtn);
        spinner=findViewById(R.id.spinner);
        editStudyHelperBt= findViewById(R.id.editStudyHelperBtn);
        goBackToMainBt=findViewById(R.id.returnToMainBtn);
        if(buttonsEnabled) {
            Log.d("RegisterActiviyy2",purpose);
            editStudyHelperBt.setVisibility(View.GONE);
            goBackToMainBt.setVisibility(View.GONE);
        }


        a_d_dButton.setEnabled(buttonsEnabled);
        ritalinButton.setEnabled(buttonsEnabled);
        konsertaButton.setEnabled(buttonsEnabled);
        adhdButton.setEnabled(buttonsEnabled);
        spinner.setEnabled(buttonsEnabled);

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

        if(buttonsEnabled == false){//what will happen when we come from the main

            editStudyHelperBt.setVisibility(View.VISIBLE);
            goBackToMainBt.setVisibility(View.VISIBLE);
            Log.d("Register2",purpose);
            ArrayList<StudyHelper> studyHelperArrayList = DBHelper.getStudyHelperFromDB(RegisterActivity2.this);

            userId = getIntent().getIntExtra("GET_USER_ID", 100);
            Log.d("Register2",userId+","+studyHelperArrayList.toString());


            for (int i=0; i<studyHelperArrayList.size(); i++){
                Log.d("checking for Register 2 watch",userId+","+studyHelperArrayList.get(i).getUserId());
                if(userId == studyHelperArrayList.get(i).getUserId()){
                    Log.d("Register2 when watch","filling the radio btns");
                    a_d_dButton.setChecked(studyHelperArrayList.get(i).isAdd());
                    adhdButton.setChecked(studyHelperArrayList.get(i).isAdhd());
                    ritalinButton.setChecked(studyHelperArrayList.get(i).isRitalin());
                    konsertaButton.setChecked(studyHelperArrayList.get(i).isKonserta());
                    //here we should initiate the spinner from the valeu we get
                    spinner.setSelection(studyHelperArrayList.get(i).getMealsPerDay()-1);
                }
            }
        }

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

        if ((a_d_dButton.isChecked() && countadd % 2 == 0))
        {


            a_d_dButton.setChecked(true);

            countadd++;
        }
        else if(( a_d_dButton.isChecked() && countadd % 2 == 1))
        {
            a_d_dButton.setChecked(false);

            countadd++;
        }

    }

    public void clickOnRitalinButton(View view)
    {
        if ((ritalinButton.isChecked() && countrit % 2 == 0))
        {

            ritalinButton.setChecked(true);

            countrit++;
        }
        else if((ritalinButton.isChecked() && countrit % 2 == 1))
        {

            ritalinButton.setChecked(false);

            countrit++;
        }
    }

    public void clickOnKonsertaButton(View view)
    {
        if ((konsertaButton.isChecked() && countkonserta % 2 == 0))
        {


            konsertaButton.setChecked(true);

            countkonserta++;
        }
        else if((konsertaButton.isChecked() && countkonserta % 2 == 1))
        {

            konsertaButton.setChecked(false);

            countkonserta++;
        }
    }

    public void clickOnADHDButton(View view) {
        if ((adhdButton.isChecked() && countadhd % 2 == 0))
        {


            adhdButton.setChecked(true);

            countadhd++;
        }
        else if((adhdButton.isChecked() && countadhd % 2 == 1))
        {

            adhdButton.setChecked(false);

            countadhd++;
        }
    }

    public void endRegFunc(View view) {
        userId= getIntent().getIntExtra("GET_USER_ID",100);
        Log.d("Register2", ""+userId);
        StudyHelper studyHelper=new StudyHelper(userId, a_d_dButton.isChecked(), adhdButton.isChecked(),ritalinButton.isChecked(),konsertaButton.isChecked(),spinnerResult);
        try {
            Log.d("Regiter2", "      "+studyHelper.toString());

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
        if(purpose.equals("edit")){
            Log.d("reg2","if 2");
            userId = getIntent().getIntExtra("GET_USER_ID",100);
            Intent intent = new Intent(RegisterActivity2.this, MainActivity.class);
            intent.putExtra("GET_USER_ID", userId);
            startActivity(intent);
        }
        else {
            Log.d("reg2","else 2");
            Intent intent = new Intent(RegisterActivity2.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void returnToMainFunc(View view) {
        userId = getIntent().getIntExtra("GET_USER_ID",100);
        Intent intent = new Intent(RegisterActivity2.this, MainActivity.class);
        intent.putExtra("GET_USER_ID", userId);

        startActivity(intent);

    }

    public void editStudyHelperFunc(View view) {
        userId= getIntent().getIntExtra("GET_USER_ID",100);
        Intent refresh = new Intent(getApplicationContext(), RegisterActivity2.class);
        refresh.putExtra("purpose","edit");
        refresh.putExtra("GET_USER_ID", userId);
        startActivity(refresh);
        finish();

    }
}
