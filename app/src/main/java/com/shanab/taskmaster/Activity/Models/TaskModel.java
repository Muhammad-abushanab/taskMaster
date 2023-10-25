package com.shanab.taskmaster.Activity.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.shanab.taskmaster.Activity.States.TaskState;

@Entity
public class TaskModel {
    @PrimaryKey(autoGenerate = true)
    public long id;
    private String title;
    private String body;
    private TaskState state;

    public TaskModel(String title, String body, TaskState state) {
        this.body = body;
        this.title = title;
        this.state = state;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }
}
