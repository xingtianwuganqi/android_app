package com.rescue.flutter_720yun.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rescue.flutter_720yun.AppService
import com.rescue.flutter_720yun.ServiceCreator
import com.rescue.flutter_720yun.models.homemodel.BaseListResp
import com.rescue.flutter_720yun.models.homemodel.HomeListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _models = MutableLiveData<List<HomeListModel>>()
    val models: LiveData<List<HomeListModel>> = _models

    private fun getList(): List<Int> {
        val list = List(10) {
            it
        }
        return list
    }

    fun fetchData() {
        val service = ServiceCreator.create<AppService>()
        service.getTopicList(1, 20, 0).enqueue(object : Callback<BaseListResp<HomeListModel>>{
            override fun onResponse(
                p0: Call<BaseListResp<HomeListModel>>,
                p1: Response<BaseListResp<HomeListModel>>
            ) {
                if (p1.isSuccessful && p1.body() != null) {
                    _models.value = p1.body()!!.data
                }else{

                }
            }

            override fun onFailure(p0: Call<BaseListResp<HomeListModel>>, p1: Throwable) {

            }
        })
    }
}