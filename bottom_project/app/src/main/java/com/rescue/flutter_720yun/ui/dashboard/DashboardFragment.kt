package com.rescue.flutter_720yun.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.rescue.flutter_720yun.R
import com.rescue.flutter_720yun.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var number: Int = 0

    // 创建livedata
    val liveData = MutableLiveData<String>()
    val liveMapData: LiveData<Pair<Int, String>> = liveData.map {
        Pair<Int, String>(it.hashCode(), it)
    }

    val liveMapData2: LiveData<String> = liveData.map {
        "liveMapData2 ${it.takeLast(6)}"
    }

    val liveTwo = MutableLiveData<String>().apply {
        value = "666"
    }

    val liveOne = MutableLiveData<String>().apply {
        value = "999"
    }


    val multiLiveData: MediatorLiveData<String> = MediatorLiveData()

    // switchMap 通过条件，控制选择数据元666 or 888
    val switchLiveData: LiveData<String> = liveMapData.switchMap {
        if (it.second.takeLast(1).toInt() % 2 == 0) liveTwo else liveOne
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}