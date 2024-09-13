package com.rescue.flutter_720yun.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.rescue.flutter_720yun.AppService
import com.rescue.flutter_720yun.Data
import com.rescue.flutter_720yun.HttpCallback
import com.rescue.flutter_720yun.OkHttpApi
import com.rescue.flutter_720yun.ServiceCreator
import com.rescue.flutter_720yun.ServiceSecond
import com.rescue.flutter_720yun.TopicListAdapter
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
    private lateinit var adapter: TopicListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)

        homeViewModel.models.observe(viewLifecycleOwner, Observer {
            adapter = TopicListAdapter(it)
            recyclerView.adapter = adapter
        })
        homeViewModel.fetchData()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


//    private fun networking() {
//        val param = Data(1, 20, 0)
//        httpApi.post(param,"api/v1/topiclist/", object: HttpCallback {
//            override fun onSuccess(data: okhttp3.Response) {
//                if (data != null) {
//                    Log.d("Tag", "body is not empty")
//                    data.body?.let {
//                        Log.d("TAG", it.string())
//                    }
//                }else{
//                    Log.d("Tag", "body is Empty")
//                }
//            }
//
//            override fun onFailed(error: Any?) {
//                Log.d("TAG", "data code")
//
//                Log.i("TAG", error.toString())
//            }
//        })
//    }
//
//    private fun networkingTest() {
//        Log.i("TAG", "fuck body 2")
//        val appService = ServiceCreator.create<AppService>()
//        appService.getTopicList(1,20,1).enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
//                Log.i("TAG", "Data code")
//                if (p1.body() == null) {
//                    Log.d("TAG", " mmp mmp mmp ")
//                }else{
//                    Log.d("TAG","fuck")
//                }
//
//                if (p1.body() != null) {
//                    Log.i("TAG", "Data code")
//                    Log.d("TAG", p1.body()!!.string())
//                }
//            }
//
//            override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
//                Log.i("TAG", "fuck fail")
//                Log.d("TAG", p1.toString())
//
//            }
//        })
//    }
//
//    private fun networkingSecond() {
//        Log.d("TAG", "Fuck Second")
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://test.rxswift.cn/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val appService = retrofit.create(AppService::class.java)
//        appService.getTopicList(1, 20, 0).enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
//                Log.d("TAG", "Fuck success")
//                if (p1 == null) {
//                    Log.d("TAG", "mmp")
//                }else{
//                    Log.d("TAG","fuck")
//                    print(p1)
//                }
//                p1.body()?.let { Log.d("Tag", it.string()) }
//            }
//
//            override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
//                Log.d("TAG", "Fuck fail")
//                p1.message?.let { Log.d("Tag", it) }
//            }
//        })
//    }
//
//    private fun networkingThird() {
//        val service = ServiceSecond.create<AppService>()
//        service.postTest().enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
//                if (p1.body() == null) {
//                    Log.d("TAG", "body empty")
//                } else {
//                    Log.d("TAG", "not empty")
//                    p1.body()?.let {
//
//                        try {
//                            val gson = GsonBuilder().setPrettyPrinting().create()
//                            val jsonElement = gson.fromJson(it.string(), Any::class.java)
//                            val prettyJson = gson.toJson(jsonElement)
//
//                            // 打印格式化的 JSON
//                            Log.d("TAG", "Formatted JSON Response: \n$prettyJson")
//                        } catch (e: Exception) {
//
//                        }
//
//                    }
//
//                }
//            }
//
//
//            override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
//                Log.d("TAG", "Fail fuck")
//            }
//        })
//
//
//    }

}