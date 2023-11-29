package com.shanab.taskmaster.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    public static final String TAG = "addTask";
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private ImageView selectedImageView;
    private String filePath;

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
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        selectedImageView = findViewById(R.id.imageViewTaskDetails);
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSharedImage(intent);
            }
        }
        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    if (uri != null) {
                        try {
                            selectedImageView.setImageURI(uri);
                            filePath = getRealPathFromURI(uri);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("PHTACT", "Error setting image URI: " + e.getMessage());
                        }
                    } else {
                        Log.d("PHTACT", "No media selected");
                        filePath = null;
                    }
                });
    }

    private void handleSharedImage(Intent intent) {
        Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        Log.i(TAG, "imageUri is" + imageUri);
        if (imageUri != null) {
            selectedImageView.setImageURI(imageUri);
            filePath = getFilePathFromUri(imageUri);
            Log.i(TAG, "handleSharedImage: inside the if condition");
            Log.i(TAG, "filepath is " + filePath);
        }
        else filePath = null;
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
                    for (Team team : success.getData()) {
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
                failure -> {
                    teamFuture.complete(null);
                    Log.i(TAG, "Did not read contacts successfully");
                }
        );
    }

    private void setUpAddTaskBtn() {
        Button addTaskActivityBtn = (Button) findViewById(R.id.AddTaskBtn);
        List<Team> teams = null;

        try {
            teams = teamFuture.get();
            Log.d(TAG, "setUpAddTaskBtn() returned: " + teams.toString());
        } catch (InterruptedException ie) {
            Log.e(TAG, " InterruptedException while getting contacts");
        } catch (ExecutionException ee) {
            Log.e(TAG, " ExecutionException while getting contacts");
        }
        assert teams != null;
        List<Team> finalTeams = teams;
        addTaskActivityBtn.setOnClickListener(view -> {
            handleFileSelection(finalTeams);
        });


    }
    private void handleFileSelection(List<Team> finalTeams){
        Log.i(TAG, "FilePath is :" + filePath);
        Intent navigateToMainActivity = new Intent(this, MainActivity.class);
        Toast toast = Toast.makeText(this, "Task Added Successfully", Toast.LENGTH_SHORT);
        if (filePath != null && !filePath.isEmpty()) {
            File imageFile = new File(filePath);
            String key = "images/" + imageFile.getName();
            String title = ((EditText) findViewById(R.id.tasktitle)).getText().toString();
            String description = ((EditText) findViewById(R.id.taskDescription)).getText().toString();
            Team selectedTeam = finalTeams.stream().filter(t -> t.getName().equals(teamsSpinner.getSelectedItem().toString())).findAny().orElseThrow(RuntimeException::new);
            TaskModel newTask = TaskModel.builder()
                    .title(title)
                    .description(description)
                    .dateCreated(new Temporal.DateTime(new Date(), 0))
                    .state((com.amplifyframework.datastore.generated.model.TaskState) taskStateSpinner.getSelectedItem())
                    .team(selectedTeam)
                    .taskImageS3Key(key)
                    .teamName(selectedTeam.getName())
                    .build();
            Amplify.Storage.uploadFile(key,
                    imageFile,
                    result -> {
                        Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey());
                    },
                    error -> {
                        Log.e("MyAmplifyApp", "Upload failed", error);
                    });
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
        } else {
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
        }
    }
    public void onAddImageButtonClicked(View view) {
        if (pickMedia != null) {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        } else {
            Log.e("PhotoPicker", "pickMedia is null");
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) {
            return null;
        } else {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
    }
    private String getFilePathFromUri(Uri uri) {
        String filePath = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                File tempFile = createTempFile();
                if (tempFile != null) {
                    OutputStream outputStream = new FileOutputStream(tempFile);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    filePath = tempFile.getAbsolutePath();
                    outputStream.close();
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    private File createTempFile() {
        String fileName = "temp_image";
        File tempDir = getApplicationContext().getCacheDir();
        try {
            return File.createTempFile(fileName, null, tempDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}