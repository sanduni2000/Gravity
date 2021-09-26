package com.example.madfinal2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText subject = findViewById(R.id.et_subject);
        final EditText date = findViewById(R.id.et_date);
        final EditText time = findViewById(R.id.et_time);
        Button btn = findViewById(R.id.btn_add);
        Button btn_open = findViewById(R.id.btn_open);
        btn_open.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, RVActivity.class);
            startActivity(intent);
        });
        DAOtimetable dao = new DAOtimetable();
        timetable time_edit = (timetable)getIntent().getSerializableExtra("EDIT");
        if(time_edit!=null){
            btn.setText("Update");
            subject.setText(time_edit.getSubject());
            date.setText(time_edit.getDate());
            time.setText(time_edit.getTime());
            btn_open.setVisibility(View.GONE);
        }
        else {
            btn.setText("Add");
            btn_open.setVisibility(View.VISIBLE);
        }

        btn.setOnClickListener(v->{
            timetable timetab = new timetable(subject.getText().toString(),date.getText().toString(),time.getText().toString());
            if(time_edit==null) {
                dao.add(timetab).addOnSuccessListener(suc->{
                    Toast.makeText(this, "Record is inserted", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er -> {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            else {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("subject",subject.getText().toString());
                hashMap.put("date",date.getText().toString());
                hashMap.put("time",time.getText().toString());
                dao.update(time_edit.getKey(), hashMap).addOnSuccessListener(suc->{
                    Toast.makeText(this,"Record is updated",Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(er->{
                    Toast.makeText(this,""+er.getMessage(),Toast.LENGTH_SHORT).show();
                });
            }
        });


    }
}