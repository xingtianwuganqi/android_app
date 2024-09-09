package com.rescue.flutter_720yun

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rescue.flutter_720yun.databinding.ActivityMainBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
//        networking()
        networkingTest()
    }

    private fun networking() {
        httpApi.post(mapOf<String, Any>(),"/api/v1/topiclist/", object: HttpCallback{
            override fun onSuccess(data: Any?) {
                println("response: ${data.toString()}")

            }

            override fun onFailed(error: Any?) {
                Log.i("TAG", error.toString())
            }
        })
    }

    private fun networkingTest() {
        Log.i("TAG", "fuck body 2")
        val appService = ServiceCreator.create<AppService>()
        appService.getTopicList().enqueue(object : Callback<ResponseBody>{
            override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
                Log.i("TAG", "Data code")
                val data = p1.body()

                Log.d("TAG", data.toString())

                if (data != null) {
                    Log.i("TAG", "Data code")
                    Log.d("TAG", data.toString())
                }
            }

            override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                Log.i("TAG", "fuck fail")
                Log.d("TAG", p1.toString())

            }
        })
    }

}


