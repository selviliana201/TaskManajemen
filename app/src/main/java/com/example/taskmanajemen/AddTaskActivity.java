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



    EditText etTitle;
    EditText etDeadline;
    EditText etNote;


    Spinner spPriority;
    Spinner spStatus;


    Button btnSave;



    TaskViewModel viewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_task);




        etTitle =
                findViewById(R.id.etTaskTitle);



        etDeadline =
                findViewById(R.id.etDeadline);



        etNote =
                findViewById(R.id.etNote);



        spPriority =
                findViewById(R.id.spPriority);



        spStatus =
                findViewById(R.id.spStatus);



        btnSave =
                findViewById(R.id.btnSave);





        viewModel =
                new ViewModelProvider(this)

                        .get(TaskViewModel.class);





        String[] priority = {


                "LOW",

                "MEDIUM",

                "HIGH"

        };




        String[] status = {


                "PENDING",

                "IN_PROGRESS",

                "DONE"

        };





        ArrayAdapter<String> adapter1 =


                new ArrayAdapter<>(

                        this,

                        android.R.layout.simple_spinner_dropdown_item,

                        priority

                );



        spPriority.setAdapter(adapter1);






        ArrayAdapter<String> adapter2 =


                new ArrayAdapter<>(

                        this,

                        android.R.layout.simple_spinner_dropdown_item,

                        status

                );



        spStatus.setAdapter(adapter2);







        btnSave.setOnClickListener(v->{



            String title =
                    etTitle.getText().toString();



            String deadline =
                    etDeadline.getText().toString();



            String note =
                    etNote.getText().toString();



            String priorityValue =
                    spPriority.getSelectedItem().toString();




            String statusValue =
                    spStatus.getSelectedItem().toString();





            if(title.isEmpty()){


                Toast.makeText(

                        this,

                        "Judul belum diisi",

                        Toast.LENGTH_SHORT

                ).show();


                return;


            }





            TaskEntity task =


                    new TaskEntity(

                            title,

                            deadline,

                            priorityValue,

                            statusValue,

                            note

                    );





            viewModel.insert(task);




            Toast.makeText(

                    this,

                    "Tugas berhasil ditambahkan",

                    Toast.LENGTH_SHORT

            ).show();



            finish();




        });



    }




}