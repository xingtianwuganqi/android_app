package com.rescue.flutter_720yun

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST

interface AppService {
    @POST("/api/v1/topiclist/")
    fun getTopicList(): Call<ResponseBody>
}

data class App(val code: Int, val message: String)