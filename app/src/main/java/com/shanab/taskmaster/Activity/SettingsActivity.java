package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskState;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.material.snackbar.Snackbar;
import com.shanab.taskmaster.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class SettingsActivity extends AppCompatActivity {
    public static final String USER_NICKNAME_TAG="userNickname";
    public static final String USER_TEAM_TAG = "userTeamName";

    private CompletableFuture<List<Team>> teamFuture = new CompletableFuture<>();
    private Spinner teamsSpinner = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Settings");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefrenceEditor = settings.edit();
        setupSpinners();
        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userNameField = findViewById(R.id.nicknameField);
                String userNameString = userNameField.getText().toString();
                String userTeam = teamsSpinner.getSelectedItem().toString();
                prefrenceEditor.putString(USER_NICKNAME_TAG,userNameString);
                prefrenceEditor.putString(USER_TEAM_TAG,userTeam);
                prefrenceEditor.apply();
                Snackbar.make(findViewById(R.id.settings_Activity),"Settings Saved",Snackbar.LENGTH_SHORT).show();
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
    private void setupSpinners() {
        teamsSpinner = (Spinner) findViewById(R.id.teamSpinnerInSettings);
        Amplify.API.query(
                ModelQuery.list(Team.class),
                success ->
                {
                    Log.i("SettingsActivity", "Read Team Successfully");
                    ArrayList<String> teamName = new ArrayList<>();
                    ArrayList<Team> teams = new ArrayList<>();
                    for(Team team: success.getData()){
                        teams.add(team);
                        teamName.add(team.getName());
                        Log.d("SettingsActivity", "setupSpinners() returned: " + team.getName());
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
                    Log.i("SettingsActivity", "Did not read contacts successfully");
                }
        );
    }
}