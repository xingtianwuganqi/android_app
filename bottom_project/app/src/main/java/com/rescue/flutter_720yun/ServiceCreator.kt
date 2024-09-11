package com.rescue.flutter_720yun
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceCreator {
    private const val BASE_URL = "http://test.rxswift.cn/"

    // 创建 Interceptor
    private val headerInterceptor = Interceptor { chain ->
        val request: Request = chain.request()
            .newBuilder()
//            .addHeader("Authorization", "Bearer your_token")  // 添加公共请求头
            .addHeader("Content-Type", "application/x-www-form-urlencoded")    // 添加Content-Type
            .build()
        chain.proceed(request)
    }

    // 创建 OkHttpClient 并添加 Interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)// 完整请求超时市场，从发起到接收返回数据， 默认值0，不限定
        .connectTimeout(30, TimeUnit.SECONDS)// 与服务器建立连接时长，默认10s
        .readTimeout(30, TimeUnit.SECONDS)// 读取服务器返回的时长
        .writeTimeout(30, TimeUnit.SECONDS)// 向服务器写入数据的时长，默认10s
        .retryOnConnectionFailure(true)
        .followRedirects(false)
        .addInterceptor(headerInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
//        .client(okHttpClient)
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}


object ServiceSecond {
    private const val BASE_URL = "http://pet-test.rxswift.cn/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}