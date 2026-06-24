package com.example.taskmanajemen;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskmanajemen.database.TaskEntity;
import com.example.taskmanajemen.viewmodel.TaskViewModel;

public class AddTaskActivity extends AppCompatActivity {

    EditText etTitle, etDeadline, etNote;
    Spinner spPriority, spStatus;
    Button btnSave;
    ImageButton btnBack;
    TextView tvHeaderTitle;
    TaskViewModel viewModel;
    
    private int taskId = -1; // -1 means create new

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
        btnBack = findViewById(R.id.btnBack);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        String[] priorities = {"LOW", "MEDIUM", "HIGH"};
        String[] statuses = {"To Do", "Progress", "Selesai"};

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, priorities);
        spPriority.setAdapter(priorityAdapter);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statuses);
        spStatus.setAdapter(statusAdapter);

        // Check if we are editing an existing task
        if (getIntent().hasExtra("TASK_ID")) {
            taskId = getIntent().getIntExtra("TASK_ID", -1);
            etTitle.setText(getIntent().getStringExtra("TASK_TITLE"));
            etDeadline.setText(getIntent().getStringExtra("TASK_DEADLINE"));
            etNote.setText(getIntent().getStringExtra("TASK_NOTE"));
            
            String priority = getIntent().getStringExtra("TASK_PRIORITY");
            if (priority != null) {
                for (int i = 0; i < priorities.length; i++) {
                    if (priorities[i].equals(priority)) {
                        spPriority.setSelection(i);
                        break;
                    }
                }
            }

            String status = getIntent().getStringExtra("TASK_STATUS");
            if (status != null) {
                if (status.equalsIgnoreCase("TO DO")) spStatus.setSelection(0);
                else if (status.equalsIgnoreCase("IN_PROGRESS")) spStatus.setSelection(1);
                else if (status.equalsIgnoreCase("DONE")) spStatus.setSelection(2);
            }

            tvHeaderTitle.setText(R.string.edit_task);
            btnSave.setText(R.string.update_task);
        }

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String deadline = etDeadline.getText().toString().trim();
            String note = etNote.getText().toString().trim();
            String priorityValue = spPriority.getSelectedItem().toString();
            String statusDisplay = spStatus.getSelectedItem().toString();
            
            String statusValue = "TO DO";
            if (statusDisplay.equals("Progress")) statusValue = "IN_PROGRESS";
            else if (statusDisplay.equals("Selesai")) statusValue = "DONE";

            if (title.isEmpty()) {
                Toast.makeText(this, "Judul tugas tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            TaskEntity task = new TaskEntity(title, deadline, priorityValue, statusValue, note);
            if (taskId != -1) {
                task.setId(taskId);
                viewModel.update(task);
                Toast.makeText(this, "Tugas berhasil diperbarui", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.insert(task);
                Toast.makeText(this, "Tugas berhasil disimpan", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}
