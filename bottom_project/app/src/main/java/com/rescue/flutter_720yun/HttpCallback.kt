package com.rescue.flutter_720yun

// 网络请求接口毁掉

interface HttpCallback {
    /**
     * 网络请求成功的回调
     * @param data 返回成功的数据
     */
    fun onSuccess(data: Any?)
    fun onFailed(error: Any?)
}