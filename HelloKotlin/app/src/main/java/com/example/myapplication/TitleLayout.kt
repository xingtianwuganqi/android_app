package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class TitleLayout(context:Context, attrs:AttributeSet): LinearLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.title,this)
        val btnThree: Button = findViewById(R.id.button3)
        btnThree.setOnClickListener {
            val activity = context as Activity
            activity.finish()
        }

        val titleEdit: TextView = findViewById(R.id.textview3)
        titleEdit.setOnClickListener {
            Toast.makeText(context, "you click Edit button", Toast.LENGTH_SHORT).show()
        }
    }
}