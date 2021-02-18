package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class RegisterActivity2 extends AppCompatActivity {
    RadioButton a_d_dButton,ritalinButton,konsertaButton,adhdButton;
    static int countadd=0;
    static int countrit=0;
    static int countkonserta=0;
    static int countadhd=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        a_d_dButton = findViewById(R.id.addBtn);
        ritalinButton= findViewById(R.id.ritalinBtn);
        konsertaButton= findViewById(R.id.konsertaBtn);
        adhdButton=findViewById(R.id.adhdBtn);
    }

    public void clickOnADDButton(View view)
    {

        if ((a_d_dButton.isPressed() && countadd % 2 == 0))
        {

            System.out.println("trueeeee "+ countadd );
            a_d_dButton.setChecked(true);
            System.out.println( a_d_dButton.isChecked());
            countadd++;
        }
        else if(( a_d_dButton.isPressed() && countadd % 2 == 1))
        {
            System.out.println("in the else if "+ countadd);
            a_d_dButton.setChecked(false);
            System.out.println(a_d_dButton.isChecked());
            countadd++;
        }

    }

    public void clickOnRitalinButton(View view)
    {
        if ((ritalinButton.isPressed() && countrit % 2 == 0))
        {

            System.out.println("trueeeee rit : "+ countrit );
            ritalinButton.setChecked(true);
            System.out.println(ritalinButton.isChecked());
            countrit++;
        }
        else if((ritalinButton.isPressed() && countrit % 2 == 1))
        {
            System.out.println("in the else if rit :"+ countrit);
            ritalinButton.setChecked(false);
            System.out.println(ritalinButton.isChecked());
            countrit++;
        }
    }

    public void clickOnKonsertaButton(View view)
    {
        if ((konsertaButton.isPressed() && countkonserta % 2 == 0))
        {

            System.out.println("trueeeee kons : "+ countkonserta );
            konsertaButton.setChecked(true);
            System.out.println(konsertaButton.isChecked());
            countkonserta++;
        }
        else if((konsertaButton.isPressed() && countkonserta % 2 == 1))
        {
            System.out.println("in the else if kons :"+ countkonserta);
            konsertaButton.setChecked(false);
            System.out.println(konsertaButton.isChecked());
            countkonserta++;
        }
    }

    public void clickOnADHDButton(View view) {
        if ((adhdButton.isPressed() && countadhd % 2 == 0))
        {

            System.out.println("trueeeee adhd : "+ countadhd );
            adhdButton.setChecked(true);
            System.out.println(adhdButton.isChecked());
            countadhd++;
        }
        else if((adhdButton.isPressed() && countadhd % 2 == 1))
        {
            System.out.println("in the else if adhd :"+ countadhd);
            adhdButton.setChecked(false);
            System.out.println(adhdButton.isChecked());
            countadhd++;
        }
    }
}
