package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class SecondActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            // 反向传值
            val intent = Intent()
            intent.putExtra("key","secondActivity")
            setResult(RESULT_OK,intent)
            finish()
        }
        // 接收传值
        var extIntent = intent.getStringExtra("title")
        Log.d("SecondActivity",extIntent ?: "")
        var date = intent.getStringExtra("date")
        Log.d("SecondActivity",date ?: "")

        // textView
        val textView: TextView = findViewById(R.id.textView)
        Log.d("==", textView.text.toString())

        val editText: EditText = findViewById(R.id.editView)

        val imageView: ImageView = findViewById(R.id.imageView)
        var progress: ProgressBar = findViewById(R.id.progress)
        // textView
        val changeB: Button = findViewById(R.id.changeBtn)
        changeB.setOnClickListener {
            textView.text = "change Click"

            val editT = editText.text.toString()
            Toast.makeText(this, editT, Toast.LENGTH_SHORT).show()

            imageView.setImageResource(R.drawable.playstore)

            if (progress.visibility == View.VISIBLE) {
                progress.visibility = View.GONE
            }else{
                progress.visibility = View.VISIBLE
            }

            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("提醒")
            dialog.setMessage("这是一个提醒")
            dialog.setCancelable(true)
            dialog.setPositiveButton("ok") { dialog,which ->
                Log.d("-","点击了ok")
            }
            dialog.setNegativeButton("cancel") { dialog, which ->
                Log.d("-","点击了cancel")
            }
            dialog.show()
        }

    }
}