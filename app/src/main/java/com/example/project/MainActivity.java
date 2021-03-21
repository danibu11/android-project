package com.example.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    ListView taskList;
    EditText taskDiscriptionET, taskPartET;
    Button editStartTimeBtn,editEndTimeBtn,editDateBtn, taskBtn;
    String fullNameText, emailText;
    int selectedStartHour,selectedStartMinute,selectedEndHour,selectedEndMinute,month,day,yearForDate,idForTasks=0,taskLength,currentMonth,currentDay,currentYearForDate;
    ArrayAdapter<String> adapter;
    AlertDialog dialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = findViewById(R.id.fab);
        taskList = findViewById(R.id.taskList);

        ArrayList<Tasks> allTasks = DBHelper.getAllTasksFromDB(MainActivity.this);
        String[] allTaskStrings= new String[allTasks.size()];
        for (int i = 0; i < allTasks.size(); i++) {
            allTaskStrings[i] = allTasks.get(i).getDescription() + " - " + allTasks.get(i).getCompleted(); // this is what's going to be displayed in the list row
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allTaskStrings);
        // this is what happens when we press an item on the list

        refreshList();

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");
                /*
                    the position you get here is the position in the list
                    it corresponds to the same position in the array/arraylist "allTaskStrings"/"allTasks"
                    get the corresponding item from there and use it as you like
                    you can also use this:
                    // Tasks selectedTask = (Tasks) parent.getAdapter().getItem(position); //get selected Tasks instance
                 */
            }
        });

        Calendar calendar=Calendar.getInstance();
        currentDay=calendar.get(Calendar.DAY_OF_MONTH);
        currentYearForDate=calendar.get(Calendar.YEAR);
        currentMonth=calendar.get(Calendar.MONTH);

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
                editStartTimeBtn= (Button) mView.findViewById(R.id.startTimeBtn);
                editEndTimeBtn= (Button) mView.findViewById(R.id.endTimeBtn);
                editDateBtn= (Button) mView.findViewById(R.id.dateBtn);
                taskDiscriptionET= mView.findViewById(R.id.descriptionET);
                taskPartET= mView.findViewById(R.id.partET);
                taskBtn= mView.findViewById(R.id.addTaskBtn);

                editStartTimeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar=Calendar.getInstance();
                        selectedStartMinute=calendar.get(Calendar.MINUTE);
                        selectedStartHour=calendar.get(Calendar.HOUR_OF_DAY);
                        TimePickerDialog timePickerDialog= new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                updateStartTime(hourOfDay, minute);
                                Log.d(TAG, "selected start time: "+selectedStartMinute+" "+selectedStartHour);
                            }
                        },selectedStartHour,selectedStartMinute,android.text.format.DateFormat.is24HourFormat(MainActivity.this));

                        timePickerDialog.show();
                    }
                });
                Log.d(TAG, "selected start time: "+selectedStartMinute+" "+selectedStartHour);
                editEndTimeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar=Calendar.getInstance();
                        selectedEndMinute=calendar.get(Calendar.MINUTE);
                        selectedEndHour=calendar.get(Calendar.HOUR_OF_DAY);
                        TimePickerDialog timePickerDialog= new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                updateEndTime(hourOfDay, minute);
                                Log.d(TAG, "selected end time: "+selectedEndMinute+" "+selectedEndHour);
                            }
                        },selectedEndHour,selectedEndMinute,android.text.format.DateFormat.is24HourFormat(MainActivity.this));
                        timePickerDialog.show();
                    }
                });
                editDateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Log.d("SELECTED DATE IN ADDTASK LISTENER", dayOfMonth+" "+month+" "+yearForDate);
                                updateDateStamp(year, month, dayOfMonth);
                            }
                        },currentYearForDate,currentMonth,currentDay);
                        datePickerDialog.show();
                    }
                });

                taskBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            ArrayList<Tasks> currentTasks = DBHelper.getAllTasksFromDB(MainActivity.this);
                            idForTasks = currentTasks.size() + 1;
                            Log.d("myDate ctor payload", day+" "+month+" "+yearForDate);
                            MyDate date=new MyDate(day,month,yearForDate);
                            MyTime time=new MyTime(selectedStartHour,selectedStartMinute,selectedEndHour,selectedEndMinute);
                            Log.d(TAG, time.toString()+" "+date.toString());
                            taskLength = time.getFinishHour() - time.getStartHour();

                            Tasks tasks=new Tasks(taskPartET.getText().toString(), taskLength, idForTasks, taskDiscriptionET.getText().toString(), date, time);
                            Log.d(TAG+1, tasks.getDate().toString());
                            Log.d(TAG+2, tasks.getTime().toString());
                            tasks.saveToDB(MainActivity.this);
                            Log.d(TAG,tasks.toString());
                            Toast.makeText(MainActivity.this, "Task added ", Toast.LENGTH_LONG).show();
                            refreshList();
                            dialog.dismiss();
                        }
                        catch (Exception e) {
                            Log.d(TAG, "lo oved");
                            Log.d(TAG, e.getMessage());
                        }
                    }
                });
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
            }
        });

    }

    private void refreshList() {
        Log.d(TAG, "refreshList");
        ArrayList<Tasks> allTasks = DBHelper.getAllTasksFromDB(MainActivity.this);
        String[] allTaskStrings= new String[allTasks.size()];
        for (int i = 0; i < allTasks.size(); i++) {
            allTaskStrings[i] = allTasks.get(i).getDescription() + " - " + allTasks.get(i).getCompleted();
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allTaskStrings);
        taskList.setAdapter(adapter); //re-set the list`s adapter
    }

    private void updateStartTime(int newStartHour, int newStartMinute) {
        this.selectedStartHour = newStartHour;
        this.selectedStartMinute = newStartMinute;
    }

    private void updateEndTime(int newEndHour, int newEndMinute) {
        this.selectedEndHour = newEndHour;
        this.selectedEndMinute = newEndMinute;
    }

    private void updateDateStamp(int inputYear, int inputMonth, int inputDayOfMonth) {
        Log.d("USER SELECTED DATE", inputDayOfMonth+" "+inputMonth+" "+inputYear);
        this.yearForDate = inputYear;
        this.month = inputMonth + 1;
        this.day = inputDayOfMonth;
        Log.d("PARSED SELECTED DATE", this.day+" "+this.month+" "+this.yearForDate);
    }

    public int numOfTodayTasks(){
        ArrayList<Tasks> currentTasks = DBHelper.getAllTasksFromDB(MainActivity.this);
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

    public void closeDialogFunc(AlertDialog.Builder mBuilder){
        Dialog dialog = mBuilder.create();
        dialog.dismiss();
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