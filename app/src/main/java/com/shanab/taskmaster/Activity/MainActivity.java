package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shanab.taskmaster.Activity.Adapters.TaskAdapter;
import com.shanab.taskmaster.Activity.Models.TaskModel;
import com.shanab.taskmaster.Activity.States.TaskState;
import com.shanab.taskmaster.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpTaskRecyclerView();
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
    private void setUpTaskRecyclerView(){
        Log.d("MainActivity", "setUpTaskRecyclerView is being called");
        RecyclerView taskListRecyclerView = (RecyclerView) findViewById(R.id.TaskRecView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskListRecyclerView.setLayoutManager(layoutManager);

        List<TaskModel> tasks = new ArrayList<>();
        tasks.add(new TaskModel("Shopping","I Am going for shopping at 3 am", TaskState.NEW));
        tasks.add(new TaskModel("Work","Some missing files", TaskState.ASSIGNED));
        tasks.add(new TaskModel("Having Fun","Meet with my friends at 8 pm", TaskState.IN_PROGRESS));
        tasks.add(new TaskModel("Time to rest","Take a nap for 20 minutes", TaskState.COMPLETED));

        TaskAdapter adapter = new TaskAdapter(tasks,this);
        taskListRecyclerView.setAdapter(adapter);
    }
}