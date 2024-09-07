package com.rescue.flutter_720yun
import okhttp3.Callback

// 网络请求的统一封装
interface HttpApi {
    fun get(param: Map<String,Any>, path: String, callback: HttpCallback)
    fun getSync(param: Map<String,Any>, path: String): Any? = Any()
    fun post(body: Any, path: String, callback: HttpCallback)
    fun postSync(body: Any, path: String):Any? = Any()

}
