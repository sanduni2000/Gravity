package com.example.winish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Goal_Details extends AppCompatActivity {
    EditText aGoal, ddes_goal, ddate, ttime;
    Button Save, View, Update, Delete;
    DatabaseReference dbRef;
    Goal goalObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_details);


        Button Nxt = (Button) findViewById(R.id.Nxt);
        Nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Goal_Details.this, MainActivity.class);
                startActivity(intent2);
            }

        });


        aGoal = findViewById(R.id.aGoal);
        ddes_goal = findViewById(R.id.ddes_goal);
        ddate = findViewById(R.id.ddate);
        ttime = findViewById(R.id.ttime);
        Save = findViewById(R.id.Save);
        View = findViewById(R.id.View);
        Update = findViewById(R.id.Update);
        Delete = findViewById(R.id.Delete);

        goalObj = new Goal();



    }

    //method to clear all user inputs
    private void clearControls() {
        aGoal.setText("");
        ddes_goal.setText("");
        ddate.setText("");
        ttime.setText("");
    }

    public void Createdata(View view) {

        dbRef = FirebaseDatabase.getInstance().getReference().child("Goal");
        try {
            if (TextUtils.isEmpty(aGoal.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter a Goal", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(ddes_goal.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please describe goal", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(ddate.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter a date", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(ttime.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter a time(hr)", Toast.LENGTH_SHORT).show();


            else {

                goalObj.setaGoal(aGoal.getText().toString().trim());
                goalObj.setDdes_goal(ddes_goal.getText().toString().trim());
                goalObj.setDdate(ddate.getText().toString().trim());
                goalObj.setTtime(Integer.parseInt(ttime.getText().toString().trim()));


                //dbRef.push().setValue(goalObj);
                dbRef.child("g1").setValue(goalObj);

                Toast.makeText(getApplicationContext(), "Data insert successfully", Toast.LENGTH_SHORT).show();
                clearControls();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid hours", Toast.LENGTH_SHORT).show();
        }

    }
    public void Showdata(View view){
        dbRef = FirebaseDatabase.getInstance().getReference().child("Goal").child("g1");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    aGoal.setText(dataSnapshot.child("aGoal").getValue().toString());
                    ddes_goal.setText(dataSnapshot.child("ddes_goal").getValue().toString());
                    ddate.setText(dataSnapshot.child("ddate").getValue().toString());
                    ttime.setText(dataSnapshot.child("ttime").getValue().toString());

                }else {
                    Toast.makeText(getApplicationContext(), "No source to display", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


    public void Updatedata(View view){
        DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Goal");
        updRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("g1")) {
                    try{

                        goalObj.setaGoal(aGoal.getText().toString().trim());
                        goalObj.setDdes_goal(ddes_goal.getText().toString().trim());
                        goalObj.setDdate(ddate.getText().toString().trim());
                        goalObj.setTtime(Integer.parseInt(ttime.getText().toString().trim()));



                        dbRef = FirebaseDatabase.getInstance().getReference().child("Goal").child("g1");
                        dbRef.setValue(goalObj);
                        //ClearControl();

                        Toast.makeText(getApplicationContext(), "Data Updated successfully", Toast.LENGTH_SHORT).show();
                    }
                    catch (NumberFormatException e){
                        Toast.makeText(getApplicationContext(), "Invalid hours",Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(getApplicationContext(), "No source to Update", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
    public  void Deletedata(View view){
        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Goal");
        delRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot datasnapshot) {
                if(datasnapshot.hasChild("g1")){
                    dbRef = FirebaseDatabase.getInstance().getReference().child("Goal").child("g1");
                    dbRef.removeValue();
                    clearControls();
                    Toast.makeText(getApplicationContext(), "Data Deleted successfully", Toast.LENGTH_SHORT).show();

                }else
                    Toast.makeText(getApplicationContext(),"No Source to Delete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}
