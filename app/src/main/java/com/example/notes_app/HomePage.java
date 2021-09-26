package com.example.notes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    /*private Button homebutton1;
    private Button homebutton2;
    private Button homebutton3;*/
    private Button homebutton4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        homebutton4 = (Button) findViewById(R.id.button4);
        homebutton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity();
            }
        });

        /*homebutton2 = (Button) findViewById(R.id.button3);
        homebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity();
            }
        });

        homebutton3 = (Button) findViewById(R.id.button);
        homebutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageName(Waruna)();
            }
        });

        homebutton1 = (Button) findViewById(R.id.button6);
        homebutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageName(Anupa)();
            }
        });*/
    }

    public void MainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    /*public void MainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void pageName(Waruna)(){
        Intent intent = new Intent(this,pageName(Waruna).class);
        startActivity(intent);
    }

    public void pageName(Anupa)(){
        Intent intent = new Intent(this,pageName(Anupa).class);
        startActivity(intent);
    }*/
}