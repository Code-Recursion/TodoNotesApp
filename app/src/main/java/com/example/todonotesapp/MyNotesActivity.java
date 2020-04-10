package com.example.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyNotesActivity extends AppCompatActivity {

    String fullName;
    FloatingActionButton fabAddNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        fabAddNotes.findViewById(R.id.fabAddNotes);

        Intent intent = getIntent();
        intent.getStringExtra("full_name");
        fullName = intent.getStringExtra("full_name");

        fabAddNotes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("MyNotesActivity","clicked");
            }
        });
        getSupportActionBar().setTitle(fullName);
    }
}
