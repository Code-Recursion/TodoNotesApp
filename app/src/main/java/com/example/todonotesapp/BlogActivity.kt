package com.example.todonotesapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotesapp.R
import com.example.todonotesapp.adapter.BlogAdapter
import com.androidnetworking.error.ANError
import com.androidnetworking.AndroidNetworking
import android.util.Log
import com.androidnetworking.common.Priority
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.todonotesapp.model.JsonResponse


class BlogActivity : AppCompatActivity() {
    lateinit var recyclerViewBlogs:RecyclerView
    val TAG = "BlogActivity"
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_blog)
        bindViews()
        getBlogs()
    }

    private fun getBlogs() {
        AndroidNetworking.get("http://www.mocky.io/v2/5926ce9d11000096006ccb30")

                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(JsonResponse::class.java, object:ParsedRequestListener<JsonResponse>{
                    override fun onResponse(response: JsonResponse?) {
                        setupRecyclerView(response)
                    }

                    override fun onError(anError: ANError?) {
                        Log.d(TAG, anError?.localizedMessage)
                    }

                })
    }

    private fun bindViews() {
        recyclerViewBlogs = findViewById(R.id.recyclerViewBlogs)
    }

    private fun setupRecyclerView(response: JsonResponse?) {
        val blogAdapter = BlogAdapter(response!!.data)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewBlogs.layoutManager = linearLayoutManager
        recyclerViewBlogs.adapter = blogAdapter
    }

}