package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class ListActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.list_layout)

        var extIntent = intent.getStringExtra("title")
        Log.d("ListActivity",extIntent ?: "")
        var date = intent.getStringExtra("date")
        Log.d("listac",date ?: "")
        print(extIntent)
        print(date)

    }
}