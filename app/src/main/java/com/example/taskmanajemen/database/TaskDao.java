package com.example.taskmanajemen.database;



import androidx.lifecycle.LiveData;

import androidx.room.*;


import java.util.List;



@Dao

public interface TaskDao {



    @Query("SELECT * FROM tasks ORDER BY id DESC")

    LiveData<List<TaskEntity>> getAll();



    @Insert

    void insert(TaskEntity task);



    @Delete

    void delete(TaskEntity task);



    @Update

    void update(TaskEntity task);



}