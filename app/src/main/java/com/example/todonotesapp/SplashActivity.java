package com.example.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupSharedPreference();
        checkLoginStatus();
    }

    private void setupSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
    }

    private void checkLoginStatus() {
        // if user is logged in -> MyNotesAcivity
        //else
        //user -> LoginActivity
        
        boolean isLoggedIn = sharedPreferences.getBoolean(PrefConstant.IS_LOGGED_IN,false);
        if(isLoggedIn) {
            // notes activity

            Intent intent = new Intent(SplashActivity.this,MyNotesActivity.class);
            startActivity(intent);
        } else {
            // log
            // inActivity
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        
    }
}
