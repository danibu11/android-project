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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        a_d_dButton = findViewById(R.id.addBtn);
        ritalinButton= findViewById(R.id.ritalinBtn);
        konsertaButton= findViewById(R.id.konsertaBtn);
        adhdButton=findViewById(R.id.adhdBtn);
        spinner=findViewById(R.id.spinner);

        stringsChoicse=new ArrayList<String>();

        stringsChoicse.add("less then 3 meals");
        stringsChoicse.add("3 meals per day");
        stringsChoicse.add("more then 3 meals");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,stringsChoicse);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelected(false);  // must
        spinner.setSelection(0,true);  //must
        spinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String item=parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),"Selected :"+ item ,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    public void clickOnADDButton(View view)
    {

        if ((a_d_dButton.isPressed() && countadd % 2 == 0))
        {


            a_d_dButton.setChecked(true);

            countadd++;
        }
        else if(( a_d_dButton.isPressed() && countadd % 2 == 1))
        {
            a_d_dButton.setChecked(false);

            countadd++;
        }

    }

    public void clickOnRitalinButton(View view)
    {
        if ((ritalinButton.isPressed() && countrit % 2 == 0))
        {

            ritalinButton.setChecked(true);

            countrit++;
        }
        else if((ritalinButton.isPressed() && countrit % 2 == 1))
        {

            ritalinButton.setChecked(false);

            countrit++;
        }
    }

    public void clickOnKonsertaButton(View view)
    {
        if ((konsertaButton.isPressed() && countkonserta % 2 == 0))
        {


            konsertaButton.setChecked(true);

            countkonserta++;
        }
        else if((konsertaButton.isPressed() && countkonserta % 2 == 1))
        {

            konsertaButton.setChecked(false);

            countkonserta++;
        }
    }

    public void clickOnADHDButton(View view) {
        if ((adhdButton.isPressed() && countadhd % 2 == 0))
        {


            adhdButton.setChecked(true);

            countadhd++;
        }
        else if((adhdButton.isPressed() && countadhd % 2 == 1))
        {

            adhdButton.setChecked(false);

            countadhd++;
        }
    }

    public void pressRegister2(View view) {
       // StudyHelper studyHelper = new StudyHelper(a_d_dButton.isChecked(),adhdButton.isChecked(),ritalinButton.isChecked(),konsertaButton.isChecked(),spinnerResult);
        //studyHelper.saveToDB(this);

        Intent intent = new Intent(RegisterActivity2.this, LoginActivity.class);
        startActivity(intent);
        //trying to fill the database but create for now problams

    }



}
