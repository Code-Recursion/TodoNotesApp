package com.example.todonotesapp.ClickListeners

import com.example.todonotesapp.model.Notes

interface ItemClickListener {
    fun onClick(notes : Notes)
}