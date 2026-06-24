package com.example.taskmanajemen.ui.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanajemen.R;
import com.example.taskmanajemen.adapter.TaskAdapter;
import com.example.taskmanajemen.database.TaskEntity;
import com.example.taskmanajemen.viewmodel.TaskViewModel;

public class TasksFragment extends Fragment {

    RecyclerView recyclerView;
    TaskAdapter adapter;
    TaskViewModel viewModel;
    TextView total, pending, progress, done;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        total = view.findViewById(R.id.tvTotal);
        pending = view.findViewById(R.id.tvPending);
        progress = view.findViewById(R.id.tvProgress);
        done = view.findViewById(R.id.tvDone);

        recyclerView = view.findViewById(R.id.rvTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // Update status logic: Pending -> In Progress -> Done
        adapter = new TaskAdapter(task -> {
            String currentStatus = task.getStatus();
            if (currentStatus == null || currentStatus.equalsIgnoreCase("PENDING")) {
                task.setStatus("IN_PROGRESS");
            } else if (currentStatus.equalsIgnoreCase("IN_PROGRESS")) {
                task.setStatus("DONE");
            } else {
                // If it's already DONE, maybe we can reset it or do nothing
                // For now, let's just keep it as DONE
                return;
            }
            viewModel.update(task);
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
                    if (status.equalsIgnoreCase("PENDING")) p++;
                    else if (status.equalsIgnoreCase("IN_PROGRESS")) pr++;
                    else if (status.equalsIgnoreCase("DONE")) d++;
                }
            }

            total.setText(String.valueOf(t));
            pending.setText(String.valueOf(p));
            progress.setText(String.valueOf(pr));
            done.setText(String.valueOf(d));
        });
    }
}
