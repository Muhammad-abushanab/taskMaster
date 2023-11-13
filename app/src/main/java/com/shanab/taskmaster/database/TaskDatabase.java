package com.shanab.taskmaster.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.shanab.taskmaster.Dao.TaskDao;
import com.shanab.taskmaster.Models.TaskModel;

@Database(entities = {TaskModel.class},version = 1)
@TypeConverters({TaskDatabaseConverter.class})
public abstract class TaskDatabase extends RoomDatabase {
public abstract TaskDao taskDao();
}
