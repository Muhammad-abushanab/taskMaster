package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shanab.taskmaster.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        Button addTaskBtn = (Button) findViewById(R.id.addTask);
        Intent navigateToAddTaskPage = new Intent(this, Add_A_Task.class);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(navigateToAddTaskPage);
            }
        });
        Button allTasksBtn = (Button) findViewById(R.id.allTasks);
        Intent navigateToAllTaskActivity = new Intent(this, All_Tasks.class);
        allTasksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(navigateToAllTaskActivity);
            }
        });
        Intent navigateToSettingsActivity = new Intent(this, SettingsActivity.class);
        findViewById(R.id.settingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(navigateToSettingsActivity);
            }
        });

        Button task1btn = findViewById(R.id.task1Btn);
        String task1Title = task1btn.getText().toString();
        task1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTaskDetail(task1Title);
            }
        });

        Button task2btn = findViewById(R.id.task2Btn);
        String task2Title = task2btn.getText().toString();
        task2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTaskDetail(task2Title);
            }
        });

        Button task3btn = findViewById(R.id.task3Btn);
        String task3Title = task3btn.getText().toString();
        task3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTaskDetail(task3Title);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userNickname = preferences.getString(SettingsActivity.USER_NICKNAME_TAG, "No nickname");
        ((TextView) findViewById(R.id.homeHeader)).setText(getString(R.string.nick_name,userNickname));
    }


    private void openTaskDetail(String taskTitle) {
        Intent navigate_To_TaskDetails_Activity = new Intent(this, TaskDetails.class);
        navigate_To_TaskDetails_Activity.putExtra("TaskTitle",taskTitle);
        startActivity(navigate_To_TaskDetails_Activity);
    }
}