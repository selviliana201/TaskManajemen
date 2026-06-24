package com.example.taskmanajemen.viewmodel;



import android.app.Application;


import androidx.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.LiveData;



import com.example.taskmanajemen.database.TaskEntity;

import com.example.taskmanajemen.repository.TaskRepository;



import java.util.List;



public class TaskViewModel
        extends AndroidViewModel {



    TaskRepository repository;



    LiveData<List<TaskEntity>> tasks;




    public TaskViewModel(
            @NonNull Application application
    ){


        super(application);



        repository =
                new TaskRepository(application);



        tasks =
                repository.getData();


    }



    public LiveData<List<TaskEntity>> getTasks(){


        return tasks;


    }

    public void update(TaskEntity task){


        repository.update(task);


    }


    public void insert(TaskEntity task){


        repository.insert(task);


    }




    public void delete(TaskEntity task){


        repository.delete(task);


    }



}