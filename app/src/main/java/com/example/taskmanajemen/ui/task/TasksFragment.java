package com.example.taskmanajemen.ui.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanajemen.AddTaskActivity;
import com.example.taskmanajemen.R;
import com.example.taskmanajemen.adapter.TaskAdapter;
import com.example.taskmanajemen.database.TaskEntity;
import com.example.taskmanajemen.viewmodel.TaskViewModel;

public class TasksFragment extends Fragment {

    RecyclerView recyclerView;
    TaskAdapter adapter;
    TaskViewModel viewModel;
    TextView total, todo, progress, done;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        total = view.findViewById(R.id.tvTotal);
        todo = view.findViewById(R.id.tvToDo);
        progress = view.findViewById(R.id.tvProgress);
        done = view.findViewById(R.id.tvDone);

        recyclerView = view.findViewById(R.id.rvTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // Inisialisasi adapter dengan listener untuk Update Status, Hapus, dan Edit
        adapter = new TaskAdapter(new TaskAdapter.OnTaskActionListener() {
            @Override
            public void onUpdateStatus(TaskEntity task) {
                String currentStatus = task.getStatus();
                if (currentStatus == null || currentStatus.equalsIgnoreCase("TO DO") || currentStatus.equalsIgnoreCase("PENDING")) {
                    task.setStatus("IN_PROGRESS");
                } else if (currentStatus.equalsIgnoreCase("IN_PROGRESS")) {
                    task.setStatus("DONE");
                } else {
                    return;
                }
                viewModel.update(task);
                Toast.makeText(getContext(), "Status diperbarui", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteTask(TaskEntity task) {
                new AlertDialog.Builder(requireContext())
                        .setTitle(R.string.delete_task)
                        .setMessage(R.string.delete_confirm)
                        .setPositiveButton(R.string.delete, (dialog, which) -> {
                            viewModel.delete(task);
                            Toast.makeText(getContext(), "Tugas berhasil dihapus", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            }

            @Override
            public void onEditTask(TaskEntity task) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                intent.putExtra("TASK_ID", task.getId());
                intent.putExtra("TASK_TITLE", task.getTitle());
                intent.putExtra("TASK_DEADLINE", task.getDeadline());
                intent.putExtra("TASK_NOTE", task.getNote());
                intent.putExtra("TASK_PRIORITY", task.getPriority());
                intent.putExtra("TASK_STATUS", task.getStatus());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        viewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            adapter.setData(tasks);

            int t = tasks.size();
            int p = 0;
            int pr = 0;
            int d = 0;

            for (TaskEntity x : tasks) {
                String status = x.getStatus();
                if (status != null) {
                    if (status.equalsIgnoreCase("TO DO") || status.equalsIgnoreCase("PENDING")) p++;
                    else if (status.equalsIgnoreCase("IN_PROGRESS")) pr++;
                    else if (status.equalsIgnoreCase("DONE") || status.equalsIgnoreCase("SUBMITTED")) d++;
                } else {
                    p++;
                }
            }

            total.setText(String.valueOf(t));
            todo.setText(String.valueOf(p));
            progress.setText(String.valueOf(pr));
            done.setText(String.valueOf(d));
        });
    }
}
