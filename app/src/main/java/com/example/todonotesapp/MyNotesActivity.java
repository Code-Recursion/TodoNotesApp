package com.example.todonotesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyNotesActivity extends AppCompatActivity {

    String fullName;
    FloatingActionButton fabAddNotes;
    TextView textTitle, textDescription;
    SharedPreferences sharedPreferences;
    String TAG = "MyNotesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        bindView();
        setupSharedPreference();
        getIntentDate();

        fabAddNotes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setupDialogBox();

            }
        });
        getSupportActionBar().setTitle(fullName);
    }

    private void setupSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
    }

    private void getIntentDate() {
        Intent intent = getIntent();
        fullName = intent.getStringExtra(AppConstant.FULL_NAME);
        if(TextUtils.isEmpty(fullName)) {
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME,"");
        }
    }

    private void bindView() {

        fabAddNotes = findViewById(R.id.fabAddNotes);
        textTitle = findViewById(R.id.TextViewTitle);
        textDescription = findViewById(R.id.TextViewDescription);

    }

    private void setupDialogBox() {
        View view = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.add_notes_dialog_layout,null);
        final EditText editTextTitle = view.findViewById(R.id.editTextTitle);
        final EditText editTextDescription = view.findViewById(R.id.editTextDescription);

        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String desc = editTextDescription.getText().toString();

                textTitle.setText(title);
                textDescription.setText(desc);
                dialog.hide();
            }
        });
    dialog.show();
    }
}
