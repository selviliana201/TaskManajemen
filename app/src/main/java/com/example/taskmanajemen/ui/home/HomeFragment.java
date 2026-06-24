package com.example.taskmanajemen.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskmanajemen.MainActivity;
import com.example.taskmanajemen.R;
import com.example.taskmanajemen.database.TaskEntity;
import com.example.taskmanajemen.viewmodel.TaskViewModel;
import com.google.android.material.button.MaterialButton;

public class HomeFragment extends Fragment {

    private TaskViewModel viewModel;
    private TextView tvSummary;
    private MaterialButton btnViewTasks;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvSummary = view.findViewById(R.id.tvSummary);
        btnViewTasks = view.findViewById(R.id.btnViewTasks);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        viewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            if (tasks != null && !tasks.isEmpty()) {
                int pending = 0;
                for (TaskEntity task : tasks) {
                    if (task.getStatus() == null || task.getStatus().equalsIgnoreCase("TO DO") || task.getStatus().equalsIgnoreCase("IN_PROGRESS")) {
                        pending++;
                    }
                }
                tvSummary.setText("Kamu memiliki " + pending + " tugas tertunda.");
            } else {
                tvSummary.setText("Belum ada tugas. Klik tombol + untuk menambah.");
            }
        });

        btnViewTasks.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToTasks();
            }
        });
    }
}
