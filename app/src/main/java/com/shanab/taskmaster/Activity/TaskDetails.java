package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.shanab.taskmaster.Activity.Models.TaskModel;
import com.shanab.taskmaster.Activity.database.TaskDatabase;
import com.shanab.taskmaster.R;

import java.util.Objects;

public class TaskDetails extends AppCompatActivity {
    public static final String TASK_NAME_TAG = "Task Title";

    private TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        Log.d("SHANAB", "onResume: " + taskTitle);
        TaskModel task = connectToDataBaseAndFindTask(getIntent().getStringExtra("TaskTitle"));
        setTitle(task.getTitle());
        TextView description = (TextView) findViewById(R.id.TaskDescription);
        TextView state = (TextView) findViewById(R.id.TaskState);
        description.setText(task.getBody());
        state.setText(task.getState().toString());
        Intent navigateToMainActivity = new Intent(this, MainActivity.class);
        findViewById(R.id.deleteTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToDataBaseAndDeleteTask(task.getId());
               startActivity(navigateToMainActivity);
            }
        });
    }
    private void connectToDataBaseAndDeleteTask(long id){
        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "Tasks").allowMainThreadQueries().build();
       taskDatabase.taskDao().deleteTaskById(id);
    }
    private TaskModel connectToDataBaseAndFindTask(String title) {
        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "Tasks").allowMainThreadQueries().build();
        return taskDatabase.taskDao().findByTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}