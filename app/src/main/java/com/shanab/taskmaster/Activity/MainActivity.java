package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.TaskModel;
import com.amplifyframework.datastore.generated.model.Team;
import com.shanab.taskmaster.Adapters.TaskAdapter;
import com.shanab.taskmaster.database.TaskDatabase;
import com.shanab.taskmaster.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String DATABASE_NAME = "Tasks";
    TaskAdapter adapter;
    TaskDatabase taskDatabase;
    String teamName = null;
    AuthUser authUser = Amplify.Auth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        if (authUser == null) {
            Intent goToLoginPage = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(goToLoginPage);
        }
        settingNavigationButtons();
//        createTeams();
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
        String userTeam = preferences.getString(SettingsActivity.USER_TEAM_TAG, "No Team");
        ((TextView) findViewById(R.id.homeHeader)).setText(getString(R.string.nick_name, authUser.getUsername()));
        ((TextView) findViewById(R.id.teamNameInMain)).setText(getString(R.string.team_name, userTeam));
        teamName = ((TextView) findViewById(R.id.teamNameInMain)).getText().toString();
        setUpTaskRecyclerView();
    }
    private void setUpTaskRecyclerView() {
        Log.d("MainActivity", "setUpTaskRecyclerView is being called");
        RecyclerView taskListRecyclerView = (RecyclerView) findViewById(R.id.TaskRecView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskListRecyclerView.setLayoutManager(layoutManager);
        List<TaskModel> tasks = new ArrayList<>();

        Log.i("SHANAB", "setUpTaskRecyclerView: " + teamName);
        Amplify.API.query(
                ModelQuery.list(TaskModel.class,TaskModel.TEAM_NAME.eq(teamName)),
                success ->
                {
                    Log.i("Crazy", "Read Product successfully");
                    tasks.clear();
                    for (TaskModel databaseTask : success.getData()) {
                        tasks.add(databaseTask);
                        Log.d("TeamName", "setUpTaskRecyclerView() returned: " + databaseTask.getTitle());
                    }
                    runOnUiThread(() -> {
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

    private void createTeams() {
        Team t1 = Team.builder().name("Innovation Avengers").dateCreated(new Temporal.DateTime(new Date(), 0)).build();
        Team t2 = Team.builder().name("Code Crusaders").dateCreated(new Temporal.DateTime(new Date(), 0)).build();
        Team t3 = Team.builder().name("Tech Titans").dateCreated(new Temporal.DateTime(new Date(), 0)).build();
        Amplify.API.mutate(ModelMutation.create(t1),
                response -> Log.i("Teams", "createTeams: Created"),
                fail -> Log.i("Teams", "createTeams: Failed"));
        Amplify.API.mutate(ModelMutation.create(t2),
                response -> Log.i("Teams", "createTeams: Created"),
                fail -> Log.i("Teams", "createTeams: Failed"));
        Amplify.API.mutate(ModelMutation.create(t3),
                response -> Log.i("Teams", "createTeams: Created"),
                fail -> Log.i("Teams", "createTeams: Failed"));
    }

}