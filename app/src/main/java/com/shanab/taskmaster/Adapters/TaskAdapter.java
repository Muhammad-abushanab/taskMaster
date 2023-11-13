package com.shanab.taskmaster.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.TaskModel;
import com.shanab.taskmaster.Activity.TaskDetails;
import com.shanab.taskmaster.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskListViewHolder> {
    List<TaskModel> tasks;
    Context callingActivity;

    public TaskAdapter(List<TaskModel> tasks, Context callingActivity) {
        this.tasks = tasks;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_list, parent, false);
        return new TaskListViewHolder(taskFragment);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        TextView taskFragmentTextView = (TextView) holder.itemView.findViewById(R.id.listFragmentTextView);
        TextView taskStateFragment_TextView = (TextView) holder.itemView.findViewById(R.id.taskStateFragment);
        TextView taskTeamName_TextView = (TextView) holder.itemView.findViewById(R.id.teamNameFragment);
        String taskTitle = tasks.get(position).getTitle();
        String taskState = tasks.get(position).getState().toString();
        String taskTeamName = tasks.get(position).getTeam().getName();
        taskFragmentTextView.setText(position  + ". " + taskTitle);
        taskStateFragment_TextView.setText(taskState);
        taskTeamName_TextView.setText(taskTeamName);
        View listViewHolder = holder.itemView;
        listViewHolder.setOnClickListener(view -> {
            Intent goToTaskFormIntent = new Intent(callingActivity, TaskDetails.class);
            goToTaskFormIntent.putExtra("TaskId", tasks.get(position).getId());
            callingActivity.startActivity(goToTaskFormIntent);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskListViewHolder extends RecyclerView.ViewHolder {
        public TaskListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
