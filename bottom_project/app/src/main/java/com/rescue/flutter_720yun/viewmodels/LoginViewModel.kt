package com.rescue.flutter_720yun.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rescue.flutter_720yun.models.BaseResponse
import com.rescue.flutter_720yun.models.UserInfo
import com.rescue.flutter_720yun.network.AppService
import com.rescue.flutter_720yun.network.ServiceCreator
import com.rescue.flutter_720yun.network.awaitResponse
import com.rescue.flutter_720yun.util.UserManager
import com.rescue.flutter_720yun.util.toMD5

class LoginViewModel: ViewModel() {

    private var appService = ServiceCreator.create<AppService>()
    private val _loginStatus = MutableLiveData<BaseResponse<UserInfo>?>()
    private val _agreementStatus = MutableLiveData(false)
    private val _showPasswordStatus = MutableLiveData(false)
    val loginStatus: LiveData<BaseResponse<UserInfo>?> get() = _loginStatus
    val agreement: LiveData<Boolean> get() = _agreementStatus
    val showPassword: LiveData<Boolean> get() = _showPasswordStatus
    suspend fun loginNetworking(phone: String, password: String) {
        val passwordMD5String = password.toMD5()
        val response = appService.login(phone, passwordMD5String).awaitResponse()
        Log.d("TAG", "==========")
        Log.d("TAG", response.toString())
        if (response.code == 200) {
            val userInfo = response.data
            UserManager.setUserInfo(userInfo)
            _loginStatus.value = response
        }else{
            _loginStatus.value = response
        }
    }

    fun cleanLoginStatus() {
        _loginStatus.value = null
    }

    fun changeAgreementStatus(finish: Boolean) {
        _agreementStatus.value = finish
    }

    fun changeShowPasswordStatus(show: Boolean) {
        _showPasswordStatus.value = show
    }
}