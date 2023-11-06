package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.shanab.taskmaster.Activity.Models.TaskModel;
import com.shanab.taskmaster.Activity.States.TaskState;
import com.shanab.taskmaster.Activity.database.TaskDatabase;
import com.shanab.taskmaster.R;

public class Add_A_Task extends AppCompatActivity {
    TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atask);
        Toolbar toolbar = findViewById(R.id.addTask_ToolBar);
        setSupportActionBar(toolbar);
        setTitle("Add Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "Tasks")
                .allowMainThreadQueries()
                .build();

        Button addTaskActivityBtn = (Button) findViewById(R.id.AddTaskBtn);
        Toast toast = Toast.makeText(this, "Task Added Successfully", Toast.LENGTH_SHORT);
        Spinner taskStateSpinner = (Spinner) findViewById(R.id.TaskStateSpinner);
        taskStateSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TaskState.values()
        ));
        Intent navigateToMainActivity = new Intent(this,MainActivity.class);
        addTaskActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskModel task = new TaskModel(
                        ((EditText) findViewById(R.id.tasktitle)).getText().toString(),
                        ((EditText) findViewById(R.id.taskDescription)).getText().toString(),
                        TaskState.fromString(taskStateSpinner.getSelectedItem().toString()));
                taskDatabase.taskDao().insertTask(task);
                toast.show();
                startActivity(navigateToMainActivity);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the back button click
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
    }
}