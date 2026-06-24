package com.example.taskmanajemen.repository;



import android.app.Application;


import androidx.lifecycle.LiveData;


import com.example.taskmanajemen.database.AppDatabase;

import com.example.taskmanajemen.database.TaskDao;

import com.example.taskmanajemen.database.TaskEntity;



import java.util.List;



public class TaskRepository {



    TaskDao dao;


    LiveData<List<TaskEntity>> data;




    public TaskRepository(Application app){


        AppDatabase db =
                AppDatabase.getDatabase(app);


        dao=db.taskDao();


        data=dao.getAll();



    }



    public LiveData<List<TaskEntity>> getData(){

        return data;

    }





    public void insert(TaskEntity task){


        new Thread(()->{


            dao.insert(task);


        }).start();



    }

    public void update(TaskEntity task){


        new Thread(()->{


            dao.update(task);



        }).start();


    }

    public void delete(TaskEntity task){


        new Thread(()->{


            dao.delete(task);


        }).start();



    }



}