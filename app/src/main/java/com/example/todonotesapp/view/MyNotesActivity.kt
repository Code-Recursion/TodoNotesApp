package com.example.todonotesapp.view
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.todonotesapp.utils.AppConstant
import com.example.todonotesapp.ClickListeners.ItemClickListener
import com.example.todonotesapp.NotesApp
import com.example.todonotesapp.utils.PrefConstant
import com.example.todonotesapp.R
import com.example.todonotesapp.adapter.NotesAdapter
import com.example.todonotesapp.db.Notes
import com.example.todonotesapp.workmanager.MyWorker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.TimeUnit

class MyNotesActivity : AppCompatActivity() {
    val TAG = "MyNotesActivity"
    lateinit var fullName : String
    lateinit var fabAddNotes: FloatingActionButton
    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerViewNotes: RecyclerView
    var listNotes = ArrayList<Notes>()

    val ADD_NOTES_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        bindView()
        setUpSharedPreference()
        getIntentData()
        getDataFromDataBase()
        setUpRecyclerView()
        setupWorkManager()

        fabAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                // setUpDialogBox()
                val intent = Intent(this@MyNotesActivity, AddNotesActivity::class.java)
                startActivityForResult(intent, ADD_NOTES_CODE)
            }
        })

        // Log.d("MyNotesActivity",fullName);
        supportActionBar?.title = fullName

    }

    private fun setupWorkManager() {
        val constraint = Constraints.Builder()
                .build()
        val request = PeriodicWorkRequest
                .Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()

      WorkManager.getInstance().enqueue(request)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_NOTES_CODE) {
            val title = data?.getStringExtra(AppConstant.TITLE)
            val description = data?.getStringExtra(AppConstant.DESCRIPTION)
            val imagePath = data?.getStringExtra(AppConstant.IMAGE_PATH)

            val notes = Notes(title = title!!, description = description!!, imagePath = imagePath!!, isTaskCompleted = false)
            addNotesToDb(notes)
            listNotes.add(notes)
            recyclerViewNotes.adapter?.notifyItemChanged(listNotes.size - 1)
        }
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
        val itemClickListener = object : ItemClickListener {

            override fun onClick(notes: Notes) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE, notes.title)
                intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                startActivity(intent)
            }
            override fun onUpdate(notes: Notes) {
                //update the value is checked or not
                Log.d(TAG,notes.isTaskCompleted.toString())
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }


        }

        val notesAdapter = NotesAdapter(listNotes, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = notesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.blog) {
            //Log.d(TAG, "Clicked on Blog")
            val intent = Intent(this, BlogActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
