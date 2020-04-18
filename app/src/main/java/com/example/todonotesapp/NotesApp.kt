package com.example.todonotesapp

import android.app.Application
import com.example.todonotesapp.db.NotesDatabase

class NotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
    fun getNotesDb():NotesDatabase{
        return NotesDatabase.getInstance(this)
    }
}