package com.example.todonotesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todonotesapp.ClickListeners.ItemClickListener;
import com.example.todonotesapp.adapter.NotesAdapter;
import com.example.todonotesapp.model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyNotesActivity extends AppCompatActivity {

    String fullName;
    FloatingActionButton fabAddNotes;
   // TextView textTitle, textDescription;
    SharedPreferences sharedPreferences;
    String TAG = "MyNotesActivity";
    RecyclerView recyclerViewNotes;
    ArrayList<Notes> notesList = new ArrayList<>();

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
        sharedPreferences = getSharedPreferences(PrefConstant.INSTANCE.getSHARED_PREFERENCE_NAME(),MODE_PRIVATE);
    }

    private void getIntentDate() {
        Intent intent = getIntent();
        fullName = intent.getStringExtra(AppConstant.INSTANCE.getFULL_NAME());
        if(TextUtils.isEmpty(fullName)) {
            fullName = sharedPreferences.getString(PrefConstant.INSTANCE.getFULL_NAME(),"");

        }
    }

    private void bindView() {

        fabAddNotes = findViewById(R.id.fabAddNotes);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
    }

    private void setupDialogBox() {
        View view = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.add_notes_dialog_layout,null);
        final EditText editTextTitle = view.findViewById(R.id.editTextTitle);
        final EditText editTextDescription = view.findViewById(R.id.editTextDescription);

        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .create();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();

                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {

                    Notes notes = new Notes();
                    notes.setTitle(title);
                    notes.setDescription(description);
                    notesList.add(notes);
                } else {
                    Toast.makeText(MyNotesActivity.this, "Title or Description can't be Empty",Toast.LENGTH_SHORT).show();
                }

//              Log.d(TAG, String.valueOf(notesList.size()));

                setupRecyclerView();

                dialog.hide();
            }
        });
    dialog.show();
    }

    private void setupRecyclerView() {

        //interface
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(Notes notes) {
//                    Log.d(TAG, notes.getTitle());

                Intent intent = new Intent(MyNotesActivity.this, DetailActivity.class);
                intent.putExtra(AppConstant.INSTANCE.getTITLE(),notes.getTitle());
                intent.putExtra(AppConstant.INSTANCE.getDESCRIPTION(),notes.getDescription());
                startActivity(intent);
            }

        };

        NotesAdapter notesAdapter = new NotesAdapter(notesList, itemClickListener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyNotesActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewNotes.setLayoutManager(linearLayoutManager);
        recyclerViewNotes.setAdapter(notesAdapter);
    }
}
