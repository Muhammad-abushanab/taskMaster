package com.shanab.taskmaster.Activity.States;

import androidx.annotation.NonNull;

public enum TaskState {
    NEW("New"),
    ASSIGNED("Assigned"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String taskState;

    TaskState(String taskState){
        this.taskState = taskState;
    }

    public String getTaskState() {
        return taskState;
    }
    public static TaskState fromString(String possibleTaskState){
        for(TaskState state : TaskState.values()){
            if (state.taskState.equals(possibleTaskState)){
                return state;
            }
        }

        return null;
    }

    @NonNull
    @Override
    public String toString(){
        if(taskState == null){
            return "";
        }
        return taskState;
    }
}
