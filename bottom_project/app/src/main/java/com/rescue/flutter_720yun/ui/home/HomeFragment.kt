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
            context?.let { it1 ->
                adapter = TopicListAdapter(it1, it)
                recyclerView.adapter = adapter
            }
        })
        homeViewModel.fetchData()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}