package com.rescue.flutter_720yun

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppService {
    @POST("api/v1/topiclist/")
    fun getTopicList(@Body data: Data): Call<ResponseBody>


    @GET("repos/square/okhttp/issues")
    fun getIssuesList(): Call<ResponseBody>
}

data class App(val code: Int, val message: String)

data class Data(val page: Int, val size: Int)