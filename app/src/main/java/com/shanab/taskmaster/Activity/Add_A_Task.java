package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskModel;
import com.amplifyframework.datastore.generated.model.TaskState;
import com.shanab.taskmaster.Activity.database.TaskDatabase;
import com.shanab.taskmaster.R;

import java.util.Date;
import java.util.Objects;

public class Add_A_Task extends AppCompatActivity {
    TaskDatabase taskDatabase;
    public static final String TAG = "AddProductActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atask);
        Toolbar toolbar = findViewById(R.id.addTask_ToolBar);
        setSupportActionBar(toolbar);
        setTitle("Add Task");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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
        Intent navigateToMainActivity = new Intent(this, MainActivity.class);
        addTaskActivityBtn.setOnClickListener(view -> {
            String title = ((EditText) findViewById(R.id.tasktitle)).getText().toString();
            String description = ((EditText) findViewById(R.id.taskDescription)).getText().toString();
            Log.d("titleFrom", title);
            Log.d("titleFrom", description);
//            Log.d("titleFrom", (String) taskStateSpinner.getSelectedItem());
            String currentDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());
            TaskModel newTask = TaskModel.builder()
                    .title(title)
                    .description(description)
                    .dateCreated(new Temporal.DateTime(new Date(), 0))
                    .state((com.amplifyframework.datastore.generated.model.TaskState) taskStateSpinner.getSelectedItem())
                    .build();
            Amplify.API.mutate(
                    ModelMutation.create(newTask),
                    successResponse -> {
                        Log.i(TAG, "Task saved successfully");
                        toast.show();
                        startActivity(navigateToMainActivity);
                    },
                    failureResponse -> {
                        Log.e(TAG, "Failed to save task: " + failureResponse.toString());
                        toast.show();
                    }
            );
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