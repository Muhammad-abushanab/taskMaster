package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.shanab.taskmaster.R;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    public static final String USER_NICKNAME_TAG="userNickname";

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

        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userNameField = findViewById(R.id.nicknameField);
                String userNameString = userNameField.getText().toString();
                prefrenceEditor.putString(USER_NICKNAME_TAG,userNameString);
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
}