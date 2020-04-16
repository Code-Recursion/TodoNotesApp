package com.example.todonotesapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    val TAG = "DetailActivity"

    private lateinit var textViewTitle: TextView
    private lateinit var textViewDescription:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindViews()
        setupIntentData()
    }

    private fun setupIntentData() {

        val intent = intent          //getIntent

        val title = intent.getStringExtra(AppConstant.TITLE)

        val description = intent.getStringExtra(AppConstant.DESCRIPTION)

        //setText()

        textViewTitle.text = title
        textViewDescription.text = description
    }

    private fun bindViews() {
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewDescription = findViewById(R.id.textViewDescription)
    }
}