package com.example.ProductivityAndTime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NewTask extends AppCompatActivity {
    TextView tvTimer;
    int tHour, tMinute;

    TextView tvDate;
    EditText etDate;
    DatePickerDialog.OnDateSetListener setListener;
    int year, month, day;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    EditText subject, description;


    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        tvTimer = findViewById(R.id.tv_TimePicker);
        tvTimer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        NewTask.this,
                        new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute){
                                tHour = hour;
                                tMinute = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, hour, minute);
                                tvTimer.setText(DateFormat.format("hh:mm aa", calendar));
                            }
                        },12, 0, false
                );
                timePickerDialog.updateTime(tHour, tMinute);
                timePickerDialog.show();
            }
        });
        tvDate = findViewById(R.id.tv_DateBanner);
        etDate = findViewById(R.id.et_date);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        etDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        etDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        subject = findViewById(R.id.et_subject);
        description = findViewById(R.id.et_description);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //firebaseUser = FirebaseUser.getInstance().getCurrentUser();

        final Button createTask = findViewById(R.id.bt_taskAdded);

        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pSubject = subject.getText().toString();
                String pDescription = description.getText().toString();
                if(pSubject.isEmpty() || pDescription.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    DocumentReference documentReference = firebaseFirestore.collection("tasks").document();
                    Task t = new Task(year, ++month, day, tHour, tMinute, pSubject, pDescription);

                    documentReference.set(t).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Task added successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NewTask.this, MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to add task", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(NewTask.this, MainActivity.class));
                        }
                    });
                }
            }
        });
    }
}