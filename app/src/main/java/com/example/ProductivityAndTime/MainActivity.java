package com.example.ProductivityAndTime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, NewTask.class);
        startActivity(intent);
    }
    public void viewTasks(View view) {
        Intent intent = new Intent(this, ViewTimeTable.class);
        startActivity(intent);
    }
}