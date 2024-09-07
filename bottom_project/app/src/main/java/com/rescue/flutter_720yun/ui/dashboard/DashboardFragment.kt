package com.rescue.flutter_720yun.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.rescue.flutter_720yun.BlankFragment
import com.rescue.flutter_720yun.R
import com.rescue.flutter_720yun.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(), BlankFragment.OnButtonClickListener {

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

        // 将自定的fragment添加到fragment上
        val blankFragment = BlankFragment()
        childFragmentManager.beginTransaction().add(R.id.frame_view, blankFragment).commit()



        liveData.observe(viewLifecycleOwner, Observer {
            Log.i("TAG", "--mliveData--触发了观察 数据发送变化--$it")
        })

        liveMapData.observe(viewLifecycleOwner, Observer {
            Log.i("TAG", "liveData map 转换后的数据发送变化 -- $it")
        })

        liveMapData2.observe(viewLifecycleOwner, Observer {
            Log.i("TAG", "liveData map2 转换后的数据发送变化 -- $it")
        })

        switchLiveData.observe(viewLifecycleOwner, Observer {
            Log.i("TAG", "switchLiveData 转换后的数据发送变化 -- $it")
        })

        multiLiveData.addSource(liveTwo, Observer {
            Log.i("TAG", "liveOne --$it")
            multiLiveData.value = it
        })

        multiLiveData.addSource(liveOne, Observer {
            Log.i("TAG", "liveOne --$it")
            multiLiveData.value = it
        })

        multiLiveData.observe(viewLifecycleOwner, Observer {
            Log.i("TAG", "multiLiveData 转换后的数据发送变化 -- $it")
        })

        return root
    }

    override fun onButtonClicked() {
        number += 1
        liveData.value = number.toString()
        liveOne.value = (number + 3).toString()
        liveTwo.value = (number + 4).toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}