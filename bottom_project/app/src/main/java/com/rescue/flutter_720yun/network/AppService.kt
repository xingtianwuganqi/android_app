package com.rescue.flutter_720yun.network

import com.rescue.flutter_720yun.models.BaseListResp
import com.rescue.flutter_720yun.models.HomeListModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AppService {
//    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("api/v1/topiclist/")
    fun getTopicList(@Field("page") page: Int, @Field("size") size: Int,@Field("order") order: Int): Call<BaseListResp<HomeListModel>>

    @GET("repos/square/okhttp/issues")
    fun getIssuesList(): Call<ResponseBody>

    @POST("v1/test/post/test")
    fun postTest(): Call<ResponseBody>
}

data class App(val code: Int, val message: String)

data class Data(val page: Int, val size: Int,val order: Int)