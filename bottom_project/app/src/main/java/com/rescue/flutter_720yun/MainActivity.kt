package com.rescue.flutter_720yun

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.gson.Gson
import com.rescue.flutter_720yun.databinding.ActivityMainBinding
import okhttp3.OkHttp

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val httpApi = OkHttpApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        Log.i("TAG", "fuck body 1")
        networking()
    }

    private fun networking() {
        Log.i("TAG", "fuck body 2")
        httpApi.post(mapOf<String, Any>(),"/api/v1/topiclist/", object: HttpCallback{
            override fun onSuccess(data: Any?) {
                Log.i("TAG", "fuck body")
                println("response: ${data.toString()}")

            }

            override fun onFailed(error: Any?) {
                Log.i("TAG", "fuck fail")
                Log.i("TAG", error.toString())
            }
        })
    }
}