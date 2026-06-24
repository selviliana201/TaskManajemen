package com.example.taskmanajemen.database;



import android.content.Context;


import androidx.room.Database;

import androidx.room.Room;

import androidx.room.RoomDatabase;



@Database(

        entities = {TaskEntity.class},

        version = 1

)



public abstract class AppDatabase
        extends RoomDatabase {



    public abstract TaskDao taskDao();




    private static AppDatabase instance;



    public static AppDatabase getDatabase(Context context){


        if(instance==null){


            instance =
                    Room.databaseBuilder(

                                    context.getApplicationContext(),

                                    AppDatabase.class,

                                    "task_database"

                            )

                            .build();


        }



        return instance;



    }



}