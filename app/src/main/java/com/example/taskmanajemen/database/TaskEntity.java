package com.example.taskmanajemen.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String deadline;
    private String priority;
    private String status;
    private String note;

    public TaskEntity(
            String title,
            String deadline,
            String priority,
            String status,
            String note
    ){
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
        this.note = note;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public String getDeadline(){
        return deadline;
    }

    public String getPriority(){
        return priority;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote(){
        return note;
    }
}
