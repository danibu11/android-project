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

        TextView tv = findViewById(R.id.testView);
        try {
            SQLConnector sqlConnector = new SQLConnector();
            if(sqlConnector.hakolTov())
                tv.setText("Oved");
            else tv.setText("Lo Oved");

        }
        catch(Exception e)
        {
            tv.setText("Lo Oved");
            Log.e("TAG", "onCreate: Failed to DB, Baasa");
        }






    }


    public void reg2(View view) {
        Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
        startActivity(intent);
    }
}
