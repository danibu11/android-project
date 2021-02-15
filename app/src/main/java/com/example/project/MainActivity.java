package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
<<<<<<< HEAD
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView t;
=======
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView title;
    ListView lv;
>>>>>>> develop

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*db*/
        t=findViewById(R.id.title);
        t.setText("hello "+User.getF_name()+" "+User.getL_name());

        title = findViewById(R.id.title);
        /*title.setText("hello "+getF_name()+" "+getL_name());
        */



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