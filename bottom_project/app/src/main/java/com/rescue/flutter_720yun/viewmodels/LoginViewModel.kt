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
import com.rescue.flutter_720yun.util.Tool
import com.rescue.flutter_720yun.util.UserManager
import com.rescue.flutter_720yun.util.toMD5

class LoginViewModel: ViewModel() {

    private var appService = ServiceCreator.create<AppService>()
    private val _loginStatus = MutableLiveData<BaseResponse<UserInfo>?>()
    private val _agreementStatus = MutableLiveData(false)
    private val _showPasswordStatus = MutableLiveData(false)
    private val _countDownNum = MutableLiveData(60)
    private val _isCountDowning = MutableLiveData(false)
    private val _checkCodeSucc = MutableLiveData<Boolean?>()
    private val _findPasswordStatus = MutableLiveData<Boolean?>()

    val loginStatus: LiveData<BaseResponse<UserInfo>?> get() = _loginStatus
    val agreement: LiveData<Boolean> get() = _agreementStatus
    val showPassword: LiveData<Boolean> get() = _showPasswordStatus
    val checkCodeStatus: LiveData<Boolean?> get() = _checkCodeSucc
    val findPasswordStatus: LiveData<Boolean?> get() = _findPasswordStatus

    suspend fun loginNetworking(phone: String, password: String) {
        val passwordMD5String = password.toMD5()
        val response = appService.login(phone, passwordMD5String).awaitResponse()
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

    fun cleanFindPasswordStatus() {
        _findPasswordStatus.value = null
    }

    fun cleanCheckStatus() {
        _checkCodeSucc.value = null
    }

    fun changeAgreementStatus(finish: Boolean) {
        _agreementStatus.value = finish
    }

    fun changeShowPasswordStatus(show: Boolean) {
        _showPasswordStatus.value = show
    }

    // 获取验证码
    suspend fun getCodeNetworking(phone: String) {
        if (_isCountDowning.value == true) {
            return
        }
        val code = Tool.encryptionString(Tool.CodeStr) ?: ""
        appService.getVerificationCode(phone, code).awaitResponse()
    }

    // 验证验证码
    suspend fun checkCodeNetworking(phone: String, code: String) {
        val response = appService.checkCode(phone, code).awaitResponse()
        _checkCodeSucc.value = response.code == 200
    }

    // 登录
    suspend fun register(phone: String, password: String) {
        val response = appService.register(phone, password, password).awaitResponse()
        if (response.code == 200) {
            val userInfo = response.data
            UserManager.setUserInfo(userInfo)
            _loginStatus.value = response
        }else{
            _loginStatus.value = response
        }
    }

    // 找回密码
    suspend fun findPassword(phone: String, password: String, confirm_phone: String) {
        val response = appService.uploadPassword(phone, password, confirm_phone).awaitResponse()
        _findPasswordStatus.value = response.code == 200
    }

    // 倒计时
    fun countDownAction() {

    }
}

/*
// MARK: 加密
    public func encryptionString(codeStr: String) -> String? {
        let index: Int = Int(arc4random_uniform(100))
        let currentStr = Array(codeStr.map {$0})[index]
        guard let currentOne = self.base64CodingToString(object: "\(currentStr)") else {
            return nil
        }
        let index_str = "index_\(index)"
        guard let indexOne = self.base64CodingToString(object: index_str) else {
            return nil
        }

        guard let indexTwo = self.base64CodingToString(object: indexOne) else {
            return nil
        }

        let dateStr = Date().dateStringFromFormatString("yyyy年MM年dd年")
        if let enString = self.base64CodingToString(object: dateStr) {
            var enArr = Array(enString.map({ "\($0)" }))
            enArr.insert("$\(currentOne)", at: 2)
            enArr.insert("$\(currentOne)", at: enArr.count - 3)
            enArr.append("$\(indexTwo)")
            return enArr.joined()
        }else{
            return nil
        }
    }


    // TODO: base64编码处理
    private func base64CodingToString(object:String) -> String? {
        // 将传入的数据 UTF8 进行编码
        let codingData = object.data(using: .utf8)
        return codingData?.base64EncodedString()
    }

    // TODO: base64解码处理
    private func base64CodingDecoding(object:String) -> String? {
        // 将传入的数据 UTF8 进行编码
        guard let codingData = Data.init(base64Encoded: object) else {
            return nil
        }
        return String.init(data: codingData, encoding: .utf8)
    }

 */