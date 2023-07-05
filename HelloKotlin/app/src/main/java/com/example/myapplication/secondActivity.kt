package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val intent = Intent()
            intent.putExtra("name","secondActivity")
            setResult(RESULT_OK,intent)
            finish()
        }
        println("哈哈哈2")
        var extIntent = intent.getStringExtra("title")
        Log.d("SecondActivity",extIntent ?: "")
        var date = intent.getStringExtra("date")
        Log.d("SecondActivity",date ?: "")
        println(extIntent)
        println(date)
    }
}