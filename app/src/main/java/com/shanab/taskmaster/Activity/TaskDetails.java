package com.shanab.taskmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskModel;
import com.amplifyframework.datastore.generated.model.TaskState;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shanab.taskmaster.database.TaskDatabase;
import com.shanab.taskmaster.R;

import java.io.File;
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
        findViewById(R.id.editBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }
        });
    }

    private void connectToDataBaseAndDeleteTask() {
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
                    String key = task.getTaskImageS3Key();
                    Log.d("KeyForMe", "run() returned: " + key);
                    if (key != null) {
                        ImageView image = findViewById(R.id.imageViewTaskDetails);
                        Amplify.Storage.downloadFile(
                                key,
                                new File(getApplicationContext().getFilesDir(), "downloaded_image.jpg"),
                                result -> {
                                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getPath());
                                    runOnUiThread(() -> {
                                        Bitmap bitmap = BitmapFactory.decodeFile(result.getFile().getPath());
                                        image.setImageBitmap(bitmap);
                                    });
                                },
                                error -> Log.e("MyAmplifyApp", "Download failed", error)
                        );
                    }
                }
            }
        });
    }

    private void showBottomDialog() {
        final BottomSheetDialog sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(R.layout.bottom_sheet_layout);
        Spinner bottomSheetSpinner = sheetDialog.findViewById(R.id.bottomSheetSpinner);
        bottomSheetSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TaskState.values()
        ));
        Button cancel = sheetDialog.findViewById(R.id.cancelBtn);
        Button save = sheetDialog.findViewById(R.id.saveBtnBottomSheet);
        sheetDialog.show();
        assert cancel != null;
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.cancel();
            }
        });
        Toast toast = Toast.makeText(this, "Updated", Toast.LENGTH_SHORT);
        Intent navigateToMainActivity = new Intent(this, MainActivity.class);
        assert save != null;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ((TextView) Objects.requireNonNull(sheetDialog.findViewById(R.id.titleEditBottomSheet))).getText().toString();
                String description = ((TextView) Objects.requireNonNull(sheetDialog.findViewById(R.id.descriptionEditBottomSheet))).getText().toString();

                Amplify.API.mutate(ModelMutation.update(task.copyOfBuilder().title(title).description(description).state((TaskState) bottomSheetSpinner.getSelectedItem()).build()),
                        response ->
                        {
                            Log.i("MyAmplifyApp", "Todo with id: " + response.getData().getId());
                            toast.show();
                            startActivity(navigateToMainActivity);
                        },
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );
            }
        });
    }

}