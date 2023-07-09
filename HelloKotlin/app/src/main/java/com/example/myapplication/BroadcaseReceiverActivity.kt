package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class BroadcaseReceiverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcase_receiver)

        val button: Button = findViewById(R.id.sendBroadcost)
        button.setOnClickListener {
            // 发送通知
            val intent = Intent("com.example.custom_broad")
            intent.setPackage(packageName)
            sendBroadcast(intent)
        }
    }
}