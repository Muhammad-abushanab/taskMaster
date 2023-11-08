package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskModel;
import com.shanab.taskmaster.Activity.Adapters.TaskAdapter;
import com.shanab.taskmaster.Activity.States.TaskState;
import com.shanab.taskmaster.Activity.database.TaskDatabase;
import com.shanab.taskmaster.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String DATABASE_NAME = "Tasks";
    TaskAdapter adapter;
    TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        settingNavigationButtons();
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
        setUpTaskRecyclerView();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userNickname = preferences.getString(SettingsActivity.USER_NICKNAME_TAG, "No nickname");
        ((TextView) findViewById(R.id.homeHeader)).setText(getString(R.string.nick_name, userNickname));
    }


//    private void openTaskDetail(String taskTitle) {
//        Intent navigate_To_TaskDetails_Activity = new Intent(this, TaskDetails.class);
//        navigate_To_TaskDetails_Activity.putExtra("TaskTitle", taskTitle);
//        startActivity(navigate_To_TaskDetails_Activity);
//    }

    private void setUpTaskRecyclerView() {
        Log.d("MainActivity", "setUpTaskRecyclerView is being called");
        RecyclerView taskListRecyclerView = (RecyclerView) findViewById(R.id.TaskRecView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskListRecyclerView.setLayoutManager(layoutManager);
//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, DATABASE_NAME)
//                .fallbackToDestructiveMigration()
//                .allowMainThreadQueries().build();
//        List<TaskModel> tasks = taskDatabase.taskDao().findAll();

        List<TaskModel> tasks = new ArrayList<>();

        Amplify.API.query(
                ModelQuery.list(TaskModel.class),
                success ->
                {
                    Log.i("Crazy", "Read Product successfully");
                    tasks.clear();
                    for (TaskModel databaseTask : success.getData()){
                        tasks.add(databaseTask);
                    }
                    runOnUiThread(() ->{
                        adapter.notifyDataSetChanged();
                    });
                },
                failure -> Log.i("Crazy", "Did not read products successfully")
        );

        adapter = new TaskAdapter(tasks, this);
        taskListRecyclerView.setAdapter(adapter);
    }

    private void settingNavigationButtons() {
        Intent navigateToAddTaskPage = new Intent(this, Add_A_Task.class);
        Intent navigateToAllTaskActivity = new Intent(this, All_Tasks.class);
        Intent navigateToSettingsActivity = new Intent(this, SettingsActivity.class);
        findViewById(R.id.addTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(navigateToAddTaskPage);
            }
        });
        findViewById(R.id.allTasks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(navigateToAllTaskActivity);
            }
        });
        findViewById(R.id.settingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(navigateToSettingsActivity);
            }
        });
    }


}