package com.example.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyNotesActivity extends AppCompatActivity {

    String fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        Intent intent = getIntent();
        intent.getStringExtra("full_name");
        fullName = intent.getStringExtra("full_name");

        //        Log.d("MyNotesActivity",fullName);

        getSupportActionBar().setTitle(fullName);
    }
}
