package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private val launcherCallback = ActivityResultCallback<ActivityResult> { result ->
        val code = result.resultCode
        val data = result.data
        println(code)
        println(data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            launcherCallback)
        println("哈哈哈")
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("title","value")
            intent.putExtra("date","23年7月1日")
//            resultLauncher.launch(intent)
            startActivity(intent)
        }
    }
}