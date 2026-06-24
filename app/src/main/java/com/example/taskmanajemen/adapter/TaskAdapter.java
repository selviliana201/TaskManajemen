package com.example.taskmanajemen.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanajemen.R;
import com.example.taskmanajemen.database.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<TaskEntity> list = new ArrayList<>();
    private OnStatusUpdateListener listener;

    public interface OnStatusUpdateListener {
        void onUpdate(TaskEntity task);
    }

    public TaskAdapter(OnStatusUpdateListener listener) {
        this.listener = listener;
    }

    public void setData(List<TaskEntity> data) {
        list = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskEntity task = list.get(position);

        holder.title.setText(task.getTitle());
        holder.deadline.setText("Deadline: " + (task.getDeadline() != null ? task.getDeadline() : "-"));
        holder.note.setText(task.getNote());

        String status = task.getStatus() != null ? task.getStatus() : "PENDING";
        
        // Display user-friendly status and colors
        if (status.equalsIgnoreCase("PENDING")) {
            holder.status.setText("To Do");
            holder.status.setBackgroundResource(R.drawable.circle_yellow);
        } else if (status.equalsIgnoreCase("IN_PROGRESS")) {
            holder.status.setText("Progress");
            holder.status.setBackgroundResource(R.drawable.circle_blue);
        } else if (status.equalsIgnoreCase("DONE")) {
            holder.status.setText("Selesai");
            holder.status.setBackgroundResource(R.drawable.circle_green);
        }

        holder.btnDone.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUpdate(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, deadline, status, note;
        Button btnDone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTaskTitle);
            deadline = itemView.findViewById(R.id.tvDeadline);
            status = itemView.findViewById(R.id.tvStatus);
            note = itemView.findViewById(R.id.tvNote);
            btnDone = itemView.findViewById(R.id.btnDone);
        }
    }
}
