package com.rescue.flutter_720yun.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rescue.flutter_720yun.databinding.FragmentHomeBinding
import com.rescue.flutter_720yun.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var homeAdapter: HomeListAdapter
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
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}