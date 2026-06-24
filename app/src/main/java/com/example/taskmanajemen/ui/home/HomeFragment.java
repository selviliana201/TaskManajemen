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

import com.example.taskmanajemen.R;
import com.example.taskmanajemen.viewmodel.TaskViewModel;

public class HomeFragment extends Fragment {

    private TaskViewModel viewModel;
    private TextView tvWelcome, tvSummary;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvWelcome = view.findViewById(R.id.tvWelcome);
        tvSummary = view.findViewById(R.id.tvSummary);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        viewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            if (tasks != null && !tasks.isEmpty()) {
                int total = tasks.size();
                tvSummary.setText("Anda memiliki " + total + " tugas dalam daftar.");
            } else {
                tvSummary.setText("Belum ada tugas. Klik tombol + untuk menambah.");
            }
        });
    }
}