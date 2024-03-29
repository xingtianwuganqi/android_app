package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
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

    // 通知
    lateinit var timeChangeRecever: TimeChangeRecever

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

        val broadBtn: Button = findViewById(R.id.broadcostActivity)
        broadBtn.setOnClickListener {
            val intent = Intent(this, BroadcaseReceiverActivity::class.java)
            startActivityed.launch(intent)
        }

        // 通知动态注册
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.custom_broad")
        timeChangeRecever = TimeChangeRecever()
        registerReceiver(timeChangeRecever, intentFilter)

        val dataSave: Button = findViewById(R.id.dataSave)
        dataSave.setOnClickListener {
            val intent = Intent(this,DataSaveActivity::class.java)
            startActivityed.launch(intent)
        }

        val titleLayoutBtn:Button = findViewById(R.id.titleLayout)
        titleLayoutBtn.setOnClickListener {
            val intent = Intent(this,TitleLayoutActivity::class.java)
            startActivityed.launch(intent)
        }

        val tabbarBtn: Button = findViewById(R.id.tabbar)
        tabbarBtn.setOnClickListener {
            val intent = Intent(this,TabbarActivity::class.java)
            startActivityed.launch(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeChangeRecever)
        Log.d("----","main activity ondestroy")
    }

    // 通知
    inner class TimeChangeRecever: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Toast.makeText(p0,"哈哈哈还",Toast.LENGTH_SHORT).show()
        }
    }
}