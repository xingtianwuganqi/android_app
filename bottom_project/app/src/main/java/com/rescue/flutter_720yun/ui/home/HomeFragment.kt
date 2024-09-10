package com.rescue.flutter_720yun.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rescue.flutter_720yun.AppService
import com.rescue.flutter_720yun.Data
import com.rescue.flutter_720yun.HttpCallback
import com.rescue.flutter_720yun.OkHttpApi
import com.rescue.flutter_720yun.ServiceCreator
import com.rescue.flutter_720yun.databinding.FragmentHomeBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val httpApi = OkHttpApi()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val button: Button = binding.testButton
        button.setOnClickListener {
            networking()
            networkingTest()
            networkingSecond()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun networking() {
        val data = Data(1, 20)
        httpApi.post(data,"/api/v1/topiclist/", object: HttpCallback {
            override fun onSuccess(data: ResponseBody?) {
                Log.d("TAG", "data code")

                if (data != null) {
                    Log.d("TAG", data.string())
                }

            }

            override fun onFailed(error: Any?) {
                Log.d("TAG", "data code")

                Log.i("TAG", error.toString())
            }
        })
    }

    private fun networkingTest() {
        Log.i("TAG", "fuck body 2")
        val appService = ServiceCreator.create<AppService>()
        val data = Data(1, 20)
        appService.getTopicList(data).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
                Log.i("TAG", "Data code")
                val data = p1.body()
                if (p1.body() == null) {
                    Log.d("TAG", " mmp mmp mmp ")
                }else{
                    Log.d("TAG","fuck")
                }

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

    private fun networkingSecond() {
        Log.d("TAG", "Fuck Second")
        val retrofit = Retrofit.Builder()
            .baseUrl("http://test.rxswift.cn/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
        val data = Data(1, 20)
        appService.getTopicList(data).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
                Log.d("TAG", "Fuck success")
                if (p1.body() == null) {
                    Log.d("TAG", "mmp")
                }else{
                    Log.d("TAG","fuck")
                }
                p1.body()?.let { Log.d("Tag", it.string()) }
            }

            override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                Log.d("TAG", "Fuck fail")
                p1.message?.let { Log.d("Tag", it) }
            }
        })
    }}