package com.example.todonotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotesapp.ClickListeners.ItemClickListener
import com.example.todonotesapp.R
import com.example.todonotesapp.model.Notes

class NotesAdapter(private val list : List<Notes>, private val itemClickListener: ItemClickListener) :RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

//    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
        override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {

        val notes = list[position] // int java -> list.get(position)
        val title = notes.title
        val description = notes.description

        holder.textViewTitle.text = title
        holder.textViewDescription.text = description
        holder.itemView.setOnClickListener { itemClickListener.onClick(notes) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle : TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription : TextView = itemView.findViewById(R.id.textViewDescription)
    }

}