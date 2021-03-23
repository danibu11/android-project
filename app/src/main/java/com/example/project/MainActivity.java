package com.example.project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
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
    Button editStartTimeBtn,editEndTimeBtn,editDateBtn, taskBtn, editUserInfoBtn;
    String fullNameText, emailText;
    int selectedStartHour,selectedStartMinute,selectedEndHour,selectedEndMinute,month,day,yearForDate,idForTasks=0,taskLength,currentMonth,currentDay,currentYearForDate, idForDB;
    ArrayAdapter<String> adapter;
    AlertDialog dialog = null;
    String notificationChannelId = "disOrder_notification_id";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idForDB = getIntent().getIntExtra("GET_USER_ID", 100);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(notificationChannelId, "disOrder_notification_id", importance);
            mChannel.enableLights(true);
            mNotificationManager.createNotificationChannel(mChannel);
        }


        //// TODO delete this crap
        //        scheduleNotification(getNotification("POOOOOOOOOOP!", "Disorder Task Notification!"), 5000);
        /*Calendar currentCalendar=Calendar.getInstance();
        currentDay=currentCalendar.get(Calendar.DAY_OF_MONTH);
        currentYearForDate=currentCalendar.get(Calendar.YEAR);
        currentMonth=currentCalendar.get(Calendar.MONTH);
        currentCalendar.set(currentYearForDate, currentMonth, currentDay);
        Calendar testDate=Calendar.getInstance();
        testDate.set(currentYearForDate, currentMonth, currentDay);
        if (testDate.equals(currentCalendar)) {
            Toast.makeText(MainActivity.this, "EQUALS ", Toast.LENGTH_LONG).show();
        } else {
            long diff = testDate.getTimeInMillis() - currentCalendar.getTimeInMillis();
            Toast.makeText(MainActivity.this, "😀 NOT EQUALS, DIFF: "+diff, Toast.LENGTH_LONG).show();
        }*/
        ////
        FloatingActionButton fab = findViewById(R.id.fab);
        taskList = findViewById(R.id.taskList);

        ArrayList<Tasks> allTasks = DBHelper.getAllTasksFromDB(MainActivity.this);
        String[] allTaskStrings= new String[allTasks.size()];
        for (int i = 0; i < allTasks.size(); i++) {
            boolean isCompleted = String.valueOf(allTasks.get(i).getCompleted()).compareTo("true") == 0;
            Log.d("EVALUATING TASK COMPLETION FOR TASK " + allTasks.get(i).getId(), String.valueOf(allTasks.get(i).getCompleted()));
            String statusSymbol = isCompleted ? "✅" : "❌";
            allTaskStrings[i] = allTasks.get(i).getDescription() + " - " + statusSymbol; // this is what's going to be displayed in the list row
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

        ArrayList<User> allUsers = DBHelper.getAllUsersFromDB(this);
        idForDB = getIntent().getIntExtra("GET_USER_ID", 100);
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getId() == idForDB) {
                fullNameText = "Hello "+allUsers.get(i).getF_name()+" "+allUsers.get(i).getL_name();
                emailText = "      "+allUsers.get(i).getEmail();

            }
        }

        editUserInfoBtn = findViewById(R.id.editStudyHelperBtn);
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
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        try{
                            ArrayList<Tasks> currentTasks = DBHelper.getAllTasksFromDB(MainActivity.this);
                            idForTasks = currentTasks.size() + 1;
                            Log.d("myDate ctor payload", day+" "+month+" "+yearForDate);
                            MyDate date=new MyDate(day,month,yearForDate);
                            MyTime time=new MyTime(selectedStartHour,selectedStartMinute,selectedEndHour,selectedEndMinute);
                            long timeTillTaskInMilliseconds = getTimeTillTaskInMilliseconds(date, time);
                            scheduleNotification(getNotification(taskDiscriptionET.getText().toString(), "Disorder Task Notification!"), timeTillTaskInMilliseconds);
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



private long getTimeTillTaskInMilliseconds(MyDate futureDate, MyTime futureTime) {
    Calendar now = Calendar.getInstance();
    Calendar taskStart = Calendar.getInstance();
    taskStart.set(futureDate.getYear(), futureDate.getMounth(), futureDate.getDay(), futureTime.getStartHour(), futureTime.getStartMins());
    long resultDiffInMilliseconds = taskStart.getTimeInMillis() - now.getTimeInMillis();
    Log.d("RESULT DIFF IN MILLISECONDS:", String.valueOf(resultDiffInMilliseconds));
    return resultDiffInMilliseconds;
};

private void scheduleNotification(Notification notification, long delay) {
    Intent notificationIntent = new Intent(this, NotificationPublisher.class);
    notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
    notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    long futureInMillis = (-1) * (SystemClock.elapsedRealtime() + delay);
    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    Toast.makeText(MainActivity.this, "scheduled task notification to; "+futureInMillis+"milliseconds from now", Toast.LENGTH_LONG).show();
    Log.d("SCHEDULE NOTIFICATION DELAY:", String.valueOf(futureInMillis));
}

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification getNotification(String content, String title) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("\uD83D\uDE00" + title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setChannelId(notificationChannelId);
        return builder.build();
    }

    private void refreshList() {
        Log.d(TAG, "refreshList");
        ArrayList<Tasks> allTasks = DBHelper.getAllTasksFromDB(MainActivity.this);
        String[] allTaskStrings= new String[allTasks.size()];
        for (int i = 0; i < allTasks.size(); i++) {
            boolean isCompleted = String.valueOf(allTasks.get(i).getCompleted()).compareTo("true") == 0;
            Log.d("EVALUATING TASK COMPLETION FOR TASK " + allTasks.get(i).getId(), String.valueOf(allTasks.get(i).getCompleted()));
            String statusSymbol = isCompleted ? "✅" : "❌";
            allTaskStrings[i] = allTasks.get(i).getDescription() + " - " + statusSymbol;
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
        // this is used to find if the task day is today
        Calendar calendar=Calendar.getInstance();
        currentDay=calendar.get(Calendar.DAY_OF_MONTH);
        currentYearForDate=calendar.get(Calendar.YEAR);
        currentMonth=calendar.get(Calendar.MONTH);
        Calendar today=Calendar.getInstance();
        today.set(currentYearForDate, currentMonth, currentDay);
        Calendar taskDay=Calendar.getInstance();
        //        taskDay.set(taskYear, taskMonth, taskYear);
        //        boolean isSameDay = today.equals(taskDay);
        ArrayList<Tasks> currentTasks = DBHelper.getAllTasksFromDB(MainActivity.this);
        int sum = 0;
        for (int i = 0; i < currentTasks.size(); i++){
            Tasks currentTask = currentTasks.get(i);
            taskDay.set(currentTask.getDate().getYear(), currentTask.getDate().getMounth(), currentTask.getDate().getDay());
             if(today.equals(taskDay)){
                sum++;
            }
        }
        return sum;
    }

    public void closeDialogFunc(AlertDialog.Builder mBuilder){

        dialog.dismiss();
    }


    //as straightforward as can be
    public void deleteDB(){
        Log.d(TAG, "delete DB");
        this.deleteDatabase("buchnitzDB");
    }

    public void switchActivityToReg2(View view) {


        Intent intent = new Intent(MainActivity.this, RegisterActivity2.class);
        intent.putExtra("GET_USER_ID", idForDB);
        intent.putExtra("purpose", "watch");
        startActivity(intent);
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