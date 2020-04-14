package com.example.todonotesapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    //var sharedPreference : SharedPreferences? = null
    //or

    lateinit var  sharedPreference : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSharedPreference();
        checkLoginStatus();
    }


    private fun setupSharedPreference() {
        sharedPreference = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun checkLoginStatus() {
        val isLoggedIn = sharedPreference.getBoolean(PrefConstant.IS_LOGGED_IN,false)
        if(isLoggedIn) {
            val intent = Intent(this@SplashActivity, MyNotesActivity::class.java)
            startActivity(intent)
        }else  {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
        }
    }


}