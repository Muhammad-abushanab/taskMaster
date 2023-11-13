package com.shanab.taskmaster.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.shanab.taskmaster.Models.TaskModel;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    public void insertTask(TaskModel taskModel);

    @Query("select * from TaskModel")
    public List<TaskModel> findAll();

    @Query("select * from TaskModel ORDER BY title ASC")
    public List<TaskModel> findAllSortedByName();

    @Query("select * from TaskModel where id = :id")
    TaskModel findByAnId(long id);

    @Query("select * from TaskModel where title = :title")
    TaskModel findByTitle(String title);

    @Query("DELETE from TaskModel where title = :title")
    public void deleteTaskByTitle(String title);

    @Query("DELETE FROM TaskModel where id=:id")
    public void deleteTaskById(long id);
}
