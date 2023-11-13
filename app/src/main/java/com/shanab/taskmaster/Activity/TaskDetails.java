package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskModel;
import com.shanab.taskmaster.database.TaskDatabase;
import com.shanab.taskmaster.R;

import java.util.Objects;

public class TaskDetails extends AppCompatActivity {
    public static final String TASK_NAME_TAG = "Task Title";
    private TaskModel task;
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
//        TaskModel task = connectToDataBaseAndFindTask(getIntent().getStringExtra("TaskId"));
        getTask(getIntent().getStringExtra("TaskId"));
        Intent navigateToMainActivity = new Intent(this, MainActivity.class);
        findViewById(R.id.deleteTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToDataBaseAndDeleteTask();
               startActivity(navigateToMainActivity);
            }
        });
    }
    private void connectToDataBaseAndDeleteTask(){
        Amplify.API.mutate(ModelMutation.delete(task),
                response -> Log.i("MyAmplifyApp", "Deleted"),
                error -> Log.e("MyAmplifyApp", "Delete failed", error));
    }
//    private TaskModel connectToDataBaseAndFindTask(String title) {
//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "Tasks").allowMainThreadQueries().build();
//        return taskDatabase.taskDao().findByTitle(title);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getTask(String id) {
        Amplify.API.query(
                ModelQuery.get(TaskModel.class, id),
                response -> {
                    task = response.getData();
                    updateUI();
                },
                error -> Log.e("MyAmplifyApp", error.toString(), error)
        );
    }
    private void updateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (task != null) {
                    setTitle(task.getTitle());
                    TextView description = findViewById(R.id.TaskDescription);
                    TextView state = findViewById(R.id.TaskState);
                    description.setText(task.getDescription());
                    state.setText(task.getState().toString());
                }
            }
        });
    }

}