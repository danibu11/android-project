package com.example.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity"; //for logging

    TextView fullNameTV, emailTV, todayTasks, task1, task2, task3, task4, task5;
    ListView lv;

    EditText taskDiscriptionET, taskPartET;
    Button editTimeBtn,editTimeBtn2,editDateBtn, taskBtn;
    String taskPartString, fullNameText, emailText;
    static int hour,minutes,f_hour,f_minutes,mounth,day,yearForDate, idForTasks=0, taskLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);

        task1 = findViewById(R.id.task1);
        task2 = findViewById(R.id.task2);
        task3 = findViewById(R.id.task3);
        task4 = findViewById(R.id.task4);
        task5 = findViewById(R.id.task5);

        fullNameText = getIntent().getStringExtra("GET_FULL_NAME");
        emailText = getIntent().getStringExtra("GET_EMAIL");

        fullNameTV = findViewById(R.id.fullNameTV);
        fullNameTV.setText(fullNameText);
        emailTV = findViewById(R.id.emailTV);
        emailTV.setText(emailText);
        todayTasks = findViewById(R.id.todayTasks);
        todayTasks.setText(String.valueOf(numOfTodayTasks()));


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_task_add, null);
                editTimeBtn= (Button) mView.findViewById(R.id.startTimeBtn);
                editTimeBtn2= (Button) mView.findViewById(R.id.endTimeBtn);
                editDateBtn= (Button) mView.findViewById(R.id.dateBtn);
                taskDiscriptionET= mView.findViewById(R.id.descriptionET);
                taskPartET= mView.findViewById(R.id.partET);
                taskBtn= mView.findViewById(R.id.addTaskBtn);


                editTimeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar=Calendar.getInstance();
                        minutes=calendar.get(Calendar.MINUTE);
                        hour=calendar.get(Calendar.HOUR_OF_DAY);
                        TimePickerDialog timePickerDialog= new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                minutes=minute;
                                hour=hourOfDay;
                            }
                        },hour,minutes,android.text.format.DateFormat.is24HourFormat(MainActivity.this));
                        timePickerDialog.show();
                    }

                });
                editTimeBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar=Calendar.getInstance();
                        f_minutes=calendar.get(Calendar.MINUTE);
                        f_hour=calendar.get(Calendar.HOUR_OF_DAY);
                        TimePickerDialog timePickerDialog= new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                f_minutes=minute;
                                f_hour=hourOfDay;
                                Log.d(TAG, "calendar section: "+f_minutes+" "+f_hour);
                            }
                        },f_hour,f_minutes,android.text.format.DateFormat.is24HourFormat(MainActivity.this));
                        timePickerDialog.show();
                    }

                });

                editDateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar=Calendar.getInstance();
                        day=calendar.get(Calendar.DAY_OF_MONTH);
                        yearForDate=calendar.get(Calendar.YEAR);
                        mounth=calendar.get(Calendar.MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                day=dayOfMonth;
                                yearForDate=year;
                                mounth=month+1;

                            }
                        },yearForDate,mounth,day);
                        datePickerDialog.show();
                    }
                });

                MyDate date=new MyDate(day,mounth,yearForDate);
                MyTime time=new MyTime(hour,minutes,f_hour,f_minutes);
                taskPartString = taskPartET.getText().toString();
                taskLength= time.getFinishHour()- time.getStartHour() + time.getFinishMins() - time.getStartMins();
                Tasks tasks=new Tasks(taskPartString,taskLength,idForTasks,taskDiscriptionET.getText().toString(),date, time);
                taskBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            Log.d(TAG, "onDateSet: Day:"+ day+ " Month: "+mounth+ " Year: "+yearForDate);
                        tasks.saveToDB(mBuilder.getContext());
                        Log.d("ss",tasks.toString());
                            Toast.makeText(MainActivity.this, "Task added ", Toast.LENGTH_LONG).show();

                        }
                        catch (Exception e) {
                            Log.d("ss", "lo oved");
                        }

                    }

                });

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();




            }
        });

    }
    public int numOfTodayTasks(){
        ArrayList<Tasks> currentTasks = DBHelper.getAllTasksFromDB(this);
        Calendar calendar = Calendar.getInstance();
        int sum = 0;
        for (int i = 0; i<currentTasks.size(); i++){
            if(calendar.DAY_OF_MONTH == currentTasks.get(i).getDate().getDay() && calendar.MONTH == currentTasks.get(i).getDate().getMounth()){
                sum++;
                switch(sum)
                {
                    case 1 : task1.setText(currentTasks.get(i).toString()); break;
                    case 2 : task2.setText(currentTasks.get(i).toString()); break;
                    case 3 : task3.setText(currentTasks.get(i).toString()); break;
                    case 4 : task4.setText(currentTasks.get(i).toString()); break;
                    case 5 : task5.setText(currentTasks.get(i).toString()); break;

                }
            }
        }
        return sum;
    }


    //as straightforward as can be
    public void deleteDB(){
        Log.d(TAG, "delete DB");
        this.deleteDatabase("buchnitzDB");
    }
}
/*
check your current branch name and status at any time (git status)
//new task
-stand on branch develop (git checkout develop)
-update your develop branch (git pull / git pull origin dvelop)
-open new branch from the branch you are on (git checkout -b "branchType/branchName")
-work work work
-save your work as many time as you like (git commit -a -m "my commit messgae"), -a -> include all files i modified or created, -m -> "the commit message"
-update your local branch again to get changes that were made while you were working on a side branch (git pull)
-push your branch to git (git push/git push upstream bla bla if its the first time [git will tell you what to write])
-go to git website and open a merge request from your branch to the target branch (99.99% of the time your target branch is develop)
*/





/*
/// USE THIS WHEN USER FINISHED FILLING THE SIGN UP FORM, DONT FORGET TO ADD FIELDS IN DBHELPER IF YOU NEED MORE COLUMNS IN THE TABLE ///
// this is an example of creating  user and adding it to the database
 try {
    User testUser = new User(1, "shachar", "angel", "hebrew", 33, "gaza strip", "testPass", "aa@aa.com"); // REMEMBER USER ID IS UNIQUE!!!
    testUser.saveToDB(this);
} catch (Exception ex) {
    Log.d("Login Activity", "threw an exception!", ex);
}

/// USED IN LOGIN ACTIVITY ///
// this is an example of how to get all the users from the db and going over the returned users list
ArrayList<User> allEntriesUpdated = DBHelper.getAllUsersFromDB(this);
for (int i = 0; i < allEntries.size(); i++) {
    Log.d("DEBUG_USERS", allEntries.get(i).toString());
}*/