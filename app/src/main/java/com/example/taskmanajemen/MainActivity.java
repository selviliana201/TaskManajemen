package com.example.taskmanajemen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.taskmanajemen.ui.home.HomeFragment;
import com.example.taskmanajemen.ui.task.TasksFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        add = findViewById(R.id.fabAddTask);

        load(new HomeFragment());

        bottomNavigation.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.navigation_home){
                load(new HomeFragment());
                return true;
            }

            if(item.getItemId() == R.id.navigation_tasks){
                load(new TasksFragment());
                return true;
            }

            return false;
        });

        add.setOnClickListener(v -> {
            startActivity(new Intent(this, AddTaskActivity.class));
        });
    }

    void load(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
