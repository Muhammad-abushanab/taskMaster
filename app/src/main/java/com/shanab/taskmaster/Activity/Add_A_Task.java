package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shanab.taskmaster.R;

public class Add_A_Task extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atask);
        Toolbar toolbar = findViewById(R.id.addTask_ToolBar);
        setSupportActionBar(toolbar);
        setTitle("Add Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button addTaskActivityBtn = (Button) findViewById(R.id.AddTaskBtn);
        Toast toast = Toast.makeText(this , "Task Added Successfully", Toast.LENGTH_SHORT);
        addTaskActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast.show();
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