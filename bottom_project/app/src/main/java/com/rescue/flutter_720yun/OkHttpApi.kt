package com.rescue.flutter_720yun

import android.util.Log
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit


class OkHttpApi: HttpApi{

    companion object {
        private const val TAG = "OkHttpApi"
    }

    private var baseUrl = "https://test.rxswift.cn"

    private val logging = HttpLoggingInterceptor()

    private val mClient = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)// 完整请求超时市场，从发起到接收返回数据， 默认值0，不限定
        .connectTimeout(30, TimeUnit.SECONDS)// 与服务器建立连接时长，默认10s
        .readTimeout(30, TimeUnit.SECONDS)// 读取服务器返回的时长
        .writeTimeout(30, TimeUnit.SECONDS)// 向服务器写入数据的时长，默认10s
        .retryOnConnectionFailure(true)
        .followRedirects(false)
        .addInterceptor(logging)
        .build()

    override fun get(param: Map<String, Any>, path: String, callback: HttpCallback) {
        val url = "$baseUrl$path"
        val urlBuilder = HttpUrl.Builder()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY) // Log the request and response bodies
        param.forEach { (key, value) ->
            urlBuilder.addEncodedQueryParameter(key, value.toString())
        }
        val request = Request.Builder()
            .get()
            .url(url)
            .build()
        mClient.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body)
            }
        })
    }

    override fun post(body: Any, path: String, callback: HttpCallback) {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY) // Log the request and response bodies
        val url = "$baseUrl$path"
        val request = Request.Builder()
            .post(Gson().toJson(body).toRequestBody())
            .url(url)
            .build()
        Log.d("Tag", url)
        Log.d("TAG", Gson().toJson(body).toRequestBody().toString())
        mClient.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body)
            }
        })
    }

}