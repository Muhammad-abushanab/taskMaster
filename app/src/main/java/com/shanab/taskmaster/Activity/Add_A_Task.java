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
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.TaskModel;
import com.amplifyframework.datastore.generated.model.TaskState;
import com.amplifyframework.datastore.generated.model.Team;
import com.shanab.taskmaster.database.TaskDatabase;
import com.shanab.taskmaster.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Add_A_Task extends AppCompatActivity {
    TaskDatabase taskDatabase;
    CompletableFuture<List<Team>> teamFuture = new CompletableFuture<>();
    Spinner taskStateSpinner = null;
    Spinner teamsSpinner = null;
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
        setupSpinners();
        setUpAddTaskBtn();
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

    private void setupSpinners() {
        taskStateSpinner = (Spinner) findViewById(R.id.TaskStateSpinner);
        taskStateSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TaskState.values()
        ));
        teamsSpinner = (Spinner) findViewById(R.id.TeamSpinner);
        Amplify.API.query(
                ModelQuery.list(Team.class),
                success ->
                {
                    Log.i(TAG, "Read Team Successfully");
                    ArrayList<String> teamName = new ArrayList<>();
                    ArrayList<Team> teams = new ArrayList<>();
                    for(Team team: success.getData()){
                        teams.add(team);
                        teamName.add(team.getName());
                        Log.d(TAG, "setupSpinners() returned: " + team.getName());
                    }
                    teamFuture.complete(teams);
                    runOnUiThread(() ->
                    {
                        teamsSpinner.setAdapter(new ArrayAdapter<>(
                                this,
                                (android.R.layout.simple_spinner_item),
                                teamName
                        ));
                    });
                },
                failure-> {
                    teamFuture.complete(null);
                    Log.i(TAG, "Did not read contacts successfully");
                }
        );
    }
    private void setUpAddTaskBtn(){
        Button addTaskActivityBtn = (Button) findViewById(R.id.AddTaskBtn);
        Toast toast = Toast.makeText(this, "Task Added Successfully", Toast.LENGTH_SHORT);
        Intent navigateToMainActivity = new Intent(this, MainActivity.class);


        List<Team> teams=null;

        try {
            teams=teamFuture.get();
            Log.d(TAG, "setUpAddTaskBtn() returned: " + teams.toString());
        }catch (InterruptedException ie){
            Log.e(TAG, " InterruptedException while getting contacts");
        }catch (ExecutionException ee){
            Log.e(TAG," ExecutionException while getting contacts");
        }
        assert teams != null;
        List<Team> finalTeams = teams;
        addTaskActivityBtn.setOnClickListener(view -> {
            String title = ((EditText) findViewById(R.id.tasktitle)).getText().toString();
            String description = ((EditText) findViewById(R.id.taskDescription)).getText().toString();
            Team selectedTeam = finalTeams.stream().filter(t -> t.getName().equals(teamsSpinner.getSelectedItem().toString())).findAny().orElseThrow(RuntimeException::new);
            TaskModel newTask = TaskModel.builder()
                    .title(title)
                    .description(description)
                    .dateCreated(new Temporal.DateTime(new Date(), 0))
                    .state((com.amplifyframework.datastore.generated.model.TaskState) taskStateSpinner.getSelectedItem())
                    .team(selectedTeam)
                    .teamName(selectedTeam.getName())
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
}