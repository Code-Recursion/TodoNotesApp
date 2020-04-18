package com.example.todonotesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesapp.utils.AppConstant
import com.example.todonotesapp.utils.PrefConstant
import com.example.todonotesapp.R

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextFullName: EditText
    private lateinit var editTextUsername: EditText
    private lateinit var buttonLogin: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUsername = findViewById(R.id.editTextUsername)
        buttonLogin = findViewById(R.id.buttonLogin)
        setUpSharedPreferences()

        val clickAction = View.OnClickListener {
            val fullName = editTextFullName.text.toString()
            val userName = editTextUsername.text.toString()

            if (fullName.isNotEmpty() && userName.isNotEmpty()) {
                val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                intent.putExtra(AppConstant.FULL_NAME, fullName)
                startActivity(intent)
                saveLoginStatus()
                saveFullName(fullName)
                //                Log.d("LoginActivity", "clicked on button");
            } else {
                Toast.makeText(this@LoginActivity, "Full Name or Username can't be Empty!", Toast.LENGTH_SHORT).show()
            }
        }
        buttonLogin.setOnClickListener(clickAction)
    }

    private fun saveFullName(fullName: String) {
        this.editor = sharedPreferences.edit()
        this.editor.putString(PrefConstant.FULL_NAME, fullName)
        this.editor.apply()
    }

    private fun saveLoginStatus() {
        this.editor = sharedPreferences.edit()
        this.editor.putBoolean(PrefConstant.IS_LOGGED_IN, true)
        this.editor.apply()
    }

    private fun setUpSharedPreferences() {
        this.sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
}