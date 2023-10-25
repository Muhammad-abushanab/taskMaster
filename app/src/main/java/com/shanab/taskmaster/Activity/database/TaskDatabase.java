package com.shanab.taskmaster.Activity.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.shanab.taskmaster.Activity.Dao.TaskDao;
import com.shanab.taskmaster.Activity.Models.TaskModel;

@Database(entities = {TaskModel.class},version = 1)
@TypeConverters({TaskDatabaseConverter.class})
public abstract class TaskDatabase extends RoomDatabase {
public abstract TaskDao taskDao();
}
