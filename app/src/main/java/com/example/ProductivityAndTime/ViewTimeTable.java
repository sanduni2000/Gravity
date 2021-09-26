package com.example.ProductivityAndTime;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ViewTimeTable extends AppCompatActivity {
    FloatingActionButton tasks;
    private FirebaseAuth firebaseAuth;

    RecyclerView mrecyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    Task task;

    FirestoreRecyclerAdapter<firebasemodel , TaskViewHolder> taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time_table);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("tasks").orderBy("subject", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<firebasemodel> allTasks = new FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query, firebasemodel.class).build();

        taskAdapter = new FirestoreRecyclerAdapter<firebasemodel, TaskViewHolder>(allTasks) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i, @NonNull firebasemodel firebasemodel) {
                taskViewHolder.taskSubject.setText(firebasemodel.getSubject());

               ImageView popupbutton=taskViewHolder.itemView.findViewById(R.id.menupopbutton);

                int colourcode = getRandomColor();
                taskViewHolder.mTask.setBackgroundColor(taskViewHolder.itemView.getResources().getColor(colourcode, null));

                String Date1 = firebasemodel.getYear() + "/" + firebasemodel.getMonth() + "/" + firebasemodel.getDay();
                taskViewHolder.taskDate.setText(Date1);
                String time1 = firebasemodel.getHour() + ":" + firebasemodel.getMinute();
                taskViewHolder.taskTime.setText(time1);
                taskViewHolder.taskDescription.setText(firebasemodel.getDescription());

                String docID = taskAdapter.getSnapshots().getSnapshot(i).getId();

                taskViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(getApplicationContext(), "This is clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), taskdetails.class);

                        int year = firebasemodel.getYear();
                        int month = firebasemodel.getMonth();
                        int day = firebasemodel.getDay();
                        int hour = firebasemodel.getHour();
                        int minute = firebasemodel.getMinute();

                        String date = year + "/" + month + "/" + day;
                        String time = hour + ":" + minute;

                        intent.putExtra("subject", firebasemodel.getSubject());
                        intent.putExtra("date", date);
                        intent.putExtra("time", time);
                        intent.putExtra("description", firebasemodel.getDescription());
                        intent.putExtra("taskID", docID);
                        v.getContext().startActivity(intent);

                    }
                });

                popupbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                        popupMenu.setGravity(Gravity.END);
                        popupMenu.getMenu().add("edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                int year = firebasemodel.getYear();
                                int month = firebasemodel.getMonth();
                                int day = firebasemodel.getDay();
                                int hour = firebasemodel.getHour();
                                int minute = firebasemodel.getMinute();

                                String date = year + "/" + month + "/" + day;
                                String time = hour + ":" + minute;

                                Intent intent = new Intent(view.getContext(), edittaskactivity.class);
                                intent.putExtra("date", date);
                                intent.putExtra("time", time);
                                intent.putExtra("description", firebasemodel.getDescription());
                                intent.putExtra("taskID", docID);
                                view.getContext().startActivity(intent);
                                return false;
                            }
                        });

                        popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                //Toast.makeText(view.getContext(), "This task is deleted",Toast.LENGTH_SHORT).show();
                                DocumentReference documentReference = firebaseFirestore.collection("tasks").document(docID);
                                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(view.getContext(), "This task is deleted",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(view.getContext(), "Failed to delete",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });
            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_time_table, parent, false);
                return new TaskViewHolder(view);
            }
        };

        mrecyclerView = findViewById(R.id.recyclerview);
        mrecyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mrecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mrecyclerView.setAdapter(taskAdapter);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        private TextView taskDate;
        private TextView taskTime;
        private TextView taskSubject;
        private TextView taskDescription;
        LinearLayout mTask;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskDate = itemView.findViewById(R.id.taskDate);
            taskTime = itemView.findViewById(R.id.taskTime);
            taskSubject = itemView.findViewById(R.id.taskSubject);
            taskDescription = itemView.findViewById(R.id.taskDescription);
            mTask = itemView.findViewById(R.id.mTask);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        taskAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(taskAdapter != null){
            taskAdapter.stopListening();
        }
    }

    private int getRandomColor(){
        List<Integer> colourcode = new ArrayList<>();
        colourcode.add(R.color.grey);
        colourcode.add(R.color.pink);
        colourcode.add(R.color.lightgreen);
        colourcode.add(R.color.skyblue);
        colourcode.add(R.color.coloe1);
        colourcode.add(R.color.color2);
        colourcode.add(R.color.color3);

        Random random = new Random();
        int number = random.nextInt(colourcode.size());
        return colourcode.get(number);
    }
}