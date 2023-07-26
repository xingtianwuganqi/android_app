package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class DataSaveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_save)

        val editText: EditText = findViewById(R.id.editTextInfo)

        // 读取
        val pref = getSharedPreferences("data",0)
        val name = pref.getString("name","").toString()
        editText.setText(name)
        Log.d("====",name)
        val saveBtn: Button = findViewById(R.id.saveInput)
        saveBtn.setOnClickListener {
            if (editText.text != null) {
                // 保存数据
                val shareP = getSharedPreferences("data",0).edit()
                shareP.putString("name", (editText.text ?: "haha").toString())
                shareP.putInt("age",18)
                shareP.putBoolean("isGirl", false)
                shareP.apply()
            }else{
                Toast.makeText(this, "text is empty", Toast.LENGTH_SHORT).show()
            }


        }

        // 第二种获取sharePre getPreferences(0)，会以activity为key
    }
}