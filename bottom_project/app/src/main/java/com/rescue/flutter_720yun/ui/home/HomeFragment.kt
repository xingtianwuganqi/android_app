package com.rescue.flutter_720yun.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rescue.flutter_720yun.HomeListAdapter
import com.rescue.flutter_720yun.HomeLoadStateAdapter
import com.rescue.flutter_720yun.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
//    private val httpApi = OkHttpApi()
//    private lateinit var adapter: TopicListAdapter
    private lateinit var homeAdapter: HomeListAdapter
//    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
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

        context?.let {
            homeAdapter = HomeListAdapter(it)
            recyclerView.adapter = homeAdapter.withLoadStateFooter(
                footer = HomeLoadStateAdapter {
                    homeAdapter.retry()
                }
            )
        }


        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            homeAdapter.refresh()
        }

        lifecycleScope.launch {
            homeViewModel.items.collectLatest {
                homeAdapter.submitData(it)
                swipeRefreshLayout.isRefreshing = false
            }
        }

        homeAdapter.addLoadStateListener { loadState ->
            when(loadState.refresh) {
                is LoadState.Loading -> {
                    swipeRefreshLayout.isRefreshing = true
                }

                is LoadState.NotLoading ->{
                    swipeRefreshLayout.isRefreshing = false
                }

                is LoadState.Error -> {
                    val errorState = loadState.refresh as LoadState.Error
                    Log.e("Paging error", "Error: ${errorState.error.message}")
                }
            }
        }

//        homeViewModel.models.observe(viewLifecycleOwner, Observer {
//            context?.let { it1 ->
//                adapter = TopicListAdapter(it1, it)
//                recyclerView.adapter = adapter
//            }
//        })
//        homeViewModel.fetchData(true)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}