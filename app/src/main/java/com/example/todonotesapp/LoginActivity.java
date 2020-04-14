package com.example.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PathEffect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editTextFullName, editTextUsername;
    Button buttonLogin;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //key     Values
    //name -> Ajay
    //id   -> 12

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextUsername = findViewById(R.id.editTextFullName);
        buttonLogin = findViewById(R.id.buttonLogin);

        setupSharedPreferences();


        View.OnClickListener clickAction = new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String fullName = editTextFullName.getText().toString();
                String userName = editTextUsername.getText().toString();

                if( !TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(userName)) {
                    Intent intent = new Intent(LoginActivity.this, MyNotesActivity.class);
                    intent.putExtra(AppConstant.INSTANCE.getFULL_NAME(), fullName);
                    startActivity(intent);

                    //login
                    saveLoginStatus();
                    saveFullName(fullName);

                } else {
                    Toast.makeText(LoginActivity.this,"Full name and username can't be empty",Toast.LENGTH_SHORT).show();
                }
            }
        };
        buttonLogin.setOnClickListener(clickAction);
    }

    private void saveFullName(String fullName) {
        editor = sharedPreferences.edit();
        editor.putString(PrefConstant.INSTANCE.getFULL_NAME(),fullName);
        editor.apply();
    }

    private void saveLoginStatus() {
        editor = sharedPreferences.edit();
        editor.putBoolean(PrefConstant.INSTANCE.getIS_LOGGED_IN(),true);
        editor.apply();
    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.INSTANCE.getSHARED_PREFERENCE_NAME(),MODE_PRIVATE);
    }
}
