package com.example.todonotesapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(){

        lateinit var editTextFullName: EditText
        lateinit var editTextUserName:EditText

        lateinit var buttonLogin:Button
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor:SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindViews()
        setupSharedPreference()
    }

    private fun setupSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun bindViews() {
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUserName = findViewById(R.id.editTextUsername)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener(clickAction)
    }
}