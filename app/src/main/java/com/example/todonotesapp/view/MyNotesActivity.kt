package com.example.todonotesapp.view
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotesapp.utils.AppConstant
import com.example.todonotesapp.ClickListeners.ItemClickListener
import com.example.todonotesapp.NotesApp
import com.example.todonotesapp.utils.PrefConstant
import com.example.todonotesapp.R
import com.example.todonotesapp.adapter.NotesAdapter
import com.example.todonotesapp.db.Notes
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyNotesActivity : AppCompatActivity() {

    lateinit var fullName : String
    lateinit var fabAddNotes: FloatingActionButton
    lateinit var sharedPreferences: SharedPreferences
    val TAG = "MyNotesActivity"
    lateinit var recyclerViewNotes: RecyclerView
    var listNotes = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        bindView()
        setUpSharedPreference()
        getIntentData()
        getDataFromDataBase()
        setUpRecyclerView()

        fabAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                setUpDialogBox()
            }
        })

        // Log.d("MyNotesActivity",fullName);
        supportActionBar?.title = fullName

    }

    private fun getDataFromDataBase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
//        Log.d(TAG, notesDao.getAll().size.toString())
        listNotes.addAll(notesDao.getAll())
    }

    private fun setUpSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun getIntentData() {
        val intent = intent
        fullName = intent.getStringExtra(AppConstant.FULL_NAME)?:""
        if (fullName.isEmpty()) {
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME,"")?:""
//            Log.d(TAG, fullName)
        }
    }

    private fun bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
    }

    private fun setUpDialogBox() {
        val view = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialog_layout, null)

        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
        //Dialog
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true) // make the dialog to be cancellable
                .create()
        buttonSubmit.setOnClickListener {
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val notes = Notes(title = title, description = description)
                listNotes.add(notes)
                addNotesToDb(notes)// add notes to db
            } else {
                Toast.makeText(this@MyNotesActivity, "Title or Description can't be Empty!", Toast.LENGTH_SHORT).show()
            }
            dialog.hide()
//            setUpRecyclerView()
        }
        dialog.show()
    }

    private fun addNotesToDb(notes: Notes) {
        // insert notes in DB
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    private fun setUpRecyclerView() {
        val itemClickListeners = object : ItemClickListener {
            override fun onUpdate(notes: Notes) {
                //update the value is checked or not
                Log.d(TAG,notes.isTaskCompleted.toString())
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }

            override fun onClick(notes: Notes) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE, notes.title)
                intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                startActivity(intent)
            }
        }

        val notesAdapter = NotesAdapter(listNotes, itemClickListeners)
        val linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = notesAdapter
    }
}
