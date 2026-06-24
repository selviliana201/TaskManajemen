package com.example.taskmanajemen;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskmanajemen.database.TaskEntity;
import com.example.taskmanajemen.viewmodel.TaskViewModel;

public class AddTaskActivity extends AppCompatActivity {

    EditText etTitle, etDeadline, etNote;
    Spinner spPriority, spStatus;
    Button btnSave;
    TaskViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTitle = findViewById(R.id.etTaskTitle);
        etDeadline = findViewById(R.id.etDeadline);
        etNote = findViewById(R.id.etNote);
        spPriority = findViewById(R.id.spPriority);
        spStatus = findViewById(R.id.spStatus);
        btnSave = findViewById(R.id.btnSave);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        String[] priorities = {"LOW", "MEDIUM", "HIGH"};
        // Menggunakan label yang lebih user-friendly
        String[] statuses = {"To Do", "Progress", "Selesai"};

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, priorities);
        spPriority.setAdapter(priorityAdapter);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statuses);
        spStatus.setAdapter(statusAdapter);

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String deadline = etDeadline.getText().toString().trim();
            String note = etNote.getText().toString().trim();
            String priorityValue = spPriority.getSelectedItem().toString();
            String statusDisplay = spStatus.getSelectedItem().toString();
            
            // Konversi kembali ke format internal database
            String statusValue = "PENDING";
            if (statusDisplay.equals("Progress")) statusValue = "IN_PROGRESS";
            else if (statusDisplay.equals("Selesai")) statusValue = "DONE";

            if (title.isEmpty()) {
                Toast.makeText(this, "Judul tugas tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            TaskEntity task = new TaskEntity(title, deadline, priorityValue, statusValue, note);
            viewModel.insert(task);

            Toast.makeText(this, "Tugas berhasil disimpan", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
