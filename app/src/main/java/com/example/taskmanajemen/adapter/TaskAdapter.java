package com.example.taskmanajemen.adapter;


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



public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{



    private List<TaskEntity> list = new ArrayList<>();


    private OnDoneClick listener;



    public interface OnDoneClick{

        void done(TaskEntity task);

    }



    public TaskAdapter(OnDoneClick listener){

        this.listener = listener;

    }





    public void setData(List<TaskEntity> data){

        list=data;

        notifyDataSetChanged();

    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType){



        View view =
                LayoutInflater.from(parent.getContext())

                        .inflate(
                                R.layout.item_task,
                                parent,
                                false
                        );


        return new ViewHolder(view);


    }






    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position){


        TaskEntity task =
                list.get(position);



        holder.title.setText(task.getTitle());

        holder.deadline.setText(
                "Deadline : "
                        +task.getDeadline()
        );


        holder.status.setText(
                "Status : "
                        +task.getStatus()
        );


        holder.note.setText(
                "Note : "
                        +task.getNote()
        );




        holder.btnDone.setOnClickListener(v->{


            listener.done(task);



        });



    }





    @Override
    public int getItemCount(){

        return list.size();

    }





    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView title,deadline,status,note;

        Button btnDone;



        public ViewHolder(@NonNull View itemView){

            super(itemView);



            title =
                    itemView.findViewById(R.id.tvTaskTitle);


            deadline =
                    itemView.findViewById(R.id.tvDeadline);


            status =
                    itemView.findViewById(R.id.tvStatus);


            note =
                    itemView.findViewById(R.id.tvNote);


            btnDone =
                    itemView.findViewById(R.id.btnDone);



        }


    }


}