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

        // Initialize TextViews
        total = view.findViewById(R.id.tvTotal);
        pending = view.findViewById(R.id.tvPending);
        progress = view.findViewById(R.id.tvProgress);
        done = view.findViewById(R.id.tvDone);

        recyclerView = view.findViewById(R.id.rvTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        adapter = new TaskAdapter(task -> {
            task.setStatus("DONE");
            viewModel.update(task);
        });

        recyclerView.setAdapter(adapter);

        // Observer with statistics logic
        viewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            adapter.setData(tasks);

            int t = tasks.size();
            int p = 0;
            int pr = 0;
            int d = 0;

            for (TaskEntity x : tasks) {
                String status = x.getStatus();
                if (status != null) {
                    if (status.equals("PENDING")) p++;
                    else if (status.equals("IN_PROGRESS")) pr++;
                    else if (status.equals("DONE")) d++;
                }
            }

            total.setText(t + "\nTotal");
            pending.setText(p + "\nPending");
            progress.setText(pr + "\nProgress");
            done.setText(d + "\nDone");
        });
    }
}