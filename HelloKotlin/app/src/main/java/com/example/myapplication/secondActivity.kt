package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class secondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        print("哈哈哈")
        var extIntent = intent.getStringExtra("title")
        Log.d("ListActivity",extIntent ?: "")
        var date = intent.getStringExtra("date")
        Log.d("listac",date ?: "")
        print(extIntent)
        print(date)
    }
}