package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : BaseActivity() {

    // 正向传值
    private val startActivityed = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
        if (it.resultCode == RESULT_OK) {
            it.data?.let {
                val value = it.getStringExtra("key")
                Log.d("===",value ?: "1")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 正向传值
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("title","value")
            intent.putExtra("date","23年7月1日")
            startActivityed.launch(intent)
        }

        val three_button: Button = findViewById(R.id.threeBtn)
        three_button.setOnClickListener {
            val intent = Intent(this, ThreeActivity::class.java)
            startActivityed.launch(intent)
        }

        val listDetail: Button = findViewById(R.id.listDetail)
        listDetail.setOnClickListener {
            val intent = Intent(this, ListDetailActivity::class.java)
            startActivityed.launch(intent)
        }

        val recycView: Button = findViewById(R.id.recycBtn)
        recycView.setOnClickListener {
            val intent = Intent(this, RecyclerActivity::class.java)
            startActivityed.launch(intent)
        }
    }
}